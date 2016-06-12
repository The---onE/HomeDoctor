package com.xmx.homedoctor.Prescription;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.bigkoo.pickerview.TimePickerView;
import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.ActivityBase.BaseTempActivity;
import com.xmx.homedoctor.Tools.Data.Callback.InsertCallback;
import com.xmx.homedoctor.Tools.Data.Callback.SelectCallback;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class PrescriptionActivity extends BaseTempActivity {
    Prescription pre = null;
    String id;
    TextView timeTextView;
    Date time = new Date();
    TimePickerView pvTime;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_prescritpion);

        id = getIntent().getStringExtra("id");
    }

    @Override
    protected void setListener() {
        timeTextView = getViewById(R.id.tv_time);
        time.setHours(0);
        time.setMinutes(0);
        pvTime = new TimePickerView(this, TimePickerView.Type.HOURS_MINS);
        pvTime.setTime(time);
        pvTime.setCancelable(true);
        pvTime.setCyclic(true);
        pvTime.setOnTimeSelectListener(new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date) {
                DateFormat df = new SimpleDateFormat("HH:mm", Locale.getDefault());
                timeTextView.setText(df.format(date));
                time = date;
            }
        });
        timeTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pvTime.show();
            }
        });

        ImageView send = getViewById(R.id.btn_send);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pre == null) {
                    pre = new Prescription();
                }

                pre.mHour = time.getHours();
                pre.mMinute = time.getMinutes();
                pre.mName[0] = getStr(R.id.tv_name1);
                pre.mCount[0] = getInt(R.id.tv_count1);
                pre.mName[1] = getStr(R.id.tv_name2);
                pre.mCount[1] = getInt(R.id.tv_count2);
                pre.mName[2] = getStr(R.id.tv_name3);
                pre.mCount[2] = getInt(R.id.tv_count3);
                pre.mName[3] = getStr(R.id.tv_name4);
                pre.mCount[3] = getInt(R.id.tv_count4);
                pre.mName[4] = getStr(R.id.tv_name5);
                pre.mCount[4] = getInt(R.id.tv_count5);
                pre.mPatient = id;

                PrescriptionManager.getInstance().insertToCloud(pre, new InsertCallback() {
                    @Override
                    public void success(String objectId) {
                        showToast(R.string.add_success);
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
        });
    }

    @Override
    protected void processLogic(Bundle savedInstanceState) {
        Map<String, Object> map = new HashMap<>();
        map.put("patient", id);
        PrescriptionManager.getInstance().selectByCondition(map, null, false,
                new SelectCallback<Prescription>() {
                    @Override
                    public void success(List<Prescription> prescriptions) {
                        if (prescriptions.size() > 0) {
                            pre = prescriptions.get(0);
                            ((TextView) getViewById(R.id.tv_time)).setText(String.format("%02d:%02d",
                                    pre.mHour, pre.mMinute));
                            Date t = new Date();
                            t.setHours(pre.mHour);
                            t.setMinutes(pre.mMinute);
                            time = t;
                            pvTime.setTime(time);

                            ((EditText) getViewById(R.id.tv_name1)).setText(pre.mName[0]);
                            ((EditText) getViewById(R.id.tv_count1)).setText("" + pre.mCount[0]);
                            ((EditText) getViewById(R.id.tv_name2)).setText(pre.mName[1]);
                            ((EditText) getViewById(R.id.tv_count2)).setText("" + pre.mCount[1]);
                            ((EditText) getViewById(R.id.tv_name3)).setText(pre.mName[2]);
                            ((EditText) getViewById(R.id.tv_count3)).setText("" + pre.mCount[2]);
                            ((EditText) getViewById(R.id.tv_name4)).setText(pre.mName[3]);
                            ((EditText) getViewById(R.id.tv_count4)).setText("" + pre.mCount[3]);
                            ((EditText) getViewById(R.id.tv_name5)).setText(pre.mName[4]);
                            ((EditText) getViewById(R.id.tv_count5)).setText("" + pre.mCount[4]);
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

    int getInt(int id) {
        String s = ((EditText) getViewById(id)).getText().toString();
        if (!s.equals("")) {
            return Integer.parseInt(s);
        } else {
            return 0;
        }
    }

    String getStr(int id) {
        return ((EditText) getViewById(id)).getText().toString();
    }
}
