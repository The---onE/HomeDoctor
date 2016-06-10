package com.xmx.homedoctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xmx.homedoctor.Appointment.Appointment;
import com.xmx.homedoctor.Appointment.AppointmentAdapter;
import com.xmx.homedoctor.Appointment.AppointmentManager;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;
import com.xmx.homedoctor.Tools.Data.Callback.SelectCallback;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseFragment {
    AppointmentAdapter adapter;
    ListView appointmentList;
    boolean loadedFlag = false;

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_statistics, container, false);
    }

    @Override
    protected void initView(View view) {
        appointmentList = (ListView) view.findViewById(R.id.list_appointment);
    }

    @Override
    protected void setListener(View view) {

    }

    @Override
    protected void processLogic(View view, Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("status", Constants.STATUS_WAITING);
        AppointmentManager.getInstance().selectByCondition(map, "date", false, new SelectCallback<Appointment>() {
            @Override
            public void success(List<Appointment> appointments) {
                adapter = new AppointmentAdapter(getContext(), appointments);
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
