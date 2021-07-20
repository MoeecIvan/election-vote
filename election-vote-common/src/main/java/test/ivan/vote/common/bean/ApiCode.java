package test.ivan.vote.common.bean;

import lombok.Getter;

/**
 * Api响应码
 *
 * @author Ivan
 */
@Getter
public enum ApiCode {
    /**
     * 操作成功
     **/
    SUCCESS(0, "操作成功"),

    /**
     * 非法访问
     **/
    UNAUTHORIZED(401, "非法访问"),

    /**
     * 没有权限
     **/
    NOT_PERMISSION(403, "没有权限"),

    /**
     * 你请求的资源不存在
     **/
    NOT_FOUND(404, "你请求的资源不存在"),

    /**
     * 操作失败
     **/
    FAIL(1, "操作失败"),

    /**
     * 登录失败
     **/
    LOGIN_EXCEPTION(4000, "登录失败"),

    /**
     * 登录失败，用户不存在
     */
    ACCOUNT_NOT_FOUND(4001, "用户不存在"),

    /**
     * 系统异常
     **/
    SYSTEM_EXCEPTION(5000, "系统异常"),

    /**
     * 请求参数校验异常
     **/
    PARAMETER_EXCEPTION(100502, "请求参数校验异常"),
    /**
     * 请求参数解析异常
     **/
    PARAMETER_PARSE_EXCEPTION(5002, "请求参数解析异常"),
    /**
     * HTTP内容类型异常
     **/
    HTTP_MEDIA_TYPE_EXCEPTION(5003, "HTTP内容类型异常"),
    // 请求头参数异常
    HTTP_HEADER_EXCEPTION(5004, "HTTP请求头参数异常"),
    /**
     * 系统处理异常
     **/
    SYS_EXCEPTION(5100, "系统处理异常"),
    /**
     * 业务处理异常
     **/
    BUSINESS_EXCEPTION(5101, "业务处理异常"),
    /**
     * 数据库处理异常
     **/
    DAO_EXCEPTION(5102, "数据库处理异常"),
    /**
     * 验证码校验异常
     **/
    VERIFICATION_CODE_EXCEPTION(5103, "验证码校验异常"),
    /**
     * 登录授权异常
     **/
    AUTHENTICATION_EXCEPTION(5104, "登录授权异常"),
    /**
     * 没有访问权限
     **/
    UNAUTHENTICATED_EXCEPTION(100403, "没有访问权限"),
    /**
     * 没有访问权限
     **/
    UNAUTHORIZED_EXCEPTION(5106, "没有访问权限"),
    /**
     * JWT Token解析异常
     **/
    JWTDECODE_EXCEPTION(5107, "Token解析异常"),

    HTTP_REQUEST_METHOD_NOT_SUPPORTED_EXCEPTION(5108, "METHOD NOT SUPPORTED"),

    TOKEN_NOT_FOUND(101401, "Token不能为空"),

    TOKEN_INVALID(101402, "Token过期或无效");

    private final int code;
    private final String message;

    ApiCode(final int code, final String message) {
        this.code = code;
        this.message = message;
    }
}
