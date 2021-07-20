package test.ivan.vote.admin.controller;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import test.ivan.vote.admin.service.UserService;
import test.ivan.vote.admin.vo.LoginUserVo;
import test.ivan.vote.common.bean.ApiResult;

import javax.validation.Valid;

/**
 * @author Ivan
 */
@RestController
@RequestMapping("/admin")
@ApiModel("后台用户接口")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 用户登录
     * @param userVo 登录信息
     * @return 用户信息
     */
    @ApiModelProperty("用户登录")
    @PostMapping("/login")
    @ResponseBody
    public ApiResult login(@Valid @RequestBody LoginUserVo userVo) {
        boolean success = userService.login(userVo);
        if (success) {
            return ApiResult.ok(userVo);
        }
        return ApiResult.fail();
    }

    /**
     * 获取用户信息
     * @param userId 用户编号
     * @return 用户信息
     */
    @ApiModelProperty("获取用户信息")
    @GetMapping("/user/{userId:\\d+}")
    @ResponseBody
    public ApiResult getUserInfo(@PathVariable Integer userId) {
        LoginUserVo userVo = userService.getUserInfo(userId);
        if (userVo != null) {
            return ApiResult.ok(userVo);
        }
        return ApiResult.fail();
    }
}
