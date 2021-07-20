package test.ivan.vote.admin.api.vo;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(value="Election对象", description="选举表")
public class ElectionVo {
    @ApiModelProperty(value = "选举编号")
    private Long electionId;

    @ApiModelProperty(value = "选举名称", required = true)
    private String electionName;

    @ApiModelProperty(value = "简介")
    private String brift;

    @ApiModelProperty(value = "选举状态 0-未启动, 1-选举中, 2-已结束")
    private Integer status;

    @ApiModelProperty(value = "规则模板编号")
    private Integer templateId;

    @ApiModelProperty(value = "开始时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    @ApiModelProperty(value = "结束时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "创建时间")
    @JSONField(format = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createdTime;
}
