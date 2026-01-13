# 数据库配置迁移指南

## 1. 概述

本文档提供了从原有数据库配置系统迁移到新配置模块的详细指南，包括配置格式变更、代码修改和示例。

## 2. 新配置模块介绍

### 2.1 模块信息
- **模块名称**：ooder-config
- **坐标**：net.ooder:ooder-config:1.0.0-SNAPSHOT
- **父依赖**：Spring Boot 2.7.0
- **主要功能**：提供类型安全的配置管理，支持Spring环境和非Spring环境

### 2.2 核心类
| 类名 | 作用 | 适用环境 |
|------|------|----------|
| `DatabaseProperties` | 数据库配置属性类，使用@ConfigurationProperties注解 | Spring环境 |
| `DatabaseConfig` | 数据库配置类，用于配置Bean | Spring环境 |
| `ConfigReader` | 独立配置读取器，支持非Spring环境 | 所有环境 |

## 3. 配置格式变更

### 3.1 原有配置格式
```properties
# 原有配置格式示例
db.database.driver=com.mysql.cj.jdbc.Driver
db.database.serverURL=jdbc:mysql://localhost:3306/testdb
db.database.username=root
db.database.password=123456
db.database.minConnections=1
db.database.maxConnections=10
db.database.mysql.useUnicode=true
db.database.mysql.characterEncoding=UTF-8
```

### 3.2 新配置格式
```properties
# 新配置格式示例
ooder.database.driver=com.mysql.cj.jdbc.Driver
ooder.database.url=jdbc:mysql://localhost:3306/testdb
ooder.database.username=root
ooder.database.password=123456
ooder.database.mysql-use-unicode=true
ooder.database.encoding=UTF-8
ooder.database.pool.min-connections=1
ooder.database.pool.max-connections=10
ooder.database.pool.connection-timeout=30000
ooder.database.pool.max-idle-time=60000
ooder.database.pool.check-idle-period=30000
ooder.database.pool.checkout-timeout=30000
```

### 3.3 配置键映射表
| 原有配置键 | 新配置键 | 说明 |
|------------|----------|------|
| `[configKey].database.driver` | `ooder.database.driver` | 数据库驱动 |
| `[configKey].database.serverURL` | `ooder.database.url` | 数据库连接URL |
| `[configKey].database.username` | `ooder.database.username` | 数据库用户名 |
| `[configKey].database.password` | `ooder.database.password` | 数据库密码 |
| `[configKey].database.mysql.useUnicode` | `ooder.database.mysql-use-unicode` | MySQL是否使用Unicode |
| `[configKey].database.mysql.characterEncoding` | `ooder.database.encoding` | 数据库编码 |
| `[configKey].database.minConnections` | `ooder.database.pool.min-connections` | 最小连接数 |
| `[configKey].database.maxConnections` | `ooder.database.pool.max-connections` | 最大连接数 |
| `[configKey].database.connectionTimeout` | `ooder.database.pool.connection-timeout` | 连接超时时间 |
| `[configKey].database.maxIdleTime` | `ooder.database.pool.max-idle-time` | 最大空闲时间 |
| `[configKey].database.checkIdlePeriod` | `ooder.database.pool.check-idle-period` | 检查空闲连接周期 |
| `[configKey].database.checkOutTimeOut` | `ooder.database.pool.checkout-timeout` | 检出超时时间 |

## 4. 上层软件修改步骤

### 4.1 步骤一：添加依赖

在需要使用新配置模块的项目中添加依赖：

```xml
<dependency>
    <groupId>net.ooder</groupId>
    <artifactId>ooder-config</artifactId>
    <version>1.0.0-SNAPSHOT</version>
</dependency>
```

### 4.2 步骤二：修改配置文件

将原有配置文件按照新格式进行修改，主要包括：
1. 更改配置键前缀为`ooder.database`
2. 调整配置项名称和格式
3. 移除已有默认值的配置项

### 4.3 步骤三：替换原有DBConfig的使用

#### 4.3.1 原有代码示例
```java
// 原有代码示例
DBConfig dbConfig = new DBConfig("db");
String url = dbConfig.getServerURL();
String username = dbConfig.getUsername();
String password = dbConfig.getPassword();
int maxConnections = dbConfig.getMaxConnections();
```

#### 4.3.2 Spring环境下的新代码示例
```java
// Spring环境下的新代码示例
@Autowired
private DatabaseProperties databaseProperties;

// 使用示例
String url = databaseProperties.getUrl();
String username = databaseProperties.getUsername();
String password = databaseProperties.getPassword();
int maxConnections = databaseProperties.getPool().getMaxConnections();
```

#### 4.3.3 非Spring环境下的新代码示例
```java
// 非Spring环境下的新代码示例
DatabaseProperties databaseProperties = ConfigReader.readDatabaseConfig();

// 使用示例
String url = databaseProperties.getUrl();
String username = databaseProperties.getUsername();
String password = databaseProperties.getPassword();
int maxConnections = databaseProperties.getPool().getMaxConnections();
```

## 5. 关键示例

### 5.1 Spring环境下的数据库连接示例

