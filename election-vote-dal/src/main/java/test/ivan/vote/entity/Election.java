package test.ivan.vote.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 选举表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_ELECTION")
@ApiModel(value="Election对象", description="选举表")
public class Election implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "选举编号")
    @TableId(value = "ELECTION_ID", type = IdType.AUTO)
    private Long electionId;

    @ApiModelProperty(value = "选举名称")
    @TableField("ELECTION_NAME")
    private String electionName;

    @ApiModelProperty(value = "简介")
    @TableField("BRIFT")
    private String brift;

    @ApiModelProperty(value = "选举状态 0-未启动, 1-选举中, 2-已结束")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "规则模板编号")
    @TableField("TEMPLATE_ID")
    private Integer templateId;

    @ApiModelProperty(value = "开始时间")
    @TableField("START_TIME")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @TableField("END_TIME")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "是否删除 0-未删除,1-已删除")
    @TableField("IS_DEL")
    @TableLogic
    @JSONField(serialize = false)
    private Integer isDel;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;


}
