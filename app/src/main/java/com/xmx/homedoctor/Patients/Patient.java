package com.xmx.homedoctor.Patients;

/**
 * Created by The_onE on 2016/4/1.
 */
public class Patient {
    String id = null;
    String username = null;
    String nickname = null;

    public Patient(String i, String un, String nn) {
        id = i;
        username = un;
        nickname = nn;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getNickname() {
        return nickname;
    }

    @Override
    public boolean equals(Object o) {
        Patient other = (Patient) o;
        return id.equals(other.id);
    }
}
