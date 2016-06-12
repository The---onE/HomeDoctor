package com.xmx.homedoctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xmx.homedoctor.Appointment.AppointmentActivity;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.Patients.Patient;
import com.xmx.homedoctor.Patients.PatientDetailActivity;
import com.xmx.homedoctor.Patients.PatientsActivity;
import com.xmx.homedoctor.Patients.PatientsAdapter;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cn.bingoogolapple.refreshlayout.BGANormalRefreshViewHolder;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import cn.bingoogolapple.refreshlayout.BGARefreshViewHolder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseFragment {

    @Override
    protected View getContentView(LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initView(View view) {
        TextView dateView = (TextView) view.findViewById(R.id.tv_date);
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("MM月dd日", Locale.getDefault());
        dateView.setText(df.format(now));
    }

    @Override
    protected void setListener(View view) {
        ImageView schedule = (ImageView) view.findViewById(R.id.btn_patients);
        schedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PatientsActivity.class);
            }
        });

        ImageView prescription = (ImageView) view.findViewById(R.id.btn_appointment);
        prescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AppointmentActivity.class);
            }
        });
    }

    @Override
    protected void processLogic(final View view, Bundle savedInstanceState) {
        AVQuery<AVObject> query = new AVQuery<>("PatientsData");
        query.findInBackground(new FindCallback<AVObject>() {
            public void done(List<AVObject> avObjects, AVException e) {
                if (e == null) {
                    TextView flag = (TextView) view.findViewById(R.id.tv_flag);
                    flag.setText("病人数:" + avObjects.size());
                } else {
                    filterException(e);
                }
            }
        });
    }
}
