package test.ivan.vote.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.common.constant.CommonRedisKey;
import test.ivan.vote.common.enums.DeleteStatus;
import test.ivan.vote.common.exception.SysException;
import test.ivan.vote.common.util.SortUtil;
import test.ivan.vote.convert.ElectionVoConvert;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.entity.Candidate;
import test.ivan.vote.entity.Election;
import test.ivan.vote.enums.ElectionStatus;
import test.ivan.vote.mapper.ElectionMapper;
import test.ivan.vote.param.ElectionListParam;
import test.ivan.vote.service.CandidateService;
import test.ivan.vote.service.ElectionService;
import test.ivan.vote.service.EmailService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Ivan
 */
@Service
public class ElectionServiceImpl extends ServiceImpl<ElectionMapper, Election> implements ElectionService {
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private CandidateService candidateService;
    @Autowired
    private EmailService emailService;

    @Override
    public boolean createElection(ElectionVo electionVo) {
        Election election = ElectionVoConvert.INSTANCE.voToElection(electionVo);
        election.setStatus(ElectionStatus.CREATED.getStatus());
        election.setElectionId(null);
        election.setCreatedTime(null);
        election.setStartTime(null);
        election.setEndTime(null);
        election.setTemplateId(0);
        election.setIsDel(DeleteStatus.NOT_DELETED.getStatus());

        boolean success = this.save(election);
        if (!success) {
            return success;
        }

        electionVo.setElectionId(election.getElectionId());
        return success;
    }

    @Override
    public boolean updateElection(ElectionVo electionVo) {
        Election result = this.getById(electionVo.getElectionId());
        if (result == null) {
            return false;
        }
        Integer status = result.getStatus();
        // ???????????????????????????????????????????????????????????????
        if (!ElectionStatus.CREATED.eq(status)) {
            return false;
        }
        Election election = ElectionVoConvert.INSTANCE.voToElection(electionVo);
        election.setCreatedTime(null);
        election.setStartTime(null);
        election.setEndTime(null);
        election.setTemplateId(0);
        election.setUpdatedTime(null);

        return this.updateById(election);
    }

    @Override
    public boolean deleteElection(Long electionId) {
        Election result = this.getById(electionId);
        if (result == null) {
            return false;
        }

        Integer status = result.getStatus();
        // ?????????????????????????????????????????????
        if (!ElectionStatus.CREATED.eq(status)) {
            return false;
        }
        return this.removeById(electionId);
    }

    @Override
    public ElectionVo getElection(Long electionId) {
        Election result = this.getById(electionId);
        if (result == null) {
            return null;
        }
        return ElectionVoConvert.INSTANCE.electionToVo(result);
    }

    @Override
    public PageInfo<ElectionVo> getElectionList(ElectionListParam param) {
        LambdaQueryWrapper<Election> wrapper = new LambdaQueryWrapper<>();
        if (param.getElectionId() != null) {
            wrapper.eq(Election::getElectionId, param.getElectionId());
        }

        Page<Election> page = new Page<>(param.getPageIndex(), param.getPageSize());
        SortUtil.handlePageSort(param, page, true);
        IPage<Election> pageInfo = this.baseMapper.selectPage(page, wrapper);

        // ????????????
        List<ElectionVo> list = new ArrayList<>();
        if (pageInfo.getRecords().size() > 0) {
            for (Election election: pageInfo.getRecords()) {
                ElectionVo vo = ElectionVoConvert.INSTANCE.electionToVo(election);
                list.add(vo);
            }
        }

        PageInfo<ElectionVo> result = new PageInfo<>();
        result.setPageIndex(pageInfo.getCurrent());
        result.setPageSize(pageInfo.getSize());
        result.setTotal(pageInfo.getTotal());
        result.setItems(list);
        return result;
    }

    @Override
    public void startElection(Long electionId) {
        Election election = this.getById(electionId);
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        Integer status = election.getStatus();
        if (ElectionStatus.FINISHED.eq(status)) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "????????????????????????????????????");
        }
        if (ElectionStatus.STARTED.eq(status)) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "???????????????????????????????????????");
        }

        // ????????????????????????????????????????????????
        int count = candidateService.count(electionId);
        if (count < 2) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "???????????????????????????????????????");
        }

        election.setStatus(ElectionStatus.STARTED.getStatus());
        election.setStartTime(LocalDateTime.now());
        this.updateById(election);

        // ?????????????????????????????????
        String electionStatusRedisKey = String.format(CommonRedisKey.ELECTION_STATUS, electionId);
        redisTemplate.opsForValue().set(electionStatusRedisKey, ElectionStatus.STARTED.getStatus());

        List<Candidate> candidateList = candidateService.getAllIds(electionId);
        Map<String, String> dataMap = new HashMap<>();
        for (Candidate candidate : candidateList) {
            dataMap.put(String.valueOf(candidate.getCandidateId()), "0");
        }
        String electionDataRedisKey = String.format(CommonRedisKey.ELECTION_CANDIDATE, electionId);
        redisTemplate.opsForHash().putAll(electionDataRedisKey, dataMap);
    }

    @Override
    public void stopElection(Long electionId) {
        Election election = this.getById(electionId);
        if (election == null) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION);
        }
        Integer status = election.getStatus();
        if (ElectionStatus.FINISHED.eq(status)) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "??????????????????");
        }
        if (ElectionStatus.CREATED.eq(status)) {
            throw new SysException(ApiCode.BUSINESS_EXCEPTION.getCode(), "?????????????????????");
        }

        election.setStatus(ElectionStatus.FINISHED.getStatus());
        election.setEndTime(LocalDateTime.now());
        this.updateById(election);

        // ????????????????????????????????????????????????????????????????????????????????????????????????
        String electionStatusRedisKey = String.format(CommonRedisKey.ELECTION_STATUS, electionId);
        redisTemplate.opsForValue().set(electionStatusRedisKey, ElectionStatus.FINISHED.getStatus());

        String electionDataRedisKey = String.format(CommonRedisKey.ELECTION_CANDIDATE, electionId);
        // Map<String, Integer> dataMap = redisTemplate.opsForHash().entries(electionDataRedisKey);

        // ??????????????????
        redisTemplate.delete(electionStatusRedisKey);
        redisTemplate.delete(electionDataRedisKey);

        // ?????????????????????????????????????????????
        emailService.send(electionId);
    }

    @Override
    public void setTemplate(Integer templateId) {

    }
}
