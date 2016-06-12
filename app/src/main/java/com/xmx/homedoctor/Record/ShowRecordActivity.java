package com.xmx.homedoctor.Record;

import android.content.Context;
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

public class ShowRecordActivity extends BaseTempActivity {
    ListView prescriptionList;
    List<Record> records = new ArrayList<>();
    Context context;

    @Override
    protected void initView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_show_prescription);

        String id = getIntent().getStringExtra("id");

        prescriptionList = getViewById(R.id.prescription_list);
        context = this;

        AVQuery<AVObject> query = new AVQuery<>("Record");
        query.whereEqualTo("patient", id);
        query.orderByDescending("date");
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
                        Record r = new Record(id, title, time, text, suggestion, status, type);
                        records.add(r);
                    }

                    RecordAdapter adapter = new RecordAdapter(context, records);
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
