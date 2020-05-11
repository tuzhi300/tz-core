package net.kuper.tz.core.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TzProperties {
    /**
     * sigar 环境目录地址
     */
    @NotNull
    private String sigarHome;
    /**
     * 静态资源地址
     */
    @NotEmpty
    private String htmlSource;

    public String getSigarHome() {
        return sigarHome;
    }

    public void setSigarHome(String sigarHome) {
        this.sigarHome = sigarHome;
    }

    public String getHtmlSource() {
        return htmlSource;
    }

    public void setHtmlSource(String htmlSource) {
        this.htmlSource = htmlSource;
    }
}
