package com.xmx.homedoctor.Patients;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xmx.homedoctor.Tools.Data.BaseSQLManager;

/**
 * Created by The_onE on 2016/3/31.
 */
public class PatientsSQLManager extends BaseSQLManager {
    private static PatientsSQLManager instance;

    public synchronized static PatientsSQLManager getInstance() {
        if (null == instance) {
            instance = new PatientsSQLManager();
        }
        return instance;
    }

    private PatientsSQLManager() {
        openDatabase();
    }

    public static long getId(Cursor c) {
        return c.getInt(0);
    }

    @Override
    protected boolean openDatabase() {
        SQLiteDatabase database = openSQLFile();
        if (database != null) {
            String createSQL = "create table if not exists PATIENTS(" +
                    "ID integer not null primary key autoincrement, " +
                    "NAME text not null, " +
                    "GENDER text not null, " +
                    "BIRTHDAY integer not null default(0), " +
                    "HEIGHT real not null default(0), " +
                    "WEIGHT real not null default(0), " +
                    "ID_NUMBER text, " +
                    "PHONE text, " +
                    "EMAIL text, " +
                    "ADDRESS text, " +
                    "DOCTOR_ID integer default(-1), " +
                    "TYPE integer default(0)" +
                    ")";
            database.execSQL(createSQL);
            openFlag = true;
        } else {
            openFlag = false;
        }
        return openFlag;
    }
}
