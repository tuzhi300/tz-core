package net.kuper.tz.core.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BuildProperties {
    /**
     * 版本号
     */
    @NotEmpty
    private String version;
    /**
     * 构建时间
     */
    @NotEmpty
    private String time;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
