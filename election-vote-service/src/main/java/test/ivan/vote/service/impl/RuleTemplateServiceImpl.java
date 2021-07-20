package test.ivan.vote.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.entity.Election;
import test.ivan.vote.entity.RuleTemplate;
import test.ivan.vote.enums.ElectionStatus;
import test.ivan.vote.mapper.RuleTemplateMapper;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.member.enums.MemberAuthType;
import test.ivan.vote.service.RuleTemplateService;
import test.ivan.vote.service.VoteService;

import java.util.List;

/**
 * @author Ivan
 */
@Service
public class RuleTemplateServiceImpl extends ServiceImpl<RuleTemplateMapper, RuleTemplate> implements RuleTemplateService {
    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private VoteService voteService;

    @Override
    public void createTemplate() {

    }

    @Override
    public void updateTemplate() {

    }

    @Override
    public void deleteTemplate() {

    }

    @Override
    public boolean checkVotePermission(Integer templateId, Long electionId, Long candidateId, Member member) {
        // 从缓存获取选举活动的状态，若取不到或状态为结束，说明不能再投票
        String electionStatusRedisKey = String.format(CommonRedisKey.ELECTION_STATUS, electionId);
        Integer status = (Integer)redisTemplate.opsForValue().get(electionStatusRedisKey);
        if (status == null || ElectionStatus.FINISHED.eq(status)) {
            return false;
        }

        // 检查会员投票权限， 如果是未注册会员或是没有认证
        if (member == null || !MemberAuthType.EMAIL_IDCARD.eq(member.getAuthType())) {
            return false;
        }

        // 检查用户是否投过票，投过票不能再投
        return !voteService.isMemberVoted(electionId, candidateId, member.getMemberId());
        /*String redisKey = String.format(CommonRedisKey.ELECTION_RULES, election.getElectionId());
        long size = redisTemplate.opsForList().size(redisKey);
        List<String> rules = redisTemplate.opsForList().range(redisKey, 0, size  - 1);
        */
        //return false;
    }
}
