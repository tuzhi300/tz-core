package net.kuper.tz.core.mybatis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Getter
@Setter
public class PaginationQuery extends SortQuery implements Serializable {

    @ApiModelProperty(value = "页码", position = 501)
    @Min(value = 1, message = "页码最小值1")
    private int page = 1;
    @ApiModelProperty(value = "每页最多条数 max=200", notes = "Minimum 1 maximum 200", position = 502)
    @Max(value = 200, message = "最多查询200条数据")
    @Min(value = 1, message = "最小查询一条数据")
    private int pageSize = 10;


}
