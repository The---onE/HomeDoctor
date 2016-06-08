package com.xmx.homedoctor.User;

import android.content.ContentValues;
import android.database.Cursor;

import com.xmx.homedoctor.Tools.Data.SQL.ISQLEntity;

/**
 * Created by The_onE on 2016/5/25.
 */
public class UserEntity implements ISQLEntity {

    public long id = -1;
    public String name;
    public String gender;
    public long birthday;
    public double height;
    public double weight;
    public String idNumber;
    public  String phone;
    public String email;
    public String address;
    int type = 0;

    UserEntity() {
    }

    public UserEntity(long id, String name, String gender, long birthday, double height, double weight,
                      String idNumber, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.birthday = birthday;
        this.height = height;
        this.weight = weight;
        this.idNumber = idNumber;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    @Override
    public String tableFields() {
        return "ID integer not null primary key autoincrement, " +
                "NAME text not null, " +
                "GENDER text not null, " +
                "BIRTHDAY integer not null default(0), " +
                "HEIGHT real not null default(0), " +
                "WEIGHT real not null default(0), " +
                "ID_NUMBER text, " +
                "PHONE text, " +
                "EMAIL text, " +
                "ADDRESS text, " +
                "TYPE integer default(0)";
    }

    @Override
    public ContentValues getContent() {
        ContentValues content = new ContentValues();
        content.put("ID", id);
        content.put("NAME", name);
        content.put("GENDER", gender);
        content.put("BIRTHDAY", birthday);
        content.put("HEIGHT", height);
        content.put("WEIGHT", weight);
        content.put("ID_NUMBER", idNumber);
        content.put("PHONE", phone);
        content.put("EMAIL", email);
        content.put("ADDRESS", address);
        content.put("TYPE", type);
        return content;
    }

    @Override
    public UserEntity convertToEntity(Cursor c) {
        UserEntity user = new UserEntity();
        user.id = c.getInt(0);
        user.name = c.getString(1);
        user.gender = c.getString(2);
        user.birthday = c.getLong(3);
        user.height = c.getDouble(4);
        user.weight = c.getDouble(5);
        user.idNumber = c.getString(6);
        user. phone = c.getString(7);
        user.email = c.getString(8);
        user.address = c.getString(9);
        user.type = c.getInt(10);
        return user;
    }
}
