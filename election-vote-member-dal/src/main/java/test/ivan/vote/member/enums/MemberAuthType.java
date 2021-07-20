package test.ivan.vote.member.enums;

import lombok.Getter;

/**
 * 会员认证状态
 */
@Getter
public enum MemberAuthType {
    UNAUTH(0, "未认证"),
    EMAIL(1, "邮箱认证"),
    IDCARD(2, "身份证认证"),
    EMAIL_IDCARD(3, "邮箱和身份证认证");

    /**
     * 状态值
     */
    private Integer type;

    /**
     * 状态名称
     */
    private String name;

    MemberAuthType(Integer type, String name){
        this.type = type;
        this.name = name;
    }

    public boolean eq(Integer value) {
        return this.type.equals(value);
    }
}
