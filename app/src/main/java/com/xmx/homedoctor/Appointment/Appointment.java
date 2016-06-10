package com.xmx.homedoctor.Appointment;

import com.avos.avoscloud.AVObject;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.Tools.Data.Cloud.ICloudEntity;

import java.util.Date;

/**
 * Created by The_onE on 2016/3/27.
 */
public class Appointment implements ICloudEntity {
    public Date mTime;
    public int mType;
    public String mSymptom;
    public Date mAddTime;
    public int mStatus = Constants.STATUS_WAITING;
    public String mCloudId;
    public String mPatient;

    public Appointment() {
    }

    @Override
    public AVObject getContent(String tableName) {
        AVObject object = new AVObject(tableName);
        if (mCloudId != null) {
            object.setObjectId(mCloudId);
        }
        object.put("time", mTime);
        object.put("type", mType);
        object.put("symptom", mSymptom);
        object.put("status", mStatus);
        object.put("addTime", mAddTime);
        return object;
    }

    @Override
    public Appointment convertToEntity(AVObject object) {
        Appointment appointment = new Appointment();
        appointment.mCloudId = object.getObjectId();
        appointment.mTime = object.getDate("time");
        appointment.mSymptom = object.getString("symptom");
        appointment.mAddTime = object.getDate("addTime");
        appointment.mType = object.getInt("type");
        appointment.mStatus = object.getInt("status");
        appointment.mPatient = object.getString("patientName");
        return appointment;
    }
}
