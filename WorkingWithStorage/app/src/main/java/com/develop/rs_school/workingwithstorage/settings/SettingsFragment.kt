package com.develop.rs_school.workingwithstorage.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.develop.rs_school.workingwithstorage.R

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}