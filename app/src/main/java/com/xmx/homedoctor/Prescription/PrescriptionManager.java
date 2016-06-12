package com.xmx.homedoctor.Prescription;

import com.xmx.homedoctor.Tools.Data.Cloud.BaseCloudEntityManager;

/**
 * Created by The_onE on 2016/3/26.
 */
public class PrescriptionManager extends BaseCloudEntityManager<Prescription> {
    private static PrescriptionManager instance;

    public synchronized static PrescriptionManager getInstance() {
        if (null == instance) {
            instance = new PrescriptionManager();
        }
        return instance;
    }

    private PrescriptionManager() {
        tableName = "Prescription";
        entityTemplate = new Prescription();
    }
}
