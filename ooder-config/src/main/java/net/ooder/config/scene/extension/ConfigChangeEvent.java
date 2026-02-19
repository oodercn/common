package net.ooder.config.scene.extension;

import java.io.Serializable;

public class ConfigChangeEvent implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private String sceneId;
    private String configPath;
    private Object oldValue;
    private Object newValue;
    private long timestamp;
    private String source;
    
    public ConfigChangeEvent() {
        this.timestamp = System.currentTimeMillis();
    }
    
    public ConfigChangeEvent(String sceneId, String configPath, Object oldValue, Object newValue) {
        this();
        this.sceneId = sceneId;
        this.configPath = configPath;
        this.oldValue = oldValue;
        this.newValue = newValue;
    }
    
    public String getSceneId() {
        return sceneId;
    }
    
    public void setSceneId(String sceneId) {
        this.sceneId = sceneId;
    }
    
    public String getConfigPath() {
        return configPath;
    }
    
    public void setConfigPath(String configPath) {
        this.configPath = configPath;
    }
    
    public Object getOldValue() {
        return oldValue;
    }
    
    public void setOldValue(Object oldValue) {
        this.oldValue = oldValue;
    }
    
    public Object getNewValue() {
        return newValue;
    }
    
    public void setNewValue(Object newValue) {
        this.newValue = newValue;
    }
    
    public long getTimestamp() {
        return timestamp;
    }
    
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public boolean hasChange() {
        if (oldValue == null && newValue == null) {
            return false;
        }
        if (oldValue == null || newValue == null) {
            return true;
        }
        return !oldValue.equals(newValue);
    }
    
    @Override
    public String toString() {
        return "ConfigChangeEvent{" +
            "sceneId='" + sceneId + '\'' +
            ", configPath='" + configPath + '\'' +
            ", oldValue=" + oldValue +
            ", newValue=" + newValue +
            ", timestamp=" + timestamp +
            '}';
    }
}
