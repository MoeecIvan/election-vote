package test.ivan.vote.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import test.ivan.vote.common.bean.ListQueryParam;

@ApiModel("选举活动列表查询参数")
@Data
public class ElectionListParam extends ListQueryParam {
    @ApiModelProperty(value = "选举活动编号")
    private Long electionId;
}
