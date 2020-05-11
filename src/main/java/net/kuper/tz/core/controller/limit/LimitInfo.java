package net.kuper.tz.core.controller.limit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LimitInfo {
    /**
     * 会话请求次数
     */
    private Long sessionCount;
    /**
     * 用户请求次数
     */
    private Long userCount;
}
