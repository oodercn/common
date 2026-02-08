# Maven Central 发布手册

## 概述

本文档记录将 `ooder-common` 项目发布到 Maven Central 仓库的完整流程。

---

## 前置条件

### 1. 注册 Sonatype 账号
- 访问 https://central.sonatype.com/
- 使用 GitHub 账号登录
- 创建 Namespace（如 `net.ooder`）

### 2. 生成 User Token
- 登录 https://central.sonatype.com/
- 进入 Account -> View Account
- 点击 "Generate User Token"
- 保存生成的 Username 和 Password

### 3. 配置 GPG 密钥
```bash
# 生成 GPG 密钥
gpg --gen-key

# 列出密钥
gpg --list-keys

# 发布公钥到密钥服务器
gpg --keyserver keyserver.ubuntu.com --send-keys YOUR_KEY_ID
```

---

## 项目配置

### 1. 父 pom.xml 配置

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>net.ooder</groupId>
    <artifactId>ooder-common-all</artifactId>
    <version>2.0</version>
    <packaging>pom</packaging>

    <!-- 必填：项目名称和描述 -->
    <name>ooder-common-all</name>
    <description>ooder V1.0 MIT Open Source Version - Common utilities and base components</description>
    <url>https://github.com/oodercn/common</url>

    <!-- 必填：组织信息 -->
    <organization>
        <name>ooder</name>
        <url>http://net.ooder</url>
    </organization>

    <!-- 必填：许可证信息 -->
    <licenses>
        <license>
            <name>MIT License</name>
            <url>http://www.opensource.org/licenses/mit-license.php</url>
            <distribution>repo</distribution>
        </license>
    </licenses>

    <!-- 必填：开发者信息 -->
    <developers>
        <developer>
            <name>IhyTdX</name>
            <email>18683731@qq.com</email>
            <organization>ooder</organization>
            <organizationUrl>http://net.ooder</organizationUrl>
            <roles>
                <role>Developer</role>
            </roles>
        </developer>
    </developers>

    <!-- 必填：SCM 信息 -->
    <scm>
        <url>https://github.com/oodercn/common</url>
        <connection>scm:git:https://github.com/oodercn/common.git</connection>
        <developerConnection>scm:git:https://github.com/oodercn/common.git</developerConnection>
        <tag>v2.0</tag>
    </scm>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>8</maven.compiler.source>
        <maven.compiler.target>8</maven.compiler.target>
        
        <!-- 重要：不要跳过这些步骤 -->
        <maven.javadoc.skip>false</maven.javadoc.skip>
        <maven.source.skip>false</maven.source.skip>
        <gpg.skip>false</gpg.skip>
    </properties>

    <!-- 发布配置 -->
    <distributionManagement>
        <repository>
            <id>central</id>
            <url>https://central.sonatype.com/api/v1/publisher/deployments/upload/</url>
        </repository>
        <snapshotRepository>
            <id>central</id>
            <url>https://central.sonatype.com/api/v1/publisher/deployments/upload/</url>
        </snapshotRepository>
    </distributionManagement>

    <build>
        <plugins>
            <!-- 源码插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.2.1</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Javadoc 插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.3.2</version>
                <configuration>
                    <source>8</source>
                    <additionalJOption>-Xdoclint:none</additionalJOption>
                    <failOnError>false</failOnError>
                    <charset>UTF-8</charset>
                    <encoding>UTF-8</encoding>
                    <docencoding>UTF-8</docencoding>
                </configuration>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- GPG 签名插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.6</version>
                <executions>
                    <execution>
                        <id>sign-artifacts</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Maven Central 发布插件 -->
            <plugin>
                <groupId>org.sonatype.central</groupId>
                <artifactId>central-publishing-maven-plugin</artifactId>
                <version>0.4.0</version>
                <extensions>true</extensions>
                <configuration>
                    <publishingServerId>central</publishingServerId>
                    <tokenAuth>true</tokenAuth>
                </configuration>
            </plugin>
        </plugins>
    </build>
</project>
```

### 2. 子模块 pom.xml 配置

每个子模块都需要包含：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    
    <parent>
        <groupId>net.ooder</groupId>
        <artifactId>ooder-common-all</artifactId>
        <version>2.0</version>
        <relativePath>../pom.xml</relativePath>
    </parent>

    <artifactId>your-module-name</artifactId>
    
    <!-- 必填：子模块也需要 name 和 description -->
    <name>your-module-name</name>
    <description>Your module description</description>

    <!-- 如果继承自非本项目的 parent，需要单独配置 -->
    <!-- 参考 ooder-config/pom.xml -->
</project>
```

### 3. 特殊模块配置（如 ooder-config）

