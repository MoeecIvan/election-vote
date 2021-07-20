package test.ivan.vote.admin.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.admin.api.ElectionVoteServiceApi;
import test.ivan.vote.admin.api.vo.ElectionListParam;
import test.ivan.vote.admin.api.vo.ElectionVo;
import test.ivan.vote.common.bean.ApiResult;

import javax.validation.Valid;

@ApiModel("选举活动接口")
@RestController
@RequestMapping("/admin/election")
public class ElectionController {
    @Autowired
    private ElectionVoteServiceApi electionVoteServiceApi;

    /**
     * 创建选举活动
     * @param election
     * @return
     */
    @ApiModelProperty(value = "创建选举活动")
    @PostMapping
    @ResponseBody
    public ApiResult addElection(@RequestBody ElectionVo election) {
        return electionVoteServiceApi.addElection(election);
    }

    /**
     * 更新选举活动
     * @param election
     * @return
     */
    @ApiModelProperty(value = "更新选举活动")
    @PutMapping("/{electionId:\\d+}")
    @ResponseBody
    public ApiResult updateElection(@PathVariable("electionId") Long electionId, @Valid @RequestBody ElectionVo election) {
        return electionVoteServiceApi.updateElection(election, electionId);
    }

    /**
     * 删除选举活动
     * @return
     */
    @ApiModelProperty(value = "删除选举活动")
    @DeleteMapping("/{electionId:\\d+}")
    @ResponseBody
    public ApiResult deleteElection(@PathVariable Long electionId) {
        return electionVoteServiceApi.deleteElection(electionId);
    }

    /**
     * 获取选举活动
     * @param electionId
     * @return
     */
    @ApiModelProperty(value = "获取选举活动")
    @GetMapping("/{electionId:\\d+}")
    @ResponseBody
    public ApiResult getElection(@PathVariable("electionId") Long electionId) {
        return electionVoteServiceApi.getElection(electionId);
    }

    /**
     * 获取选举活动分页列表
     * @param param
     * @return
     */
    @ApiModelProperty(value = "获取选举活动分页列表")
    @PostMapping("/list")
    @ResponseBody
    public ApiResult getElectionList(@RequestBody ElectionListParam param) {
        return electionVoteServiceApi.getElectionList(param);
    }

    /**
     * 开始选举活动
     * @return
     */
    @ApiModelProperty(value = "开始选举活动")
    @PostMapping("/{electionId:\\d+}/start")
    @ResponseBody
    public ApiResult startElection(@PathVariable("electionId") Long electionId) {
        return electionVoteServiceApi.startElection(electionId);
    }

    /**
     * 结束选举活动
     * @return
     */
    @ApiModelProperty(value = "结束选举活动")
    @PostMapping("/{electionId:\\d+}/stop")
    @ResponseBody
    public ApiResult stopElection(@PathVariable("electionId") Long electionId) {
        return electionVoteServiceApi.stopElection(electionId);
    }
}
