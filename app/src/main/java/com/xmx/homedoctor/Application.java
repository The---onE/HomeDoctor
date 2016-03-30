package com.xmx.homedoctor;

import com.xmx.homedoctor.Tools.Data.DataManager;

/**
 * Created by The_onE on 2016/1/3.
 */
public class Application extends android.app.Application {
    @Override
    public void onCreate() {
        super.onCreate();

        DataManager.getInstance().setContext(this);
    }
}
