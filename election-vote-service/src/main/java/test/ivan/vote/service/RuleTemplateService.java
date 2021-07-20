package test.ivan.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.entity.Election;
import test.ivan.vote.entity.RuleTemplate;
import test.ivan.vote.member.entity.Member;

/**
 * @author Ivan
 */
public interface RuleTemplateService extends IService<RuleTemplate> {
    void createTemplate();
    void updateTemplate();
    void deleteTemplate();

    /**
     * 检查会员是否可以投票
     * @param templateId 规则模板编号
     * @param electionId 选举活动编号
     * @param candidateId 候选人编号
     * @param member 会员
     * @return 是否有投票权限
     */
    boolean checkVotePermission(Integer templateId, Long electionId, Long candidateId, Member member);
}
