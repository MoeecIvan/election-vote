package test.ivan.vote.admin.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.admin.api.ElectionVoteServiceApi;
import test.ivan.vote.admin.api.vo.Candidate;
import test.ivan.vote.common.bean.ApiResult;

import javax.validation.Valid;

/**
 * @author Ivan
 */
@ApiModel("候选人接口")
@RestController
@RequestMapping("/admin/candidate")
@Slf4j
public class CandidateController {
    @Autowired
    private ElectionVoteServiceApi electionVoteServiceApi;

    /**
     * 新增候选人
     * @param candidate 候选人
     * @return
     */
    @ApiModelProperty(value = "新增候选人")
    @PostMapping
    @ResponseBody
    public ApiResult addCandidate(@RequestBody Candidate candidate) {
        return electionVoteServiceApi.addCandidate(candidate);
    }

    /**
     * 更新候选人
     * @param candidateId
     * @param candidate
     * @return
     */
    @ApiModelProperty(value = "更新候选人")
    @PutMapping("/{candidateId:\\d+}")
    @ResponseBody
    public ApiResult updateCandidate(@PathVariable("candidateId") Long candidateId, @Valid @RequestBody Candidate candidate) {
        return electionVoteServiceApi.updateCandidate(candidate, candidateId);
    }

    /**
     * 查询候选人列表
     * @param electionId
     * @return
     */
    @ApiModelProperty(value = "查询候选人列表")
    @GetMapping("/list")
    @ResponseBody
    public ApiResult getCandidateList(@RequestParam("electionId") Long electionId) {
        return electionVoteServiceApi.getCandidateList(electionId);
    }

    /**
     * 查询候选人票数
     * @param electionId
     * @return
     */
    @ApiModelProperty(value = "查询候选人票数")
    @GetMapping("/voteCount")
    @ResponseBody
    public ApiResult getCandidateVoteCount(@RequestParam("electionId") Long electionId) {
        return electionVoteServiceApi.getCandidateVoteCount(electionId);
    }
}
