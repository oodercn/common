/**
 * $RCSfile: FileInfo.java,v $
 * $Revision: 1.0 $
 * $Date: 2025/08/25 $
 * <p>
 * Copyright (c) 2025 ooder.net
 * </p>
 * <p>
 * Company: ooder.net
 * </p>
 * <p>
 * License: MIT License
 * </p>
 */
package net.ooder.vfs;

import net.ooder.annotation.*;
import net.ooder.common.cache.Cacheable;
import net.ooder.common.md5.MD5InputStream;
import net.ooder.esd.annotation.ViewType;

import java.util.List;
import java.util.Set;

/**
 * vfs对外操作对象, 获取 文件实体,副本,版本,流程, 连接，视图相关信息
 */
@ESDEntity
public interface FileInfo extends Cacheable {

    @MethodChinaName(cname = "取得文件标识")
    @Uid
    public String getID();

    @MethodChinaName(cname = "取得文件名称")
    public String getName();

    @MethodChinaName(cname = "取得文件路径")
    public String getPath();

    @MethodChinaName(cname = "获取文件上传者")
    public String getPersonId();

    @MethodChinaName(cname = "获取文件类型")
    public Integer getFileType();

    @MethodChinaName(cname = "文件创建时间")
    public Long getCreateTime();
//
//    @MethodChinaName(cname = "取得拥有该文件权限的所有")
//    public List<String> getRoleIdList(RoleType type);
//
//    @MethodChinaName(cname = "取得拥有该文件权限的所有角")
//    public List<Role> getRoleList(RoleType type);

    @MethodChinaName(cname = "取得文件所有版本信息")
    @Ref(ref = RefType.O2M,view = ViewType.GRID)
    public List<FileVersion> getVersionList();

    public Set<String> getCurrentViewIds();

    @MethodChinaName(cname = "取得当前视图")
    @Ref(ref = RefType.O2M,view = ViewType.GRID)
    public List<FileView> getCurrentViews();

    @MethodChinaName(cname = "获取文件链接")
    public Set<String> getLinkIds();

    @MethodChinaName(cname = "获取文件链接")
    @Ref(ref = RefType.O2M,view = ViewType.GRID)
    public List<FileLink> getLinks();

    @MethodChinaName(cname = "文件描述")
    public String getDescription();

    public String getOldFolderId();

    // 移动使用
    public void setOldFolderId(String oldFolderId);

    public String getRight();

    @Pid
    public String getFolderId();

    @MethodChinaName(cname = "取得文件所有版本信息")
    public Set<String> getVersionIds();

    @MethodChinaName(cname = "取得当前版本id")
    public String getCurrentVersonId();

    @MethodChinaName(cname = "取得当前版本文件hash")
    public String getCurrentVersonFileHash();

    @MethodChinaName(cname = "取得当前版本")
    public FileVersion getCurrentVersion();

    @MethodChinaName(cname = "取得该文件所属所有文件夹")
    @Ref(ref = RefType.M2O,view = ViewType.DIC)
    public Folder getFolder();


    @MethodChinaName(cname = "取得当前版本文件文件流")
    public MD5InputStream getCurrentVersonInputStream();


}
