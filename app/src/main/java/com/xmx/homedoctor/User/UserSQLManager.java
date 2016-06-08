package com.xmx.homedoctor.User;

import com.xmx.homedoctor.Tools.Data.SQL.BaseSQLEntityManager;

/**
 * Created by The_onE on 2016/3/26.
 */
public class UserSQLManager extends BaseSQLEntityManager<UserEntity> {
    private static UserSQLManager instance;

    public synchronized static UserSQLManager getInstance() {
        if (null == instance) {
            instance = new UserSQLManager();
        }
        return instance;
    }

    private UserSQLManager() {
        tableName = "USER";
        entityTemplate = new UserEntity();

        openDatabase();
    }
}
