package test.ivan.vote.common.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

@Data
@ApiModel("分页请求参数")
public class ListQueryParam implements Serializable {
    private static final long serialVersionUID = -2706243447120683613L;

    @ApiModelProperty("分页数据大小")
    private Integer pageSize = 10;

    @ApiModelProperty("分页编号")
    private Integer pageIndex = 1;

    @ApiModelProperty("排序字段, 默认为createdTime字段")
    private String sortField = "createdTime";

    @ApiModelProperty("排序方式，值desc为降序, asc为升序")
    private String sortOrder = "desc";
}

