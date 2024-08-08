package com.example.morty

import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.View
import androidx.fragment.app.Fragment
import com.google.firebase.crashlytics.FirebaseCrashlytics

abstract class BaseFragment(@LayoutRes layoutRes: Int): Fragment(layoutRes) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        FirebaseCrashlytics.getInstance().log("Fragment ${this::class.java.simpleName}")
    }
}