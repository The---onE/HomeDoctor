package com.xmx.homedoctor.Appointment;

import com.xmx.homedoctor.Tools.Data.Cloud.BaseCloudEntityManager;

/**
 * Created by The_onE on 2016/3/27.
 */
public class AppointmentManager extends BaseCloudEntityManager<Appointment> {
    private static AppointmentManager instance;

    public synchronized static AppointmentManager getInstance() {
        if (null == instance) {
            instance = new AppointmentManager();
        }
        return instance;
    }

    private AppointmentManager() {
        tableName = "Appointment";
        entityTemplate = new Appointment();
    }
}
