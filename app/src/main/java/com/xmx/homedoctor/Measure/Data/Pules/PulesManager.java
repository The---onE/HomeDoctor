package com.xmx.homedoctor.Measure.Data.Pules;

import com.xmx.homedoctor.Tools.Data.Sync.BaseSyncEntityManager;

import java.util.Date;
import java.util.List;

/**
 * Created by The_onE on 2016/6/10.
 */
public class PulesManager extends BaseSyncEntityManager<Pules> {
    private static PulesManager instance;

    public synchronized static PulesManager getInstance() {
        if (null == instance) {
            instance = new PulesManager();
        }
        return instance;
    }

    private PulesManager() {
        setTableName("Pules");
        setEntityTemplate(new Pules());
    }

    public Pules selectByDate(String id, int year, int month, int day) {
        List<Pules> list = getSQLManager().selectByCondition("Time", false,
                "Year = " + year, "Month = " + month,
                "Day = " + day, "Patient = '" + id + "'");
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Pules selectToday(String id) {
        Date time = new Date();
        return selectByDate(id, time.getYear() + 1900, time.getMonth() + 1, time.getDate());
    }
}
