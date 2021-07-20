package test.ivan.vote.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 规则表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_VOTE_RULE")
@ApiModel(value="VoteRule对象", description="规则表")
public class VoteRule implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "规则编号")
    @TableId(value = "RULE_ID", type = IdType.AUTO)
    private Long ruleId;

    @ApiModelProperty(value = "模板编号")
    @TableField("TEMPLATE_ID")
    private Integer templateId;

    @ApiModelProperty(value = "规则名称")
    @TableField("RULE_NAME")
    private String ruleName;

    @ApiModelProperty(value = "规则内容")
    @TableField("CONTENT")
    private String content;

    @ApiModelProperty(value = "是否启用 0-未启用, 1-启用")
    @TableField("IS_ENABLE")
    private Integer isEnable;

    @ApiModelProperty(value = "是否删除 0-未删除,1-已删除")
    @TableField("IS_DEL")
    @TableLogic
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updatedTime;


}
