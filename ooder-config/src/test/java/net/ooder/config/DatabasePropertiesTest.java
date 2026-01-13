package net.ooder.config;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

/**
 * 数据库配置属性测试类
 */
@SpringBootTest(classes = DatabaseConfig.class)
@TestPropertySource(properties = {
        "ooder.database.driver=com.mysql.cj.jdbc.Driver",
        "ooder.database.url=jdbc:mysql://localhost:3306/testdb",
        "ooder.database.username=testuser",
        "ooder.database.password=testpass",
        "ooder.database.mysql-use-unicode=true",
        "ooder.database.encoding=UTF-8",
        "ooder.database.pool.min-connections=2",
        "ooder.database.pool.max-connections=20",
        "ooder.database.pool.connection-timeout=60000",
        "ooder.database.pool.max-idle-time=120000",
        "ooder.database.pool.check-idle-period=60000",
        "ooder.database.pool.checkout-timeout=60000"
})
public class DatabasePropertiesTest {
    
    @Autowired
    private DatabaseProperties databaseProperties;
    
    @Autowired
    private DatabaseConfig databaseConfig;
    
    @Test
    public void testDatabasePropertiesBinding() {
        // 测试基本属性绑定
        Assertions.assertEquals("com.mysql.cj.jdbc.Driver", databaseProperties.getDriver());
        Assertions.assertEquals("jdbc:mysql://localhost:3306/testdb", databaseProperties.getUrl());
        Assertions.assertEquals("testuser", databaseProperties.getUsername());
        Assertions.assertEquals("testpass", databaseProperties.getPassword());
        Assertions.assertTrue(databaseProperties.getMysqlUseUnicode());
        Assertions.assertEquals("UTF-8", databaseProperties.getEncoding());
        
        // 测试连接池属性绑定
        Assertions.assertEquals(Integer.valueOf(2), databaseProperties.getPool().getMinConnections());
        Assertions.assertEquals(Integer.valueOf(20), databaseProperties.getPool().getMaxConnections());
        Assertions.assertEquals(Integer.valueOf(60000), databaseProperties.getPool().getConnectionTimeout());
        Assertions.assertEquals(Integer.valueOf(120000), databaseProperties.getPool().getMaxIdleTime());
        Assertions.assertEquals(Integer.valueOf(60000), databaseProperties.getPool().getCheckIdlePeriod());
        Assertions.assertEquals(Integer.valueOf(60000), databaseProperties.getPool().getCheckoutTimeout());
    }
    
    @Test
    public void testFullDatabaseUrl() {
        // 测试完整数据库URL生成
        String fullUrl = databaseConfig.getFullDatabaseUrl();
        Assertions.assertTrue(fullUrl.contains("jdbc:mysql://localhost:3306/testdb"));
        Assertions.assertTrue(fullUrl.contains("useUnicode=true"));
        Assertions.assertTrue(fullUrl.contains("characterEncoding=UTF-8"));
    }
    
    @Test
    public void testDefaultValues() {
        // 创建一个新的实例，测试默认值
        DatabaseProperties defaultProps = new DatabaseProperties();
        
        Assertions.assertNull(defaultProps.getDriver());
        Assertions.assertNull(defaultProps.getUrl());
        Assertions.assertNull(defaultProps.getUsername());
        Assertions.assertNull(defaultProps.getPassword());
        Assertions.assertFalse(defaultProps.getMysqlUseUnicode());
        Assertions.assertEquals("UTF-8", defaultProps.getEncoding());
        
        // 测试连接池默认值
        Assertions.assertEquals(Integer.valueOf(1), defaultProps.getPool().getMinConnections());
        Assertions.assertEquals(Integer.valueOf(10), defaultProps.getPool().getMaxConnections());
        Assertions.assertEquals(Integer.valueOf(30000), defaultProps.getPool().getConnectionTimeout());
        Assertions.assertEquals(Integer.valueOf(60000), defaultProps.getPool().getMaxIdleTime());
        Assertions.assertEquals(Integer.valueOf(30000), defaultProps.getPool().getCheckIdlePeriod());
        Assertions.assertEquals(Integer.valueOf(30000), defaultProps.getPool().getCheckoutTimeout());
    }
}