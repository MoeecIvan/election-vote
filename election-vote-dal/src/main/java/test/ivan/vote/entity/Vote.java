package test.ivan.vote.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 用户投票记录表
 *
 * @author Ivan
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("BIZ_VOTE")
@ApiModel(value="Vote对象", description="用户投票记录表")
public class Vote implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编号")
    @TableId(value = "ID", type = IdType.INPUT)
    private Long id;

    @ApiModelProperty(value = "选举编号")
    @TableField("ELECTION_ID")
    private Long electionId;

    @ApiModelProperty(value = "候选人")
    @TableField("CANDIDATE_ID")
    private Long candidateId;

    @ApiModelProperty(value = "投票人编号")
    @TableField("MEMBER_ID")
    private Long memberId;

    @ApiModelProperty(value = "投票人名称")
    @TableField("VOTER_NAME")
    private String voterName;

    @ApiModelProperty(value = "头像")
    @TableField("AVATAR")
    private String avatar;

    @ApiModelProperty(value = "投票时间")
    @TableField("VOTE_TIME")
    private LocalDateTime voteTime;

    @ApiModelProperty(value = "是否通知 0-没有,1-已通知")
    @TableField("IS_NOTIFIED")
    private Integer isNotified;

    @ApiModelProperty(value = "通知时间")
    @TableField("NOTIFIED_TIME")
    private LocalDateTime notifiedTime;


}
