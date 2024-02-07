package com.example.kadyrchat.ui.fragments

import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kadyrchat.MainActivity
import com.example.kadyrchat.R
import com.example.kadyrchat.activities.RegisterActivity
import com.example.kadyrchat.utilits.AUTH
import com.example.kadyrchat.utilits.AppTextWatcher
import com.example.kadyrchat.utilits.CHILD_ID
import com.example.kadyrchat.utilits.CHILD_PHONE
import com.example.kadyrchat.utilits.CHILD_USERNAME
import com.example.kadyrchat.utilits.NODE_USERS
import com.example.kadyrchat.utilits.REF_DATABASE_ROOT
import com.example.kadyrchat.utilits.replaceActivity
import com.example.kadyrchat.utilits.showToast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class EnterCodeFragment(val phoneNumber: String, val id: String) :
    Fragment(R.layout.fragment_enter_code) {

    private lateinit var registerInputCode: EditText

    override fun onStart() {
        super.onStart()
        (activity as RegisterActivity).title = phoneNumber
        registerInputCode.addTextChangedListener(AppTextWatcher {
            val string = registerInputCode.text.toString()
            if (string.length == 6) {
                enterCode()
            }
        })
    }

    private fun enterCode() {
        val code = registerInputCode.text.toString()
        val credential = PhoneAuthProvider.getCredential(id, code)
        AUTH.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val uid = AUTH.currentUser?.uid.toString()
                val dateMap = mutableMapOf<String, Any>()
                dateMap[CHILD_ID] = uid
                dateMap[CHILD_PHONE] = phoneNumber
                dateMap[CHILD_USERNAME] = uid

                REF_DATABASE_ROOT.child(NODE_USERS).child(uid).updateChildren(dateMap)
                    .addOnCompleteListener { task2 ->
                        if (task2.isSuccessful) {
                            showToast("Добро пожаловать")
                            (activity as RegisterActivity).replaceActivity(MainActivity())
                        } else showToast(task2.exception?.message.toString())
                    }
            } else showToast(task.exception?.message.toString())
        }
    }
}
