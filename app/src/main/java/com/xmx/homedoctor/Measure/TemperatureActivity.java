package com.xmx.homedoctor.Measure;

import android.os.Bundle;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.xmx.homedoctor.Measure.ChartView.TemperatureChartView;
import com.xmx.homedoctor.Measure.Data.Temperature.Temperature;
import com.xmx.homedoctor.Measure.Data.Temperature.TemperatureManager;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;
import com.xmx.homedoctor.Tools.Data.Callback.SelectCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class TemperatureActivity extends BaseTempActivity {
    TextView dataView;
    boolean loadFlag = false;
    String id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_temperature);

        setTitle(getString(R.string.temperature));
        dataView = getViewById(R.id.tv_temperature);
        dataView.setText("同步中…");

        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void setListener() {
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("Patient", id);
        TemperatureManager.getInstance().syncFromCloud(map, new SelectCallback<Temperature>() {
            @Override
            public void success(List<Temperature> temperatureList) {
                loadFlag = true;
                Temperature temperature = TemperatureManager.getInstance()
                        .getSQLManager().selectLatest("Time", false, "Patient = '" + id + "'");

                if (temperature != null) {
                    TextView dataView = getViewById(R.id.tv_temperature);
                    dataView.setText("" + temperature.mTemperature);
                    TextView dateView = getViewById(R.id.tv_date);
                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
                    dateView.setText(df.format(temperature.mTime));
                } else {
                    dataView.setText("还未测量");
                }

                TemperatureChartView temperatureChartView = getViewById(R.id.spline_chart);
                Date now = new Date();
                temperatureChartView.setLabels(now.getMonth() + 1, now.getDate());
                double[] a = new double[7];
                for (int i = 0; i < 7; ++i) {
                    int year = now.getYear() + 1900;
                    int month = now.getMonth() + 1;
                    int day = now.getDate();
                    Temperature temp = TemperatureManager.getInstance()
                            .selectByDate(id, year, month, day);
                    if (temp != null) {
                        a[i] = temp.mTemperature;
                    } else {
                        a[i] = -1;
                    }
                    now.setDate(now.getDate() - 1);
                }
                temperatureChartView.setDataSet(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);
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
    protected void onResume() {
        super.onResume();
        if (loadFlag) {
            Temperature temperature = TemperatureManager.getInstance()
                    .getSQLManager().selectLatest("Time", false);

            if (temperature != null) {
                TextView dataView = getViewById(R.id.tv_temperature);
                dataView.setText("" + temperature.mTemperature);
                TextView dateView = getViewById(R.id.tv_date);
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
                dateView.setText(df.format(temperature.mTime));
            } else {
                dataView.setText("还未测量");
            }

            TemperatureChartView temperatureChartView = getViewById(R.id.spline_chart);
            Date now = new Date();
            temperatureChartView.setLabels(now.getMonth() + 1, now.getDate());
            double[] a = new double[7];
            for (int i = 0; i < 7; ++i) {
                int year = now.getYear() + 1900;
                int month = now.getMonth() + 1;
                int day = now.getDate();
                Temperature temp = TemperatureManager.getInstance()
                        .selectByDate(id, year, month, day);
                if (temp != null) {
                    a[i] = temp.mTemperature;
                } else {
                    a[i] = -1;
                }
                now.setDate(now.getDate() - 1);
            }
            temperatureChartView.setDataSet(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);
        }
    }
}
