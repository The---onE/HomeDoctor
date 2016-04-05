package com.xmx.homedoctor.Patients;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.xmx.homedoctor.R;

import java.util.ArrayList;

/**
 * Created by The_onE on 2016/4/1.
 */
public class PatientsAdapter extends BaseAdapter {
    ArrayList<Patient> mItems = new ArrayList<>();
    Context mContext;

    public PatientsAdapter(Context context, ArrayList<Patient> items) {
        mContext = context;
        mItems = items;
    }

    public void setItems(ArrayList<Patient> items) {
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
        TextView username;
        TextView nickname;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_patients, null);
            holder = new ViewHolder();
            holder.username = (TextView) convertView.findViewById(R.id.item_username);
            holder.nickname = (TextView) convertView.findViewById(R.id.item_nickname);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position < mItems.size()) {
            holder.username.setText(mItems.get(position).getUsername());
            holder.username.setTextColor(Color.BLACK);
            holder.nickname.setText(mItems.get(position).getNickname());
            holder.nickname.setTextColor(Color.BLACK);
        } else {
            holder.nickname.setText("加载失败");
            holder.username.setText("");
        }
        return convertView;
    }
}
