package com.example.kadyrchat.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.kadyrchat.R
import com.example.kadyrchat.databinding.ActivityRegisterBinding
import com.example.kadyrchat.ui.fragments.EnterPhoneNumberFragment
import com.example.kadyrchat.utilits.initFirebase
import com.example.kadyrchat.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityRegisterBinding
    private lateinit var mToolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        initFirebase()
    }

    override fun onStart() {
        super.onStart()
        mToolbar = mBinding.registerToolBar
        setSupportActionBar(mToolbar)
        title = getString(R.string.register_title_your_phone)
        replaceFragment(EnterPhoneNumberFragment(), false)
    }
}