package test.ivan.vote.admin.api;

import feign.Headers;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.admin.api.vo.Candidate;
import test.ivan.vote.admin.api.vo.ElectionListParam;
import test.ivan.vote.admin.api.vo.ElectionVo;
import test.ivan.vote.common.bean.ApiResult;

import javax.validation.Valid;
import java.util.List;

@FeignClient(value = "election-vote")
@Headers({"Accept: application/json"})
public interface ElectionVoteServiceApi {

    /**
     * 获取选举活动
     * @param electionId
     * @return
     */
    @RequestMapping(value = "/election/{electionId}", method = RequestMethod.GET)
    @Headers("Content-Type: application/json")
    ApiResult getElection(@PathVariable("electionId") Long electionId);

    /**
     * 新增选举活动
     * @param election
     * @return
     */
    @RequestMapping(value = "/election", method = RequestMethod.POST)
    @Headers("Content-Type: application/json")
    ApiResult addElection(ElectionVo election);

    /**
     * 更新选举活动
     * @param election
     * @return
     */
    @RequestMapping(value = "/election/{electionId:\\d+}", method = RequestMethod.PUT)
    @Headers("Content-Type: application/json")
    ApiResult updateElection(@RequestBody ElectionVo election, @PathVariable("electionId") Long electionId);

    /**
     * 删除选举活动
     * @param electionId
     * @return
     */
    @RequestMapping(value = "/election/{electionId:\\d+}", method = RequestMethod.DELETE)
    @Headers("Content-Type: application/json")
    ApiResult deleteElection(@PathVariable("electionId") Long electionId);

    /**
     * 获取选举活动分页列表
     * @param param
     * @return
     */
    @PostMapping(value = "/election/list")
    @Headers("Content-Type: application/json")
    ApiResult getElectionList(@RequestBody ElectionListParam param);

    /**
     * 开始选举活动
     * @return
     */
    @PostMapping("/election/{electionId:\\d+}/start")
    @Headers("Content-Type: application/json")
    ApiResult startElection(@PathVariable("electionId") Long electionId);

    /**
     * 结束选举活动
     * @return
     */
    @PostMapping("/election/{electionId:\\d+}/stop")
    @Headers("Content-Type: application/json")
    ApiResult stopElection(@PathVariable("electionId") Long electionId);

    // ========== 候选人Api ==========

    /**
     * 新增候选人
     * @param candidate 候选人
     * @return
     */
    @ApiModelProperty(value = "新增候选人")
    @PostMapping("/candidate")
    @Headers("Content-Type: application/json")
    ApiResult addCandidate(@RequestBody Candidate candidate);

    /**
     * 更新候选人
     * @param candidateId
     * @param candidate
     * @return
     */
    @ApiModelProperty(value = "更新候选人")
    @PutMapping("/candidate/{candidateId:\\d+}")
    @Headers("Content-Type: application/json")
    ApiResult updateCandidate(@RequestBody Candidate candidate, @PathVariable("candidateId") Long candidateId);

    /**
     * 查询候选人列表
     * @param electionId
     * @return
     */
    @ApiModelProperty(value = "查询候选人列表")
    @GetMapping("/candidate/list")
    @Headers("Content-Type: application/json")
    ApiResult getCandidateList(@RequestParam("electionId") Long electionId);

    /**
     * 查询候选人票数
     * @param electionId
     * @return
     */
    @ApiModelProperty(value = "查询候选人票数")
    @GetMapping("/candidate/voteCount")
    @Headers("Content-Type: application/json")
    ApiResult getCandidateVoteCount(@RequestParam("electionId") Long electionId);
}
