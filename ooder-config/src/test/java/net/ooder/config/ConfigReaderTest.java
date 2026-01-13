package net.ooder.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * 独立配置读取器测试类
 */
public class ConfigReaderTest {
    
    @Test
    public void testReadDatabaseConfigDefault() {
        // 测试默认配置文件读取（应该返回默认值）
        DatabaseProperties config = ConfigReader.readDatabaseConfig();
        
        // 验证默认值
        Assertions.assertNull(config.getDriver());
        Assertions.assertNull(config.getUrl());
        Assertions.assertNull(config.getUsername());
        Assertions.assertNull(config.getPassword());
        Assertions.assertFalse(config.getMysqlUseUnicode());
        Assertions.assertEquals("UTF-8", config.getEncoding());
        
        // 验证连接池默认值
        Assertions.assertEquals(Integer.valueOf(1), config.getPool().getMinConnections());
        Assertions.assertEquals(Integer.valueOf(10), config.getPool().getMaxConnections());
    }
    
    @Test
    public void testReadDatabaseConfigWithSystemProperties() {
        // 设置系统属性
        System.setProperty("ooder.database.driver", "com.mysql.cj.jdbc.Driver");
        System.setProperty("ooder.database.url", "jdbc:mysql://localhost:3306/testdb");
        System.setProperty("ooder.database.username", "systemuser");
        
        try {
            // 测试读取配置（应该包含系统属性）
            DatabaseProperties config = ConfigReader.readDatabaseConfig();
            
            // 验证系统属性被读取
            Assertions.assertEquals("com.mysql.cj.jdbc.Driver", config.getDriver());
            Assertions.assertEquals("jdbc:mysql://localhost:3306/testdb", config.getUrl());
            Assertions.assertEquals("systemuser", config.getUsername());
            Assertions.assertNull(config.getPassword()); // 未设置，应为null
        } finally {
            // 清理系统属性
            System.clearProperty("ooder.database.driver");
            System.clearProperty("ooder.database.url");
            System.clearProperty("ooder.database.username");
        }
    }
    
    @Test
    public void testReadDatabaseConfigMultipleFiles() {
        // 测试读取多个配置文件
        DatabaseProperties config = ConfigReader.readDatabaseConfig(Arrays.asList(
                "application.properties", 
                "test.properties"));
        
        // 应该返回默认值，因为测试文件不存在
        Assertions.assertNull(config.getDriver());
        Assertions.assertNull(config.getUrl());
    }
    
    @Test
    public void testDatabasePropertiesBuilder() {
        // 测试DatabaseProperties的构建和设置
        DatabaseProperties config = new DatabaseProperties();
        config.setDriver("org.postgresql.Driver");
        config.setUrl("jdbc:postgresql://localhost:5432/testdb");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setMysqlUseUnicode(false);
        config.setEncoding("UTF-8");
        
        // 设置连接池属性
        DatabaseProperties.Pool pool = config.getPool();
        pool.setMinConnections(5);
        pool.setMaxConnections(50);
        pool.setConnectionTimeout(10000);
        
        // 验证设置
        Assertions.assertEquals("org.postgresql.Driver", config.getDriver());
        Assertions.assertEquals("jdbc:postgresql://localhost:5432/testdb", config.getUrl());
        Assertions.assertEquals("postgres", config.getUsername());
        Assertions.assertEquals("postgres", config.getPassword());
        Assertions.assertEquals(Integer.valueOf(5), config.getPool().getMinConnections());
        Assertions.assertEquals(Integer.valueOf(50), config.getPool().getMaxConnections());
        Assertions.assertEquals(Integer.valueOf(10000), config.getPool().getConnectionTimeout());
    }
}