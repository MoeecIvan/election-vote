package test.ivan.vote.service;

import com.baomidou.mybatisplus.extension.service.IService;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.entity.Election;
import test.ivan.vote.param.ElectionListParam;

/**
 * @author Ivan
 */
public interface ElectionService extends IService<Election> {

    /**
     * 新建选举活动
     * @param electionVo 选举活动
     * @return 成功返回true，否则返回false
     */
    boolean createElection(ElectionVo electionVo);

    /**
     * 更新选举活动
     * @param electionVo 选举活动
     * @return 成功返回true，否则返回false
     */
    boolean updateElection(ElectionVo electionVo);

    /**
     * 删除选举活动
     * @param electionId 选举活动编号
     * @return 成功返回true，否则返回false
     */
    boolean deleteElection(Long electionId);

    /**
     * 查询单个选举活动
     * @param electionId 选举活动编号
     * @return 选举活动信息
     */
    ElectionVo getElection(Long electionId);

    /**
     * 查询选举活动分页列表数据
     * @param param
     * @return
     */
    PageInfo<ElectionVo> getElectionList(ElectionListParam param);

    /**
     * 开始选举
     * @param electionId
     */
    void startElection(Long electionId);

    /**
     * 结束选举
     * @param electionId
     */
    void stopElection(Long electionId);

    /**
     * 设置规则模板
     * @param templateId
     */
    void setTemplate(Integer templateId);
}