```java
@Service
public class DatabaseService {
    
    @Autowired
    private DatabaseProperties databaseProperties;
    
    public Connection getConnection() throws SQLException {
        // 获取基础配置
        String url = databaseProperties.getUrl();
        String username = databaseProperties.getUsername();
        String password = databaseProperties.getPassword();
        
        // 如果是MySQL，添加Unicode参数
        if (url.contains("mysql") && databaseProperties.getMysqlUseUnicode()) {
            if (url.contains("?")) {
                url += "&useUnicode=true&characterEncoding=" + databaseProperties.getEncoding();
            } else {
                url += "?useUnicode=true&characterEncoding=" + databaseProperties.getEncoding();
            }
        }
        
        // 建立连接
        return DriverManager.getConnection(url, username, password);
    }
}
```

### 5.2 非Spring环境下的数据库连接池配置示例

```java
public class ConnectionPoolManager {
    
    private static HikariDataSource dataSource;
    
    public static synchronized void init() {
        if (dataSource == null) {
            // 读取配置
            DatabaseProperties config = ConfigReader.readDatabaseConfig();
            
            // 配置HikariCP连接池
            HikariConfig hikariConfig = new HikariConfig();
            hikariConfig.setDriverClassName(config.getDriver());
            hikariConfig.setJdbcUrl(config.getUrl());
            hikariConfig.setUsername(config.getUsername());
            hikariConfig.setPassword(config.getPassword());
            
            // 配置连接池参数
            hikariConfig.setMinimumIdle(config.getPool().getMinConnections());
            hikariConfig.setMaximumPoolSize(config.getPool().getMaxConnections());
            hikariConfig.setConnectionTimeout(config.getPool().getConnectionTimeout());
            hikariConfig.setIdleTimeout(config.getPool().getMaxIdleTime());
            
            // 初始化数据源
            dataSource = new HikariDataSource(hikariConfig);
        }
    }
    
    public static Connection getConnection() throws SQLException {
        if (dataSource == null) {
            init();
        }
        return dataSource.getConnection();
    }
}
```

### 5.3 配置监听器示例

```java
@Component
public class DatabaseConfigListener {
    
    private static final Logger logger = LoggerFactory.getLogger(DatabaseConfigListener.class);
    
    @Autowired
    private DatabaseProperties databaseProperties;
    
    @PostConstruct
    public void onConfigLoaded() {
        logger.info("数据库配置已加载：");
        logger.info("URL: {}", databaseProperties.getUrl());
        logger.info("用户名: {}", databaseProperties.getUsername());
        logger.info("最大连接数: {}", databaseProperties.getPool().getMaxConnections());
        logger.info("连接超时: {}ms", databaseProperties.getPool().getConnectionTimeout());
        
        // 可以在这里添加配置验证逻辑
        validateConfig();
    }
    
    private void validateConfig() {
        if (databaseProperties.getUrl() == null || databaseProperties.getUrl().isEmpty()) {
            throw new IllegalArgumentException("数据库URL不能为空");
        }
        if (databaseProperties.getUsername() == null || databaseProperties.getUsername().isEmpty()) {
            throw new IllegalArgumentException("数据库用户名不能为空");
        }
    }
}
```

## 6. 注意事项

1. **向后兼容性**：原有DBConfig类仍保留在ooder-common-client模块中，可以暂时继续使用，但建议逐步迁移到新配置模块。

2. **配置优先级**：新配置模块支持多层级配置，优先级从高到低为：
   - 系统属性
   - 环境变量
   - 配置文件

3. **配置验证**：新配置模块会自动验证配置项的类型，如果配置项类型不正确，会在启动时抛出异常。

4. **测试**：建议在迁移完成后，运行单元测试和集成测试，确保配置读取正常。

## 7. 常见问题

### 7.1 问题：Spring环境下无法注入DatabaseProperties
**解决方案**：确保在Spring Boot应用主类上添加了`@EnableConfigurationProperties`注解，或者在配置类上添加了`@ComponentScan`注解，扫描到ooder-config模块。

### 7.2 问题：非Spring环境下无法读取配置
**解决方案**：确保配置文件位于类路径下，或者使用绝对路径指定配置文件位置。

### 7.3 问题：配置项值为null
**解决方案**：检查配置文件中是否正确设置了配置项，或者检查配置键是否正确。

## 8. 迁移进度跟踪

| 模块 | 迁移状态 | 负责人 | 完成日期 |
|------|----------|--------|----------|
| ooder-common-client | ❌ 未开始 | - | - |
| ooder-database | ❌ 未开始 | - | - |
| ooder-server | ❌ 未开始 | - | - |
| ooder-vfs-web | ❌ 未开始 | - | - |
| ooder-org-web | ❌ 未开始 | - | - |
| ooder-index-web | ❌ 未开始 | - | - |
| ooder-msg-web | ❌ 未开始 | - | - |
| ooder-iot-webclient | ❌ 未开始 | - | - |

## 9. 联系方式

如有任何问题，请联系配置模块维护人员：
- 邮箱：dev@ooder.net
- 文档地址：https://docs.ooder.net/config-module

---

**版本**：1.0.0  
**发布日期**：2026-01-13  
**更新记录**：
- 1.0.0 (2026-01-13)：初始版本