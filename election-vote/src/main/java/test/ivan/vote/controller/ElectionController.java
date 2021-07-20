package test.ivan.vote.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.param.ElectionListParam;
import test.ivan.vote.service.ElectionService;

import javax.validation.Valid;

/**
 * @author Ivan
 */
@Slf4j
@ApiModel("选举活动接口")
@RestController
@RequestMapping("/election")
public class ElectionController {
    @Autowired
    private ElectionService electionService;

    /**
     * 创建选举活动
     * @param election
     * @return
     */
    @ApiModelProperty(value = "创建选举活动")
    @PostMapping
    @ResponseBody
    public ApiResult addElection(@RequestBody ElectionVo election) {
        boolean success = electionService.createElection(election);
        if (success) {
            return ApiResult.okMap("electionId", election.getElectionId());
        }
        return ApiResult.fail();
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
        election.setElectionId(electionId);
        boolean success = electionService.updateElection(election);
        if (success) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
    }

    /**
     * 删除选举活动
     * @return
     */
    @ApiModelProperty(value = "删除选举活动")
    @DeleteMapping("/{electionId:\\d+}")
    @ResponseBody
    public ApiResult deleteElection(@PathVariable Long electionId) {
        boolean success = electionService.deleteElection(electionId);
        if (success) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
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
        ElectionVo vo = electionService.getElection(electionId);
        if (vo != null) {
            return ApiResult.ok(vo);
        }
        return ApiResult.fail();
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
        PageInfo<ElectionVo> page = electionService.getElectionList(param);
        return ApiResult.ok(page);
    }

    /**
     * 开始选举活动
     * @return
     */
    @ApiModelProperty(value = "开始选举活动")
    @PostMapping("/{electionId:\\d+}/start")
    @ResponseBody
    public ApiResult startElection(@PathVariable("electionId") Long electionId) {
        electionService.startElection(electionId);
        return ApiResult.ok();
    }

    /**
     * 结束选举活动
     * @return
     */
    @ApiModelProperty(value = "结束选举活动")
    @PostMapping("/{electionId:\\d+}/stop")
    @ResponseBody
    public ApiResult stopElection(@PathVariable("electionId") Long electionId) {
        electionService.stopElection(electionId);
        return ApiResult.ok();
    }
}
