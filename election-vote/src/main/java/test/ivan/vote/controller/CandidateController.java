package test.ivan.vote.controller;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.domain.ElectionVo;
import test.ivan.vote.entity.Candidate;
import test.ivan.vote.service.CandidateService;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Ivan
 */
@ApiModel("候选人接口")
@RestController
@RequestMapping("/candidate")
@Slf4j
public class CandidateController {
    @Autowired
    private CandidateService candidateService;

    /**
     * 新增候选人
     * @param candidate 候选人
     * @return
     */
    @ApiModelProperty(value = "新增候选人")
    @PostMapping
    @ResponseBody
    public ApiResult addCandidate(@RequestBody Candidate candidate) {
        boolean success = candidateService.addCandidate(candidate);
        if (success) {
            return ApiResult.okMap("candidateId", candidate.getCandidateId());
        }
        return ApiResult.fail();
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
        candidate.setCandidateId(candidateId);
        boolean success = candidateService.updateCandidate(candidate);
        if (success) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
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
        List<Candidate> list = candidateService.getAllList(electionId);
        return ApiResult.ok(list);
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
        List<Candidate> list = candidateService.getVoteCount(electionId);
        return ApiResult.ok(list);
    }
}
