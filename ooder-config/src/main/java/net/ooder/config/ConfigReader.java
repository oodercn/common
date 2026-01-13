package net.ooder.config;

import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertySources;
import org.springframework.boot.env.EnvironmentPostProcessorApplicationListener;
import org.springframework.boot.env.PropertySourcesLoader;
import org.springframework.core.env.*;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 独立配置读取器
 * 用于在非Spring环境中读取配置文件
 */
public class ConfigReader {
    
    private static final String DEFAULT_CONFIG_FILE = "application.properties";
    
    /**
     * 读取默认配置文件（application.properties）
     * @return DatabaseProperties 数据库配置属性
     */
    public static DatabaseProperties readDatabaseConfig() {
        return readDatabaseConfig(DEFAULT_CONFIG_FILE);
    }
    
    /**
     * 读取指定配置文件
     * @param configFile 配置文件路径
     * @return DatabaseProperties 数据库配置属性
     */
    public static DatabaseProperties readDatabaseConfig(String configFile) {
        try {
            // 创建PropertySources
            MutablePropertySources propertySources = new MutablePropertySources();
            
            // 加载配置文件
            Resource resource = new ClassPathResource(configFile);
            if (resource.exists()) {
                PropertySourcesLoader loader = new PropertySourcesLoader();
                List<PropertySource<?>> loadedSources = loader.load(configFile, resource);
                for (PropertySource<?> source : loadedSources) {
                    propertySources.addLast(source);
                }
            }
            
            // 添加系统属性和环境变量
            propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", System.getenv()));
            propertySources.addLast(new PropertiesPropertySource("systemProperties", System.getProperties()));
            
            // 创建Binder并绑定配置
            Binder binder = new Binder(ConfigurationPropertySources.from(propertySources));
            Bindable<DatabaseProperties> bindable = Bindable.of(DatabaseProperties.class);
            
            return binder.bind("ooder.database", bindable).orElse(new DatabaseProperties());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read database configuration", e);
        }
    }
    
    /**
     * 读取多个配置文件
     * @param configFiles 配置文件路径列表
     * @return DatabaseProperties 数据库配置属性
     */
    public static DatabaseProperties readDatabaseConfig(List<String> configFiles) {
        try {
            // 创建PropertySources
            MutablePropertySources propertySources = new MutablePropertySources();
            
            // 加载所有配置文件
            PropertySourcesLoader loader = new PropertySourcesLoader();
            for (String configFile : configFiles) {
                Resource resource = new ClassPathResource(configFile);
                if (resource.exists()) {
                    List<PropertySource<?>> loadedSources = loader.load(configFile, resource);
                    for (PropertySource<?> source : loadedSources) {
                        propertySources.addLast(source);
                    }
                }
            }
            
            // 添加系统属性和环境变量
            propertySources.addLast(new SystemEnvironmentPropertySource("systemEnvironment", System.getenv()));
            propertySources.addLast(new PropertiesPropertySource("systemProperties", System.getProperties()));
            
            // 创建Binder并绑定配置
            Binder binder = new Binder(ConfigurationPropertySources.from(propertySources));
            Bindable<DatabaseProperties> bindable = Bindable.of(DatabaseProperties.class);
            
            return binder.bind("ooder.database", bindable).orElse(new DatabaseProperties());
        } catch (IOException e) {
            throw new RuntimeException("Failed to read database configuration", e);
        }
    }
}