# 过时方法和类记录

## 1. DBBeanBase类
- **位置**: ooder-database/src/main/java/net/ooder/common/database/DBBeanBase.java
- **状态**: 不建议再使用
- **注释**: "/***
 * 不建议再使用
 * @author wenzhang
 *
 */"
- **说明**: 这个类是旧的数据库访问实现，可能已被DBAgent等新的数据库访问方式替代

## 2. 旧版@MethodChinaName注解格式
- **格式**: `@MethodChinaName(cname = "xxx")`
- **推荐**: 使用新版格式 `@MethodChinaName("xxx")`
- **说明**: 旧格式虽然仍能工作，但不符合ooder v0.5规范，建议统一更新为新格式

## 3. VFS相关乱码方法
- **位置**: ooder-vfs-web模块中的多个API类
- **问题**: 存在乱码注解，如`@MethodChinaName("获取文件适配器")`
- **说明**: 这些乱码可能导致运行时解析错误，已修复为正确的中文

## 4. 旧版JDBC连接获取方式
- **位置**: DBBeanBase.java中的getConnection_Direct()方法
- **说明**: 直接使用JNDI获取连接的方式，可能已被连接池管理替代

## 5. 旧版日志记录方式
- **位置**: 多个类中的System.out.println()语句
- **说明**: 已被Log工具类替代，建议统一使用Log进行日志记录
