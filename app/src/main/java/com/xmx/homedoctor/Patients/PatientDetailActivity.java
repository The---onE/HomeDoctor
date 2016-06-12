package com.xmx.homedoctor.Patients;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.xmx.homedoctor.Prescription.PrescriptionActivity;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Record.AddRecordActivity;
import com.xmx.homedoctor.Record.ShowRecordActivity;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;

public class PatientDetailActivity extends BaseTempActivity {
    String id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_patient_detail);

        id = getIntent().getStringExtra("id");

        AVQuery<AVObject> query = new AVQuery<>("PatientsData");
        query.getInBackground(id, new GetCallback<AVObject>() {
            @Override
            public void done(AVObject avObject, AVException e) {
                if (e == null) {
                    setTitle(avObject.getString("nickname"));
                } else {
                    setTitle(R.string.app_name);
                }
            }
        });
    }

    @Override
    protected void setListener() {
        Button addRecord = getViewById(R.id.btn_add_record);
        addRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddRecordActivity.class, "id", id);
            }
        });

        Button showRecord = getViewById(R.id.btn_show_record);
        showRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(ShowRecordActivity.class, "id", id);
            }
        });

        Button addPrescription = getViewById(R.id.btn_add_prescription);
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PrescriptionActivity.class, "id", id);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
