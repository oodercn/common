package net.ooder.config.scene.extension;

public interface ConfigChangeListener {
    
    void onConfigChanged(ConfigChangeEvent event);
    
    default int getOrder() {
        return 0;
    }
}
