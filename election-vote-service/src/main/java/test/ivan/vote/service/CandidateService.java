package test.ivan.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.entity.Candidate;

import java.util.List;
import java.util.Map;

/**
 * @author Ivan
 */
public interface CandidateService extends IService<Candidate> {

    /**
     * 新增候选人
     * @param candidate 候选人
     * @return 成功返回true，否则返回false
     */
    boolean addCandidate(Candidate candidate);

    /**
     * 更新候选人
     * @param candidate 候选人
     * @return 成功返回true，否则返回false
     */
    boolean updateCandidate(Candidate candidate);

    /**
     * 批量更新候选人票数，一般在选举结束的时候调用
     * @param electionId 选举编号
     * @param dataMap 候选人投票数据
     */
    void updateCandidateVoteCount(Long electionId, Map<String, Integer> dataMap);

    /**
     * 获取选举活动的所有候选人的编号列表
     * @param electionId
     * @return
     */
    List<Candidate> getAllIds(Long electionId);

    /**
     * 获取选举活动的所有候选人
     * @param electionId
     * @return
     */
    List<Candidate> getAllList(Long electionId);

    /**
     * 获取选举活动的所有候选人的票数
     * @param electionId
     * @return
     */
    List<Candidate> getVoteCount(Long electionId);

    /**
     * 获取指定选举活动候选人个数
     * @param electionId
     * @return
     */
    Integer count(Long electionId);
}
