package net.kuper.tz.core.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class AdminProperties {
    /**
     * 超级角色ID
     */
    @NotEmpty
    private String superRoleId;
    /**
     * system管理员ID
     */
    @NotEmpty
    private String systemUserId;


}

