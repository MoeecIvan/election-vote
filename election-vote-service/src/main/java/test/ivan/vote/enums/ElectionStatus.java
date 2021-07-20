package test.ivan.vote.enums;

import lombok.Getter;

@Getter
public enum ElectionStatus {
    CREATED(0, "未启动"),
    STARTED(1, "选举中"),
    FINISHED(2, "已结束");

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String name;

    ElectionStatus(Integer status, String name){
        this.status = status;
        this.name = name;
    }

    public boolean eq(Integer value) {
        return this.status.equals(value);
    }
}
