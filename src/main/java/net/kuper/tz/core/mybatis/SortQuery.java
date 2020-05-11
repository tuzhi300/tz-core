package net.kuper.tz.core.mybatis;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SortQuery {

    @ApiModelProperty(value = "排序字段", position = 503)
    private String sidx;

    @ApiModelProperty(value = "排序方式,可选AES，DESC，默认DESC", position = 504)
    private String stype = "DESC";

}
