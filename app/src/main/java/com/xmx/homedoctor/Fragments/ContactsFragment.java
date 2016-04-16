package com.xmx.homedoctor.Fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;
import app.akexorcist.bluetotohspp.library.BluetoothState;
import app.akexorcist.bluetotohspp.library.DeviceList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {
    BluetoothSPP bt;
    boolean connectFlag = false;
    EditText editText;
    TextView stateText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contacts, container, false);
        editText = (EditText) view.findViewById(R.id.text_bluetooth);
        stateText = (TextView) view.findViewById(R.id.bluetooth_state);

        bt = new BluetoothSPP(getContext());
        if (bt.isBluetoothAvailable()) {
            if (bt.isBluetoothEnabled()) {
                stateText.setText(R.string.bluetooth_opened);
            } else {
                stateText.setText(R.string.bluetooth_unopened);
            }

            Button open = (Button) view.findViewById(R.id.open_bluetooth);
            open.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bt.isBluetoothEnabled()) {
                        showToast(R.string.bluetooth_opened);
                    } else {
                        bt.enable();
                        showToast(R.string.opening_bluetooth);
                    }
                    stateText.setText(R.string.bluetooth_opened);
                }
            });

            Button search = (Button) view.findViewById(R.id.search_bluetooth);
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bt.isBluetoothEnabled()) {
                        Intent intent = new Intent(getContext(), DeviceList.class);
                        startActivityForResult(intent, BluetoothState.REQUEST_CONNECT_DEVICE);
                    } else {
                        showToast(R.string.open_bluetooth_hint);
                    }
                }
            });

            Button send = (Button) view.findViewById(R.id.send_bluetooth);
            send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (connectFlag) {
                        String data = editText.getText().toString();
                        if (!data.equals("")) {
                            bt.send(data, true);
                            showToast(R.string.send_bluetooth_success);
                        } else {
                            showToast(R.string.empty_hint);
                        }
                    } else {
                        showToast(R.string.connect_bluetooth_hint);
                    }
                }
            });

            bt.setBluetoothStateListener(new BluetoothSPP.BluetoothStateListener() {
                public void onServiceStateChanged(int state) {
                    if (state == BluetoothState.STATE_CONNECTED) {
                        showToast(R.string.connect_bluetooth_success);
                        stateText.setText(R.string.connect_bluetooth_success);
                        connectFlag = true;
                    } else if (state == BluetoothState.STATE_CONNECTING) {
                        showToast(R.string.connecting_bluetooth);
                        stateText.setText(R.string.connecting_bluetooth);
                    } else if (state == BluetoothState.STATE_LISTEN) {
                        stateText.setText(R.string.waiting_bluetooth);
                    } else if (state == BluetoothState.STATE_NONE) {
                        stateText.setText(R.string.no_bluetooth);
                    }
                }
            });
        } else {
            stateText.setText(R.string.bluetooth_unavailable);
        }

        return view;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if (resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_OTHER);
                bt.connect(data);
            }
        }
    }
}
