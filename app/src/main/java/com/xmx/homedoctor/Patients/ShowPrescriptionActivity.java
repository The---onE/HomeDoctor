package com.xmx.homedoctor.Patients;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ListView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShowPrescriptionActivity extends BaseTempActivity {
    ListView prescriptionList;
    List<Prescription> prescriptions = new ArrayList<>();
    Context context;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_prescription);

        String id = getIntent().getStringExtra("id");

        prescriptionList = getViewById(R.id.prescription_list);
        context = this;

        AVQuery<AVObject> query = new AVQuery<>("Prescription");
        query.whereEqualTo("patient", id);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (e == null) {
                    for (AVObject item : list) {
                        long id = 0;
                        String title = item.getString("title");
                        Date date = item.getDate("date");
                        long time = date.getTime();
                        String text = item.getString("text");
                        String suggestion = item.getString("suggestion");
                        int status = item.getInt("status");
                        int type = item.getInt("type");
                        Prescription r = new Prescription(id, title, time, text, suggestion, status, type);
                        prescriptions.add(r);
                    }

                    PrescriptionAdapter adapter = new PrescriptionAdapter(context, prescriptions);
                    prescriptionList.setAdapter(adapter);
                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
