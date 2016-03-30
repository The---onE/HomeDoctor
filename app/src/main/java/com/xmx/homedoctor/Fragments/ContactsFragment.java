package com.xmx.homedoctor.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xmx.homedoctor.R;
import com.xmx.homedoctor.Tools.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactsFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_contacts, container, false);
    }

}
