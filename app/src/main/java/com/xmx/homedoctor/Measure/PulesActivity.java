package com.xmx.homedoctor.Measure;

import android.os.Bundle;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.xmx.homedoctor.Measure.ChartView.PulesChartView;
import com.xmx.homedoctor.Measure.Data.Pules.Pules;
import com.xmx.homedoctor.Measure.Data.Pules.PulesManager;
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

public class PulesActivity extends BaseTempActivity {
    TextView dataView;
    boolean loadFlag = false;
    String id;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_pules);

        setTitle(getString(R.string.pules));
        dataView = getViewById(R.id.tv_pules);
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
        PulesManager.getInstance().syncFromCloud(map, new SelectCallback<Pules>() {
            @Override
            public void success(List<Pules> pulesList) {
                loadFlag = true;
                Pules pules = PulesManager.getInstance()
                        .getSQLManager().selectLatest("Time", false, "Patient = '" + id + "'");

                if (pules != null) {
                    TextView dataView = getViewById(R.id.tv_pules);
                    dataView.setText("" + pules.mPules);
                    TextView dateView = getViewById(R.id.tv_date);
                    DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
                    dateView.setText(df.format(pules.mTime));
                } else {
                    dataView.setText("还未测量");
                }

                PulesChartView pulesChartView = getViewById(R.id.spline_chart);
                Date now = new Date();
                pulesChartView.setLabels(now.getMonth() + 1, now.getDate());
                double[] a = new double[7];
                for (int i = 0; i < 7; ++i) {
                    int year = now.getYear() + 1900;
                    int month = now.getMonth() + 1;
                    int day = now.getDate();
                    Pules p = PulesManager.getInstance().selectByDate(id, year, month, day);
                    if (p != null) {
                        a[i] = p.mPules;
                    } else {
                        a[i] = -1;
                    }
                    now.setDate(now.getDate() - 1);
                }
                pulesChartView.setDataSet(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);
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
            Pules pules = PulesManager.getInstance()
                    .getSQLManager().selectLatest("Time", false);

            if (pules != null) {
                TextView dataView = getViewById(R.id.tv_pules);
                dataView.setText("" + pules.mPules);
                TextView dateView = getViewById(R.id.tv_date);
                DateFormat df = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());
                dateView.setText(df.format(pules.mTime));
            } else {
                dataView.setText("还未测量");
            }

            PulesChartView pulesChartView = getViewById(R.id.spline_chart);
            Date now = new Date();
            pulesChartView.setLabels(now.getMonth() + 1, now.getDate());
            double[] a = new double[7];
            for (int i = 0; i < 7; ++i) {
                int year = now.getYear() + 1900;
                int month = now.getMonth() + 1;
                int day = now.getDate();
                Pules p = PulesManager.getInstance().selectByDate(id, year, month, day);
                if (p != null) {
                    a[i] = p.mPules;
                } else {
                    a[i] = -1;
                }
                now.setDate(now.getDate() - 1);
            }
            pulesChartView.setDataSet(a[0], a[1], a[2], a[3], a[4], a[5], a[6]);
        }
    }
}
