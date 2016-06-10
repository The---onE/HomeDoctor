package com.xmx.homedoctor.Tools.Data.Sync;


import com.xmx.homedoctor.Tools.Data.Cloud.ICloudEntity;
import com.xmx.homedoctor.Tools.Data.SQL.ISQLEntity;

/**
 * Created by The_onE on 2016/5/29.
 */
public interface ISyncEntity extends ICloudEntity, ISQLEntity {
    public String getCloudId();
    public void setCloudId(String id);
}
