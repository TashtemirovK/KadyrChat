package com.example.kadyrchat

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.kadyrchat.activities.RegisterActivity
import com.example.kadyrchat.databinding.ActivityMainBinding
import com.example.kadyrchat.ui.fragments.ChatsFragment
import com.example.kadyrchat.ui.objects.AppDrawer
import com.example.kadyrchat.utilits.AUTH
import com.example.kadyrchat.utilits.initFirebase
import com.example.kadyrchat.utilits.replaceActivity
import com.example.kadyrchat.utilits.replaceFragment
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var mBinding: ActivityMainBinding
    private lateinit var mAppDrawer: AppDrawer
    private lateinit var mToolBar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)

    }

    override fun onStart() {
        super.onStart()
        initFields()
        initFunc()
    }

    private fun initFunc() {
        if (AUTH.currentUser != null) {
            setSupportActionBar(mToolBar)
            mAppDrawer.create()
            replaceFragment(ChatsFragment(), false)
        } else {
            replaceActivity(RegisterActivity())
        }
    }

    private fun initFields() {
        mToolBar = mBinding.mainToolBar
        mAppDrawer = AppDrawer(this, mToolBar)
        initFirebase()
    }

}