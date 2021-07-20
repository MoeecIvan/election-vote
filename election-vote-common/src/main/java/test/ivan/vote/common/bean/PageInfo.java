package test.ivan.vote.common.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 分页结果对象
 * @author Ivan
 * @param <T>
 */
@Data
@ApiModel("分页结果对象")
public class PageInfo<T> implements Serializable {
    @ApiModelProperty("总行数")
    @JSONField(name = "total")
    private long total = 0;

    @ApiModelProperty("数据列表")
    @JSONField(name = "items")
    private List<T> items = Collections.emptyList();

    @ApiModelProperty(value = "页码")
    @JSONField(name = "pageIndex")
    private Long pageIndex;

    @ApiModelProperty(value = "页大小")
    @JSONField(name = "pageSize")
    private Long pageSize;

    public PageInfo() {
    }

    public PageInfo(IPage<T> page) {
        this.total = page.getTotal();
        this.items = page.getRecords();
        this.pageIndex = page.getCurrent();
        this.pageSize = page.getSize();
    }
}
