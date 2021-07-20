package test.ivan.vote.common.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.commons.lang3.StringUtils;
import test.ivan.vote.common.bean.ListQueryParam;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * 处理排序工具类
 */
@SuppressWarnings("unchecked")
public class SortUtil {
    // 排序规则： descend 降序
    public static final String ORDER_DESC = "desc";
    // 排序规则： ascend 升序
    public static final String ORDER_ASC = "asc";

    /**
     * 处理排序（分页情况下） for mybatis-plus
     *
     * @param request           ListQueryParam
     * @param page              Page
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(ListQueryParam request, Page page, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        page.setCurrent(request.getPageIndex());
        page.setSize(request.getPageSize());
        String sortField = request.getSortField();
        if (camelToUnderscore) {
            sortField = SortUtil.camelToUnderscore(sortField);
            defaultSort = SortUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(request.getSortField())
                && StringUtils.isNotBlank(request.getSortOrder())
                && !StringUtils.equalsIgnoreCase(request.getSortField(), "undefined")
                && !StringUtils.equalsIgnoreCase(request.getSortOrder(), "undefined")) {
            OrderItem item = new OrderItem();
            item.setColumn(sortField);

            item.setAsc(StringUtils.equals(request.getSortOrder(), SortUtil.ORDER_ASC));
            List<OrderItem> orderItems = new ArrayList<>();
            orderItems.add(item);
            page.setOrders(orderItems);
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                OrderItem item = new OrderItem();
                item.setColumn(defaultSort);

                item.setAsc(StringUtils.equals(defaultOrder, SortUtil.ORDER_ASC));
                List<OrderItem> orderItems = new ArrayList<>();
                orderItems.add(item);
                page.setOrders(orderItems);
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request ListQueryParam
     * @param page    Page
     */
    public static void handlePageSort(ListQueryParam request, Page page) {
        handlePageSort(request, page, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request           ListQueryParam
     * @param page              Page
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handlePageSort(ListQueryParam request, Page page, boolean camelToUnderscore) {
        handlePageSort(request, page, null, null, camelToUnderscore);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request           ListQueryParam
     * @param wrapper           wrapper
     * @param defaultSort       默认排序的字段
     * @param defaultOrder      默认排序规则
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(ListQueryParam request, QueryWrapper wrapper, String defaultSort, String defaultOrder, boolean camelToUnderscore) {
        String sortField = request.getSortField();
        if (camelToUnderscore) {
            sortField = SortUtil.camelToUnderscore(sortField);
            defaultSort = SortUtil.camelToUnderscore(defaultSort);
        }
        if (StringUtils.isNotBlank(request.getSortField())
                && StringUtils.isNotBlank(request.getSortOrder())
                && !StringUtils.equalsIgnoreCase(request.getSortField(), "undefined")
                && !StringUtils.equalsIgnoreCase(request.getSortOrder(), "undefined")) {
            if (StringUtils.equals(request.getSortOrder(), SortUtil.ORDER_DESC))
                wrapper.orderByDesc(sortField);
            else
                wrapper.orderByAsc(sortField);
        } else {
            if (StringUtils.isNotBlank(defaultSort)) {
                if (StringUtils.equals(defaultOrder, SortUtil.ORDER_DESC))
                    wrapper.orderByDesc(defaultSort);
                else
                    wrapper.orderByAsc(defaultSort);
            }
        }
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request ListQueryParam
     * @param wrapper wrapper
     */
    public static void handleWrapperSort(ListQueryParam request, QueryWrapper wrapper) {
        handleWrapperSort(request, wrapper, null, null, false);
    }

    /**
     * 处理排序 for mybatis-plus
     *
     * @param request           ListQueryParam
     * @param wrapper           wrapper
     * @param camelToUnderscore 是否开启驼峰转下划线
     */
    public static void handleWrapperSort(ListQueryParam request, QueryWrapper wrapper, boolean camelToUnderscore) {
        handleWrapperSort(request, wrapper, null, null, camelToUnderscore);
    }

    /**
     * 驼峰转下划线
     *
     * @param value 待转换值
     * @return 结果
     */
    public static String camelToUnderscore(String value) {
        if (StringUtils.isBlank(value))
            return value;
        String[] arr = StringUtils.splitByCharacterTypeCamelCase(value);
        if (arr.length == 0)
            return value;
        StringBuilder result = new StringBuilder();
        IntStream.range(0, arr.length).forEach(i -> {
            if ("_".equals(arr[i])) {
                return;
            } else if (i != arr.length - 1)
                result.append(arr[i]).append(StringPool.UNDERSCORE);
            else
                result.append(arr[i]);
        });
        return StringUtils.lowerCase(result.toString());
    }
}
