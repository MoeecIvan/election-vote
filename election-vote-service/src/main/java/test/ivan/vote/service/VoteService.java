package test.ivan.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.entity.Vote;
import test.ivan.vote.param.VoteMemberListParam;
import test.ivan.vote.param.VoteParam;

/**
 * @author Ivan
 */
public interface VoteService extends IService<Vote> {

    /**
     * 给候选人投票
     * @param param
     */
    void vote(VoteParam param);

    /**
     * 检查会员是否投过票
     * @param electionId
     * @param candidateId
     * @param memberId
     * @return
     */
    boolean isMemberVoted(Long electionId, Long candidateId, Long memberId);

    /**
     * 获取指定投票人列表
     * @param param 请求参数
     * @return  分页列表数据
     */
    PageInfo<Vote> getVoteMemberList(VoteMemberListParam param);
}
