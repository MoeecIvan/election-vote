package test.ivan.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.ivan.vote.api.MemberApi;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.common.exception.SysException;
import test.ivan.vote.common.util.HttpServletUtils;
import test.ivan.vote.common.util.SnowflakeIdFactory;
import test.ivan.vote.common.util.SortUtil;
import test.ivan.vote.entity.Election;
import test.ivan.vote.entity.Vote;
import test.ivan.vote.mapper.VoteMapper;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.param.VoteMemberListParam;
import test.ivan.vote.param.VoteParam;
import test.ivan.vote.service.ElectionService;
import test.ivan.vote.service.RuleTemplateService;
import test.ivan.vote.service.VoteService;

import java.time.LocalDateTime;

/**
 * @author Ivan
 */
@Slf4j
@Service
public class VoteServiceImpl extends ServiceImpl<VoteMapper, Vote> implements VoteService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RuleTemplateService ruleTemplateService;
    @Autowired
    private ElectionService electionService;
    @Autowired
    private SnowflakeIdFactory idFactory;
    @Autowired
    private MemberApi memberApi;

    @Override
    public void vote(VoteParam param) {
        log.info(JSON.toJSONString(param));
        // 获取会员信息
        ApiResult<Member> memberResult = memberApi.getMember(param.getMemberId());
        Member member = memberResult.getData();
        if (member == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }

        //查询选举活动信息
        Election election = electionService.getById(param.getElectionId());
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }

        // 获取会员信息，检查是否可以投票
        boolean allowVote = ruleTemplateService.checkVotePermission(election.getTemplateId(),
                election.getElectionId(), param.getCandidateId(), member);
        if (!allowVote) {
            log.info("用户身份认证不符规定或已投票，不能投票");
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户身份认证不符规定或已投票，不能投票");
        }

        // 可以投票，缓存中增加增加计数
        String electionDataRedisKey = String.format(CommonRedisKey.ELECTION_CANDIDATE, param.getElectionId());
        String val = (String)redisTemplate.opsForHash().get(electionDataRedisKey, String.valueOf(param.getCandidateId()));
        log.info("electionDataRedisKey:{},getCandidateId:{},val:{}", electionDataRedisKey, String.valueOf(param.getCandidateId()), val);
        int count = Integer.parseInt(val) + 1;
        redisTemplate.opsForHash().put(electionDataRedisKey, String.valueOf(param.getCandidateId()), String.valueOf(count));
        // redisTemplate.opsForHash().increment(electionDataRedisKey, String.valueOf(param.getCandidateId()), 1);

        // 数据库增加投票数据
        Vote vote = new Vote();
        vote.setId(idFactory.nextId());
        vote.setElectionId(election.getElectionId());
        vote.setCandidateId(param.getCandidateId());
        vote.setMemberId(member.getMemberId());
        vote.setAvatar(member.getAvatar());
        vote.setVoterName(member.getNickName());
        vote.setVoteTime(LocalDateTime.now());
        vote.setIsNotified(0);
        this.save(vote);
    }

    @Override
    public boolean isMemberVoted(Long electionId, Long candidateId, Long memberId) {
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Vote::getElectionId, electionId)
                .eq(Vote::getCandidateId, candidateId)
                .eq(Vote::getMemberId, memberId);
        int count = this.count(wrapper);
        return count > 0;
    }

    @Override
    public PageInfo<Vote> getVoteMemberList(VoteMemberListParam param) {
        LambdaQueryWrapper<Vote> wrapper = new LambdaQueryWrapper<>();
        if (param.getCandidateId() != null) {
            wrapper.eq(Vote::getCandidateId, param.getCandidateId());
        }
        if (param.getElectionId() != null) {
            wrapper.eq(Vote::getElectionId, param.getElectionId());
        }

        Page<Vote> page = new Page<>(param.getPageIndex(), param.getPageSize());
        SortUtil.handlePageSort(param, page, true);
        IPage<Vote> resultPage = this.baseMapper.selectPage(page, wrapper);
        PageInfo<Vote> result = new PageInfo<>(resultPage);
        return result;
    }
}
