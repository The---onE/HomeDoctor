package com.xmx.homedoctor.Patients;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.xmx.homedoctor.R;
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
        Button addPrescription = getViewById(R.id.btn_add_prescription);
        addPrescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(AddPrescriptionActivity.class, "id", id);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {

    }
}
