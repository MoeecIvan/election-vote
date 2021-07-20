package test.ivan.vote.member.param;

import lombok.Data;

/**
 * 登录参数
 */
@Data
public class LoginParam {
    /**
     * 登录名
     */
    private String loginName;

    /**
     * 登录密码
     */
    private String password;
}
