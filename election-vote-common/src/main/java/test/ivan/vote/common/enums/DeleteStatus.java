package test.ivan.vote.common.enums;

import lombok.Getter;

/**
 * 删除状态
 */
@Getter
public enum DeleteStatus {
    NOT_DELETED(0, "未删除"),
    DELETED(1, "删除");

    /**
     * 状态值
     */
    private Integer status;

    /**
     * 状态名称
     */
    private String name;

    DeleteStatus(Integer status, String name){
        this.status = status;
        this.name = name;
    }
}
