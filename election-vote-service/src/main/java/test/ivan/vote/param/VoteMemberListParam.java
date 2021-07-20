package test.ivan.vote.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import test.ivan.vote.common.bean.ListQueryParam;

@ApiModel("投票人列表查询参数")
@Data
public class VoteMemberListParam extends ListQueryParam {
    @ApiModelProperty(value = "选举活动编号", required = true)
    private Long electionId;

    @ApiModelProperty(value = "候选人编号", required = true)
    private Long candidateId;
}
