package test.ivan.vote.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.JSONSerializerMap;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.api.MemberApi;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.common.bean.PageInfo;
import test.ivan.vote.entity.Vote;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.param.VoteMemberListParam;
import test.ivan.vote.param.VoteParam;
import test.ivan.vote.service.VoteService;

/**
 * @author Ivan
 */
@ApiModel("投票服务")
@RestController
@RequestMapping("/vote")
public class VoteController {
    @Autowired
    private VoteService voteService;

    @Autowired
    private MemberApi memberApi;

    /**
     * 查询投票用户分页数据
     * @param param
     * @return
     */
    @ApiModelProperty(value = "查询投票会员分页数据")
    @GetMapping("/memberList")
    @ResponseBody
    public ApiResult getVoteMemberList(@RequestBody VoteMemberListParam param) {
        PageInfo<Vote> page = voteService.getVoteMemberList(param);
        return ApiResult.ok(page);
    }

    @ApiModelProperty(value = "给候选人投票")
    @PostMapping("/vote")
    @ResponseBody
    public ApiResult vote(@RequestBody VoteParam param) {
        voteService.vote(param);
        return ApiResult.ok();
    }

    @GetMapping("/test")
    public ApiResult test() {
        ApiResult<Member> obj = memberApi.getMember(1l);
        System.out.println("k===== "+JSON.toJSONString(obj));
        return obj;// ApiResult.ok(obj);

        //ApiResult result = JSON.parseObject(obj, ApiResult.class);
        //return result;
    }
}
