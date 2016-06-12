package com.xmx.homedoctor.Patients;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.xmx.homedoctor.Measure.BloodPressureActivity;
import com.xmx.homedoctor.Measure.Data.BloodPressure.BloodPressure;
import com.xmx.homedoctor.Measure.Data.BloodPressure.BloodPressureManager;
import com.xmx.homedoctor.Measure.Data.HeartRate.HeartRate;
import com.xmx.homedoctor.Measure.Data.HeartRate.HeartRateManager;
import com.xmx.homedoctor.Measure.Data.Pules.Pules;
import com.xmx.homedoctor.Measure.Data.Pules.PulesManager;
import com.xmx.homedoctor.Measure.Data.Temperature.Temperature;
import com.xmx.homedoctor.Measure.Data.Temperature.TemperatureManager;
import com.xmx.homedoctor.Measure.HeartRateActivity;
import com.xmx.homedoctor.Measure.PulesActivity;
import com.xmx.homedoctor.Measure.TemperatureActivity;
import com.xmx.homedoctor.Prescription.PrescriptionActivity;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Record.AddRecordActivity;
import com.xmx.homedoctor.Record.ShowRecordActivity;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;
import com.xmx.homedoctor.Tools.Data.Callback.SelectCallback;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

        getViewById(R.id.card_blood_pressure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(BloodPressureActivity.class, "id", id);
            }
        });

        getViewById(R.id.card_heart_rate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(HeartRateActivity.class, "id", id);
            }
        });

        getViewById(R.id.card_pules).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(PulesActivity.class, "id", id);
            }
        });

        getViewById(R.id.card_temperature).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(TemperatureActivity.class, "id", id);
            }
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("Patient", id);
        BloodPressureManager.getInstance().syncFromCloud(map, new SelectCallback<BloodPressure>() {
            @Override
            public void success(List<BloodPressure> bloodPressures) {
                BloodPressure bloodPressure = BloodPressureManager.getInstance().selectToday(id);
                if (bloodPressure != null) {
                    TextView dataView = getViewById(R.id.data_blood_pressure);
                    dataView.setText(String.format("%.0f/%.0f",
                            bloodPressure.mPressureHigh, bloodPressure.mPressureLow));
                }
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

        HeartRateManager.getInstance().syncFromCloud(map, new SelectCallback<HeartRate>() {
            @Override
            public void success(List<HeartRate> heartRates) {
                HeartRate heartRate = HeartRateManager.getInstance().selectToday(id);
                if (heartRate != null) {
                    TextView dataView = getViewById(R.id.data_heart_rate);
                    dataView.setText("" + heartRate.mRate);
                }
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

        PulesManager.getInstance().syncFromCloud(map, new SelectCallback<Pules>() {
            @Override
            public void success(List<Pules> pulesList) {
                Pules pules = PulesManager.getInstance().selectToday(id);
                if (pules != null) {
                    TextView dataView = getViewById(R.id.data_pules);
                    dataView.setText("" + pules.mPules);
                }
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

        TemperatureManager.getInstance().syncFromCloud(map, new SelectCallback<Temperature>() {
            @Override
            public void success(List<Temperature> temperatures) {
                Temperature temperature = TemperatureManager.getInstance().selectToday(id);
                if (temperature != null) {
                    TextView dataView = getViewById(R.id.data_temperature);
                    dataView.setText("" + temperature.mTemperature);
                }
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