如果模块继承自 Spring Boot 等外部 parent，需要完整配置：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>2.7.0</version>
        <relativePath/>
    </parent>

    <groupId>net.ooder</groupId>
    <artifactId>ooder-config</artifactId>
    <version>2.0</version>
    
    <!-- 必填 -->
    <name>ooder-config</name>
    <description>Ooder configuration module</description>

    <!-- 完整的 build 配置 -->
    <build>
        <plugins>
            <!-- 编译插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.1</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                    <encoding>utf-8</encoding>
                </configuration>
            </plugin>

            <!-- 源码插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-source-plugin</artifactId>
                <version>3.2.1</version>
                <executions>
                    <execution>
                        <id>attach-sources</id>
                        <goals>
                            <goal>jar-no-fork</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Javadoc 插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-javadoc-plugin</artifactId>
                <version>3.3.2</version>
                <configuration>
                    <source>1.8</source>
                    <additionalJOption>-Xdoclint:none</additionalJOption>
                    <failOnError>false</failOnError>
                    <charset>UTF-8</charset>
                    <encoding>UTF-8</encoding>
                    <docencoding>UTF-8</docencoding>
                </configuration>
                <executions>
                    <execution>
                        <id>attach-javadocs</id>
                        <goals>
                            <goal>jar</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- GPG 签名插件 -->
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-gpg-plugin</artifactId>
                <version>1.6</version>
                <executions>
                    <execution>
                        <id>sign-artifacts</id>
                        <phase>verify</phase>
                        <goals>
                            <goal>sign</goal>
                        </goals>
                    </execution>
                </executions>
            </plugin>

            <!-- Maven Central 发布插件 -->
            <plugin>
                <groupId>org.sonatype.central</groupId>
                <artifactId>central-publishing-maven-plugin</artifactId>
                <version>0.4.0</version>
                <extensions>true</extensions>
                <configuration>
                    <publishingServerId>central</publishingServerId>
                    <tokenAuth>true</tokenAuth>
                </configuration>
            </plugin>
        </plugins>
    </build>

    <!-- 发布配置 -->
    <distributionManagement>
        <repository>
            <id>central</id>
            <url>https://central.sonatype.com/api/v1/publisher/deployments/upload/</url>
        </repository>
        <snapshotRepository>
            <id>central</id>
            <url>https://central.sonatype.com/api/v1/publisher/deployments/upload/</url>
        </snapshotRepository>
    </distributionManagement>
</project>
```

---

## settings.xml 配置

在 `~/.m2/settings.xml` 或项目根目录创建 `settings.xml`：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd">

    <servers>
        <!-- Maven Central 认证 - 使用 User Token -->
        <server>
            <id>central</id>
            <username>YOUR_USER_TOKEN_USERNAME</username>
            <password>YOUR_USER_TOKEN_PASSWORD</password>
        </server>
    </servers>

    <profiles>
        <profile>
            <id>gpg</id>
            <properties>
                <!-- GPG 密钥配置 -->
                <gpg.executable>gpg</gpg.executable>
                <gpg.keyname>YOUR_GPG_KEY_ID</gpg.keyname>
                <gpg.passphrase>YOUR_GPG_PASSPHRASE</gpg.passphrase>
            </properties>
        </profile>
    </profiles>

    <activeProfiles>
        <activeProfile>gpg</activeProfile>
    </activeProfiles>

</settings>
```

---

## 发布流程

### 1. 清理并编译
```bash
mvn clean compile
```

### 2. 运行测试
```bash
mvn test
```

### 3. 发布到 Maven Central
```bash
mvn clean deploy -DskipTests -s settings.xml
```

### 4. 验证发布
- 访问 https://central.sonatype.com/publishing/deployments
- 查看部署状态
- 如果显示 "Validated"，点击 "Publish" 完成发布

---

## 常见问题

### 1. 验证失败：缺少 GPG 签名
**错误信息**: `Missing Signature`

**解决方案**:
```xml
<properties>
    <gpg.skip>false</gpg.skip>
</properties>
```

### 2. 验证失败：缺少 Javadoc
**错误信息**: `Missing Javadoc`

**解决方案**:
```xml
<properties>
    <maven.javadoc.skip>false</maven.javadoc.skip>
</properties>
```

### 3. 验证失败：缺少项目名称
**错误信息**: `Missing Project Name`

**解决方案**: 在每个 pom.xml 中添加：
```xml
<name>your-project-name</name>
<description>Your project description</description>
```

### 4. 认证失败
**错误信息**: `401 Unauthorized`

**解决方案**:
- 检查 settings.xml 中的 username 和 password
- 确保使用的是 User Token，不是登录密码
- 确认 server id 与 pom.xml 中的 repository id 匹配

### 5. GPG 签名失败
**错误信息**: `GPG failed to sign data`

**解决方案**:
```bash
# 检查 GPG 密钥
gpg --list-keys

# 如果密钥不存在，重新导入
gpg --import private-key.asc

# 设置信任级别
gpg --edit-key YOUR_KEY_ID
trust
5
save
```

---

## 验证发布

发布后 10-30 分钟，可以在以下地址查看：
- https://repo1.maven.org/maven2/net/ooder/
- https://search.maven.org/search?q=g:net.ooder

---

## 版本更新流程

1. 更新所有 pom.xml 中的版本号
2. 更新父 pom.xml 中的 `<tag>`
3. 提交代码并打标签
4. 执行发布命令
5. 在 Maven Central Portal 确认发布

---

## 参考文档

- [Maven Central 发布指南](https://central.sonatype.org/publish/publish-portal-maven/)
- [Maven GPG Plugin](https://maven.apache.org/plugins/maven-gpg-plugin/)
- [Maven Source Plugin](https://maven.apache.org/plugins/maven-source-plugin/)
- [Maven Javadoc Plugin](https://maven.apache.org/plugins/maven-javadoc-plugin/)
