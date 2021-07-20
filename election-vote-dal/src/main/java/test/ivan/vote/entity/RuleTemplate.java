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
 * 规则模板表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_RULE_TEMPLATE")
@ApiModel(value="RuleTemplate对象", description="规则模板表")
public class RuleTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板编号")
    @TableId(value = "TEMPLATE_ID", type = IdType.AUTO)
    private Integer templateId;

    @ApiModelProperty(value = "模板名称")
    @TableField("TEMPLATE_NAME")
    private String templateName;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;

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
