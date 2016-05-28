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
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class StatisticsFragment extends BaseFragment {
    AppointmentAdapter adapter;
    ListView appointmentList;
    boolean loadedFlag = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_statistics, container, false);

        appointmentList = (ListView) view.findViewById(R.id.list_appointment);

        AVQuery<AVObject> query = new AVQuery<>("Appointment");
        query.whereEqualTo("status", Constants.STATUS_WAITING);
        query.orderByDescending("date");
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    List<Appointment> appointments = new ArrayList<>();
                    for (AVObject item : list) {
                        long id = 0;
                        long time = item.getLong("time");
                        int type = item.getInt("type");
                        String symptom = item.getString("symptom");
                        long addTime = item.getLong("addTime");
                        int status = item.getInt("status");
                        String patient = item.getString("patientName");

                        Appointment a = new Appointment(id, patient, new Date(time), type, symptom,
                                new Date(addTime), status);
                        appointments.add(a);
                    }

                    adapter = new AppointmentAdapter(getContext(), appointments);
                    appointmentList.setAdapter(adapter);
                    loadedFlag = true;
                } else {
                    e.printStackTrace();
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (loadedFlag) {
            AVQuery<AVObject> query = new AVQuery<>("Appointment");
            query.whereEqualTo("status", Constants.STATUS_WAITING);
            query.orderByDescending("date");
            query.findInBackground(new FindCallback<AVObject>() {
                @Override
                public void done(List<AVObject> list, AVException e) {
                    if (e == null) {
                        List<Appointment> appointments = new ArrayList<>();
                        for (AVObject item : list) {
                            long id = 0;
                            long time = item.getLong("time");
                            int type = item.getInt("type");
                            String symptom = item.getString("symptom");
                            long addTime = item.getLong("addTime");
                            int status = item.getInt("status");
                            String patient = item.getString("patientName");

                            Appointment a = new Appointment(id, patient, new Date(time), type, symptom,
                                    new Date(addTime), status);
                            appointments.add(a);
                        }

                        adapter.setItems(appointments);
                    } else {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
