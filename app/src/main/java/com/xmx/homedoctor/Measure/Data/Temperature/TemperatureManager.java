package com.xmx.homedoctor.Measure.Data.Temperature;

import com.xmx.homedoctor.Tools.Data.Sync.BaseSyncEntityManager;

import java.util.Date;
import java.util.List;

/**
 * Created by The_onE on 2016/6/10.
 */
public class TemperatureManager extends BaseSyncEntityManager<Temperature> {
    private static TemperatureManager instance;

    public synchronized static TemperatureManager getInstance() {
        if (null == instance) {
            instance = new TemperatureManager();
        }
        return instance;
    }

    private TemperatureManager() {
        setTableName("Temperature");
        setEntityTemplate(new Temperature());
    }

    public Temperature selectByDate(String id, int year, int month, int day) {
        List<Temperature> list = getSQLManager().selectByCondition("Time", false,
                "Year = " + year, "Month = " + month,
                "Day = " + day, "Patient = '" + id + "'");
        if (list.size() > 0) {
            return list.get(0);
        } else {
            return null;
        }
    }

    public Temperature selectToday(String id) {
        Date time = new Date();
        return selectByDate(id, time.getYear() + 1900, time.getMonth() + 1, time.getDate());
    }
}
