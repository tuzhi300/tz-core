package net.kuper.tz.core.properties;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class StorageProperties {
    /**
     * 云存储配置
     */
    @NotEmpty
    private String configParentKey;
    /**
     * 临时文件位置
     */
    @NotEmpty
    private String tmpFilePath;
    /**
     * 本地文件存储位置
     */
    @NotEmpty
    private String localFilePath;

    public String getConfigParentKey() {
        return configParentKey;
    }

    public void setConfigParentKey(String configParentKey) {
        this.configParentKey = configParentKey;
    }

    public String getTmpFilePath() {
        return tmpFilePath;
    }

    public void setTmpFilePath(String tmpFilePath) {
        this.tmpFilePath = tmpFilePath;
    }

    public String getLocalFilePath() {
        return localFilePath;
    }

    public void setLocalFilePath(String localFilePath) {
        this.localFilePath = localFilePath;
    }
}
