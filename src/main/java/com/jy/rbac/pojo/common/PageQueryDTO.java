package com.jy.rbac.pojo.common;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
@Schema(name = "分页查询参数")
@Data
public class PageQueryDTO {

    @Schema(description = "页码，从 1 开始", example = "1")
    private Integer page = 1;

    @Schema(description = "每页条数，最大 100", example = "10")
    private Integer pageSize = 10;

    /**
     * 修正后的页码：小于 1 按 1 处理
     */
    public int getPageNum() {
        return Math.max(page, 1);
    }

    /**
     * 修正后的每页条数：小于 1 按 1 处理，大于 100 按 100 处理
     */
    public int getPageSizeNum() {
        return Math.min(Math.max(pageSize, 1), 100);
    }
}
