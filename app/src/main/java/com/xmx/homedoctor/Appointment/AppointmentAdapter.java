package com.xmx.homedoctor.Appointment;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.GetCallback;
import com.avos.avoscloud.SaveCallback;
import com.xmx.homedoctor.Constants;
import com.xmx.homedoctor.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by The_onE on 2016/3/27.
 */
public class AppointmentAdapter extends BaseAdapter {
    List<Appointment> mItems = new ArrayList<>();
    Context mContext;

    public AppointmentAdapter(Context context, List<Appointment> items) {
        mContext = context;
        mItems = items;
    }

    public void setItems(List<Appointment> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return Math.max(mItems.size(), 1);
    }

    @Override
    public Object getItem(int position) {
        if (position < mItems.size()) {
            return mItems.get(position);
        } else {
            return null;
        }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        TextView patient;
        TextView time;
        TextView type;
        TextView symptom;
        TextView addTime;

        CardView card;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_appointment, null);
            holder = new ViewHolder();
            holder.patient = (TextView) convertView.findViewById(R.id.item_patient);
            holder.time = (TextView) convertView.findViewById(R.id.item_time);
            holder.type = (TextView) convertView.findViewById(R.id.item_type);
            holder.symptom = (TextView) convertView.findViewById(R.id.item_symptom);
            holder.addTime = (TextView) convertView.findViewById(R.id.item_add_time);
            holder.card = (CardView) convertView.findViewById(R.id.card_appointment);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < mItems.size()) {
            Appointment appointment = mItems.get(position);
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String timeString = df.format(appointment.getTime());
            holder.patient.setText(appointment.getPatient());
            holder.time.setText(timeString);
            holder.type.setText(Constants.APPOINTMENT_TYPE[appointment.getType()]);
            holder.symptom.setText(appointment.getSymptom());
            String addTimeString = df.format(appointment.getAddTime());
            holder.addTime.setText(addTimeString);

            switch (appointment.getStatus()) {
                case Constants.STATUS_CANCELED:
                    holder.card.setCardBackgroundColor(Color.GRAY);
                    break;
                default:
                    holder.card.setCardBackgroundColor(Color.WHITE);
                    break;
            }
        } else {
            holder.patient.setText("没有数据");
        }

        return convertView;
    }
}