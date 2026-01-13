/**
 * $RCSfile: Msg.java,v $
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
package net.ooder.msg;


import net.ooder.common.MsgStatus;

import java.io.Serializable;

/**
 * <p>
 * Title: ooder组织机构
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * </p>
 * <p>
 * Copyright: Copyright (c) 2025-2015
 * </p>
 * <p>
 * Company: raddev.cn
 * </p>
 *
 * @author  ooder
 * @version 1.0
 */
public interface Msg extends Serializable {


    public String getType();

    public void setType(String type);

    public Long getArrivedTime();

    public void setArrivedTime(Long date);

    public String getBody();

    public void setBody(String body);

    public String getFrom();

    public void setFrom(String personId);

    public String getId();

    public void setId(String id);

    public String getReceiver();

    public void setReceiver(String receiverId);

    public Long getReceiveTime();

    public void setReceiveTime(Long date);

    public MsgStatus getStatus();

    public void setStatus(MsgStatus status);


    public String getTitle();

    public void setTitle(String title);

    public Integer getTimes();

    public void setTimes(Integer times);

    public Long getEventTime();

    public void setEventTime(Long time);

    public String getSystemCode();

    public void setSystemCode(String systemCode);


    ;


}