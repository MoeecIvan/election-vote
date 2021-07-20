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
 * 候选人表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_CANDIDATE")
@ApiModel(value="Candidate对象", description="候选人表")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "候选人编号")
    @TableId(value = "CANDIDATE_ID", type = IdType.AUTO)
    private Long candidateId;

    @ApiModelProperty(value = "姓名")
    @TableField("CANDIDATE_NAME")
    private String candidateName;

    @ApiModelProperty(value = "图片")
    @TableField("IMAGE")
    private String image;

    @ApiModelProperty(value = "简介")
    @TableField("PROFILE")
    private String profile;

    @ApiModelProperty(value = "选举编号")
    @TableField("ELECTION_ID")
    private Long electionId;

    @ApiModelProperty(value = "得票数")
    @TableField("TOTAL")
    private Integer total;

    @ApiModelProperty(value = "是否赢家 0 - 否, 1-是")
    @TableField("IS_WINNER")
    private Integer isWinner;

    @ApiModelProperty(value = "创建时间")
    @TableField(value = "CREATED_TIME", fill = FieldFill.INSERT)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @TableField(value = "UPDATED_TIME", fill = FieldFill.INSERT_UPDATE)
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;


}
