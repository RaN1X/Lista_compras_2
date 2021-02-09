package com.joorpe.myapplication;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.preference.ListPreference;
import androidx.preference.PreferenceFragmentCompat;

import android.util.Log;


public class fragmento_config extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.configuration, rootKey);

        ListPreference listPreference = findPreference("list_preference_1");



    }

}


