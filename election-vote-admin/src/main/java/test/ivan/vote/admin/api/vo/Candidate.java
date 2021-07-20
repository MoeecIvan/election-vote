package test.ivan.vote.admin.api.vo;

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
@ApiModel(value="Candidate对象", description="候选人表")
public class Candidate implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "候选人编号")
    private Long candidateId;

    @ApiModelProperty(value = "姓名")
    private String candidateName;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "简介")
    private String profile;

    @ApiModelProperty(value = "选举编号")
    private Long electionId;

    @ApiModelProperty(value = "得票数")
    private Integer total;

    @ApiModelProperty(value = "是否赢家 0 - 否, 1-是")
    private Integer isWinner;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;

    @ApiModelProperty(value = "更新时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updatedTime;


}
