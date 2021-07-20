package test.ivan.vote.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import springfox.documentation.spring.web.json.Json;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.common.exception.SysException;
import test.ivan.vote.entity.Candidate;
import test.ivan.vote.entity.Election;
import test.ivan.vote.enums.ElectionStatus;
import test.ivan.vote.mapper.CandidateMapper;
import test.ivan.vote.mapper.ElectionMapper;
import test.ivan.vote.service.CandidateService;
import test.ivan.vote.service.ElectionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan
 */
@Slf4j
@Service
public class CandidateServiceImpl extends ServiceImpl<CandidateMapper, Candidate> implements CandidateService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private ElectionService electionService;

    @Override
    public boolean addCandidate(Candidate candidate) {
        Election election = electionService.getById(candidate.getElectionId());
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        if (!ElectionStatus.CREATED.eq(election.getStatus())) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "只有未开始的选举活动才能新增候选人");
        }

        candidate.setCandidateId(null);
        candidate.setCreatedTime(null);
        candidate.setUpdatedTime(null);
        candidate.setTotal(0);
        candidate.setIsWinner(0);

        return this.save(candidate);
    }

    @Override
    public boolean updateCandidate(Candidate candidate) {
        Candidate result = this.getById(candidate.getCandidateId());
        if (result == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        Election election = electionService.getById(candidate.getElectionId());
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        if (!ElectionStatus.CREATED.eq(election.getStatus())) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "只有未开始的选举活动才能编辑候选人");
        }

        candidate.setCreatedTime(null);
        candidate.setUpdatedTime(null);
        candidate.setTotal(null);
        candidate.setIsWinner(null);
        candidate.setElectionId(null);

        return this.updateById(candidate);
    }

    @Override
    public void updateCandidateVoteCount(Long electionId, Map<String, Integer> dataMap) {
        List<Candidate> candidateList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : dataMap.entrySet()) {
            Candidate candidate = new Candidate();
            candidate.setElectionId(electionId);
            candidate.setCandidateId(Long.valueOf(entry.getKey()));
            candidate.setTotal(entry.getValue());
            candidateList.add(candidate);
        }
        this.updateBatchById(candidateList);
    }

    @Override
    public List<Candidate> getAllIds(Long electionId) {
        LambdaQueryWrapper<Candidate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Candidate::getElectionId, electionId)
                .select(Candidate::getCandidateId);

        return this.list(wrapper);
    }

    @Override
    public List<Candidate> getAllList(Long electionId) {
        Election election = electionService.getById(electionId);
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        LambdaQueryWrapper<Candidate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Candidate::getElectionId, electionId);
        return this.list(wrapper);
    }

    @Override
    public List<Candidate> getVoteCount(Long electionId) {
        // 先查找缓存中是否有数据，有数据则直接返回
        String electionDataRedisKey = String.format(CommonRedisKey.ELECTION_CANDIDATE, electionId);
        boolean hasKey = redisTemplate.hasKey(electionDataRedisKey);
        if (hasKey) {
            Map<String, String> dataMap = redisTemplate.opsForHash().entries(electionDataRedisKey);
            List<Candidate> candidateList = new ArrayList<>();
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                Candidate candidate = new Candidate();
                candidate.setCandidateId(Long.valueOf(entry.getKey()));
                candidate.setTotal(Integer.valueOf(entry.getValue()));
                candidateList.add(candidate);
            }
            log.info("从缓存读取 {}", JSON.toJSONString(candidateList));
            return candidateList;
        }

        // 查找数据库
        LambdaQueryWrapper<Candidate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Candidate::getElectionId, electionId)
                .select(Candidate::getCandidateId, Candidate::getTotal);

        return this.list(wrapper);
    }

    /**
     * 获取指定选举活动候选人个数
     * @param electionId
     * @return
     */
    @Override
    public Integer count(Long electionId) {
        LambdaQueryWrapper<Candidate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Candidate::getElectionId, electionId);
        return this.count(wrapper);
    }
}
