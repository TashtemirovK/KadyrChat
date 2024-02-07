package com.example.kadyrchat.ui.fragments

import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.example.kadyrchat.MainActivity
import com.example.kadyrchat.R
import com.example.kadyrchat.activities.RegisterActivity
import com.example.kadyrchat.utilits.AUTH
import com.example.kadyrchat.utilits.replaceActivity
import com.example.kadyrchat.utilits.replaceFragment
import com.example.kadyrchat.utilits.showToast
import com.google.firebase.FirebaseException
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class EnterPhoneNumberFragment : Fragment(R.layout.fragment_enter_phone_number) {

    private lateinit var mPhoneNumber: String
    private lateinit var mCallBack: PhoneAuthProvider.OnVerificationStateChangedCallbacks
    //private var phoneNumberEditText = view?.findViewById<EditText>(R.id.register_input_phone_number)
    //private var nextButton = view?.findViewById<FloatingActionButton>(R.id.register_btn_next)

    override fun onStart() {
        super.onStart()
        mCallBack = object: PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                AUTH.signInWithCredential(credential).addOnCompleteListener {task ->
                    if (task.isSuccessful) {
                        showToast("Добро пожаловать")
                        (activity as RegisterActivity).replaceActivity(MainActivity())
                    } else showToast(task.exception?.message.toString())
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                showToast(p0.message.toString())
            }

            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                replaceFragment(EnterCodeFragment(mPhoneNumber,id))
            }
        }
        val registerBtnNext = view?.findViewById<Button>(R.id.register_btn_next)
        registerBtnNext?.setOnClickListener { sendCode() }
    }


    private fun sendCode() {
        val registerInputPhoneNumber =
            view?.findViewById<EditText>(R.id.register_input_phone_number)
        if (registerInputPhoneNumber?.text.toString().isEmpty()) {
            showToast(getString(R.string.register_toast_enter_phone))
        } else {
            authUser(registerInputPhoneNumber?.text.toString())
        }
    }

    private fun authUser(phoneNumber: String) {
        mPhoneNumber = phoneNumber
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
            mPhoneNumber,
            60,
            TimeUnit.SECONDS,
            activity as RegisterActivity,
            mCallBack
        )
    }
}
