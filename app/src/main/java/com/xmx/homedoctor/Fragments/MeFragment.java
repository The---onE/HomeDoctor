package com.xmx.homedoctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;
import com.xmx.homedoctor.Tools.Data.DataManager;
import com.xmx.homedoctor.User.LoginActivity;

/**
 * A simple {@link Fragment} subclass.
 */
public class MeFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);

        Button logout = (Button) view.findViewById(R.id.btn_logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DataManager.getInstance().logout();
                startActivity(LoginActivity.class);
                getActivity().finish();
            }
        });

        return view;
    }

}
