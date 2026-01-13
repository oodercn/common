package net.ooder.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 数据库配置属性类
 * 使用@ConfigurationProperties注解自动绑定配置文件中的属性
 */
@Data
@Component
@ConfigurationProperties(prefix = "ooder.database")
public class DatabaseProperties {
    
    /**
     * 数据库驱动类名
     */
    private String driver;
    
    /**
     * 数据库连接URL
     */
    private String url;
    
    /**
     * 数据库用户名
     */
    private String username;
    
    /**
     * 数据库密码
     */
    private String password;
    
    /**
     * MySQL是否使用Unicode编码
     */
    private Boolean mysqlUseUnicode = false;
    
    /**
     * 数据库编码
     */
    private String encoding = "UTF-8";
    
    /**
     * 连接池配置
     */
    private Pool pool = new Pool();
    
    /**
     * 连接池配置内部类
     */
    @Data
    public static class Pool {
        
        /**
         * 最小连接数
         */
        private Integer minConnections = 1;
        
        /**
         * 最大连接数
         */
        private Integer maxConnections = 10;
        
        /**
         * 连接超时时间（毫秒）
         */
        private Integer connectionTimeout = 30000;
        
        /**
         * 最大空闲时间（毫秒）
         */
        private Integer maxIdleTime = 60000;
        
        /**
         * 检查空闲连接的周期（毫秒）
         */
        private Integer checkIdlePeriod = 30000;
        
        /**
         * 检出超时时间（毫秒）
         */
        private Integer checkoutTimeout = 30000;
    }
}