package com.xmx.homedoctor.Appointment;

import android.os.Bundle;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;
import com.xmx.homedoctor.Tools.Data.Callback.SelectCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppointmentActivity extends BaseTempActivity {
    AppointmentAdapter adapter;
    ListView appointmentList;
    boolean loadedFlag = false;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_appointment);

        appointmentList = getViewById(R.id.list_appointment);
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", Constants.STATUS_WAITING);
        AppointmentManager.getInstance().selectByCondition(map, "date", false, new SelectCallback<Appointment>() {
            @Override
            public void success(List<Appointment> appointments) {
                adapter = new AppointmentAdapter(getBaseContext(), appointments);
                appointmentList.setAdapter(adapter);
                loadedFlag = true;
            }

            @Override
            public void notInit() {
                showToast(R.string.failure);
            }

            @Override
            public void syncError(AVException e) {
                showToast(R.string.sync_failure);
            }

            @Override
            public void notLoggedIn() {
                showToast(R.string.not_loggedin);
            }

            @Override
            public void errorNetwork() {
                showToast(R.string.network_error);
            }

            @Override
            public void errorUsername() {
                showToast(R.string.username_error);
            }

            @Override
            public void errorChecksum() {
                showToast(R.string.not_loggedin);
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loadedFlag) {
            Map<String, Object> map = new HashMap<>();
            map.put("status", Constants.STATUS_WAITING);
            AppointmentManager.getInstance().selectByCondition(map, "date", false,
                    new SelectCallback<Appointment>() {
                        @Override
                        public void success(List<Appointment> appointments) {
                            adapter.setItems(appointments);
                        }

                        @Override
                        public void notInit() {
                            showToast(R.string.failure);
                        }

                        @Override
                        public void syncError(AVException e) {
                            showToast(R.string.sync_failure);
                        }

                        @Override
                        public void notLoggedIn() {
                            showToast(R.string.not_loggedin);
                        }

                        @Override
                        public void errorNetwork() {
                            showToast(R.string.network_error);
                        }

                        @Override
                        public void errorUsername() {
                            showToast(R.string.username_error);
                        }

                        @Override
                        public void errorChecksum() {
                            showToast(R.string.not_loggedin);
                        }
                    });
        }
    }
}
