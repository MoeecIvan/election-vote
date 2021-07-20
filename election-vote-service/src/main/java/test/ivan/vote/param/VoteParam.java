package test.ivan.vote.param;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 会员投票请求参数
 */
@Data
public class VoteParam {
    @ApiModelProperty(value = "选举编号", required = true)
    private Long electionId;

    @ApiModelProperty(value = "候选人", required = true)
    private Long candidateId;

    @ApiModelProperty(value = "投票人编号")
    private Long memberId;
}
