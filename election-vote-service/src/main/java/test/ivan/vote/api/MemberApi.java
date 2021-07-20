package test.ivan.vote.api;

import com.alibaba.fastjson.JSONObject;
import feign.Headers;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.member.entity.Member;

@FeignClient(value = "election-vote-member")
@Headers({"Accept: application/json"})
public interface MemberApi {
    /**
     * 获取会员信息
     * @param memberId
     * @return
     */
    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
    @Headers("Content-Type: application/json")
    ApiResult<Member> getMember(@PathVariable("memberId") Long memberId);
}
