package test.ivan.vote.member.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.common.bean.ApiCode;
import test.ivan.vote.common.bean.ApiResult;
import test.ivan.vote.member.domain.LoginResp;
import test.ivan.vote.member.entity.Member;
import test.ivan.vote.member.param.AuthEmailParam;
import test.ivan.vote.member.param.AuthIdCardParam;
import test.ivan.vote.member.param.LoginParam;
import test.ivan.vote.member.service.MemberService;

import javax.validation.Valid;

/**
 * @author Ivan
 */
@ApiModel("会员接口")
@RestController
@RequestMapping("/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    /**
     * 会员注册
     * @param member
     * @return
     */
    @ApiModelProperty(value = "会员注册")
    @PostMapping("/register")
    @ResponseBody
    public ApiResult register(@RequestBody Member member) {
        boolean success = memberService.register(member);
        if (success) {
            return ApiResult.ok(member);
        }
        return ApiResult.fail();
    }

    /**
     * 会员登录
     * @param param
     * @return
     */
    @ApiModelProperty(value = "会员登录")
    @PostMapping("/login")
    @ResponseBody
    public ApiResult login(@RequestBody LoginParam param) {
        LoginResp resp = memberService.login(param);
        if (resp != null) {
            return ApiResult.ok(resp);
        }
        return ApiResult.fail(ApiCode.LOGIN_EXCEPTION);
    }

    @ApiModelProperty(value = "获取会员信息")
    @GetMapping("/{memberId:\\d+}")
    @ResponseBody
    public ApiResult getMember(@PathVariable("memberId") Long memberId) {
        Member member = memberService.getMemberInfo(memberId);
        if (member != null) {
            return ApiResult.ok(member);
        }
        return ApiResult.fail(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户不存在");
    }

    @ApiModelProperty(value = "认证身份证")
    @PostMapping("/authIdCard")
    @ResponseBody
    public ApiResult authIdCard(@Valid @RequestBody AuthIdCardParam param) {
        boolean success = memberService.authByIdcard(param.getMemberId(), param.getIdCard());
        if (success) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
    }

    @ApiModelProperty(value = "认证邮箱")
    @PostMapping("/authEmail")
    @ResponseBody
    public ApiResult authEmail(@Valid @RequestBody AuthEmailParam param) {
        boolean success = memberService.authByEmail(param.getMemberId(), param.getEmail());
        if (success) {
            return ApiResult.ok();
        }
        return ApiResult.fail();
    }

    @GetMapping("/test")
    public ResponseEntity test(){
        Member member = memberService.getMemberInfo(1l);
        if (member != null) {
            return ResponseEntity.ok(ApiResult.ok(member));
        }
        return ResponseEntity.ok(ApiResult.fail(ApiCode.BUSINESS_EXCEPTION.getCode(), "用户不存在"));
    }
}
