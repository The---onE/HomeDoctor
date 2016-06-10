package com.xmx.homedoctor.User;

import com.xmx.homedoctor.Tools.Data.Callback.UpdateCallback;
import com.xmx.homedoctor.Tools.Data.Sync.BaseSyncEntityManager;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by The_onE on 2016/3/26.
 */
public class UserSyncManager extends BaseSyncEntityManager<User> {
    private static UserSyncManager instance;

    public synchronized static UserSyncManager getInstance() {
        if (null == instance) {
            instance = new UserSyncManager();
        }
        return instance;
    }

    private UserSyncManager() {
        setTableName("DoctorPersonal");
        setEntityTemplate(new User());
        setUserField("doctor");
    }

    public void updateUser(String cloudId, String name, String gender, Date birthday, double height
            , double weight, String idNumber, String phone, String email, String address
            , UpdateCallback callback) {
        if (!checkDatabase()) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("name", name);
        map.put("gender", gender);
        map.put("birthday", birthday);
        map.put("height", height);
        map.put("weight", weight);
        map.put("idNumber", idNumber);
        map.put("phone", phone);
        map.put("email", email);
        map.put("address", address);
        updateData(cloudId, map, callback);
    }
}
