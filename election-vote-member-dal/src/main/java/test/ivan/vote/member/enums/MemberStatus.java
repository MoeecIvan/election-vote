package test.ivan.vote.member.enums;

import lombok.Getter;

@Getter
public enum MemberStatus {
    FORBIDDEN(0, "禁用"),
    NORMAL(1, "正常");

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String name;

    MemberStatus(Integer status, String name){
        this.status = status;
        this.name = name;
    }

    public boolean eq(Integer value) {
        return this.status.equals(value);
    }
}
