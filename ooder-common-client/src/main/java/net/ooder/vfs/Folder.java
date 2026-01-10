/**
 * $RCSfile: Folder.java,v $
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

import com.alibaba.fastjson.annotation.JSONField;
import net.ooder.annotation.*;
import net.ooder.common.FolderState;
import net.ooder.common.FolderType;
import net.ooder.common.cache.Cacheable;
import net.ooder.esd.annotation.ViewType;

import java.util.List;
import java.util.Set;

@ESDEntity
public interface Folder extends Cacheable {

    @MethodChinaName(cname = "取得文件夹标识")
    @Uid
    public String getID();

    @MethodChinaName(cname = "取得该文件夹所有拥有的文件列表")
    public Set<String> getFileIdList();

    @MethodChinaName(cname = "取得文件夹名称")
    public String getName();

    @MethodChinaName(cname = "取得文件夹资源逻辑地址")
    public String getPath();

    @MethodChinaName(cname = "取得父文件夹的标识")
    @Pid
    public String getParentId();

    @MethodChinaName(cname = "取得文件夹创建人")
    public String getPersonId();

    @MethodChinaName(cname = "设置文件标示", display = false)
    public void setName(String name);

    @MethodChinaName(cname = "设置父节点", display = false)
    public void setParentId(String parentId);

    @MethodChinaName(cname = "获取人员id", display = false)
    public void setPersonId(String personId);

    @MethodChinaName(cname = "设置文件类型", display = false)
    public void setFolderType(FolderType type);

    @MethodChinaName(cname = "获取文件类型", display = false)
    public FolderType getFolderType();

    @MethodChinaName(cname = "文件夹大小", display = false)
    public Long getFolderSize();

    @MethodChinaName(cname = "排序", display = false)
    public int getOrderNum();

    @MethodChinaName(cname = "设置排序", display = false)
    public void setOrderNum(int orderNum);

    @MethodChinaName(cname = "是否删除", display = false)
    public int getRecycle();

    @MethodChinaName(cname = "状态", display = false)
    public void setState(FolderState state);

    @MethodChinaName(cname = "状态", display = false)
    public FolderState getState();

    @MethodChinaName(cname = "创建时间")
    public long getCreateTime();


    @JSONField(serialize = false)
    @MethodChinaName(cname = "取得父文件夹对象")
    @Ref(ref = RefType.O2O, view = ViewType.DIC)
    public Folder getParent();

//    
//    @MethodChinaName(cname = "取得当前所有父节点（递归）", display = false)
//    public List<String> getAllParentIdList();


//    @MethodChinaName(cname = "取得当前所有父节点（递归）", display = false)
//    public List<Folder> getAllParent();
//    


    @MethodChinaName(cname = "取得该应用的所有直接子节点")
    public Set<String> getChildrenIdList();


    @JSONField(serialize = false)
    @MethodChinaName(cname = "取得该应用的所有直接子节点")
    public List<Folder> getChildrenList();


    @JSONField(serialize = false)
    @MethodChinaName(cname = "取得该应用的所有子文件（递归)", display = false)
    @Ref(ref = RefType.REF, view = ViewType.GRID)
    public List<Folder> getChildrenRecursivelyList();


    @JSONField(serialize = false)
    @MethodChinaName(cname = "取得该文件夹所有拥有的文件列表")
    @Ref(ref = RefType.O2M, view = ViewType.GRID)
    public List<FileInfo> getFileList();


    @JSONField(serialize = false)
    @MethodChinaName(cname = "取得该文件夹所有拥有的文件列表(递归)", display = false)
    @Ref(ref = RefType.FIND, view = ViewType.GRID)
    public List<FileInfo> getFileListRecursively();


    //   public void resotre();

    @MethodChinaName(cname = "增加子文件")
    public FileInfo createFile(String name, String createPersonId);

    @MethodChinaName(cname = "增加子文件")
    public FileInfo createFile(String name, String description, String createPersonId);

    @MethodChinaName(cname = "增加子文件夹")
    public Folder createChildFolder(String name, String createPersonId);

    @MethodChinaName(cname = "增加子文件夹")
    public Folder createChildFolder(String name, String description, String createPersonId);

    @MethodChinaName(cname = "描述")
    public String getDescription();

    public void setDescription(String description);

    public String getSystemCode();

    public void setSystemCode(String sysCode);

    public void setIndex(int index);

    public int getIndex();

    public Long getUpdateTime();


    public int getCachedSize();

    public void setHit(Integer hit);

    public Integer getHit();

}
