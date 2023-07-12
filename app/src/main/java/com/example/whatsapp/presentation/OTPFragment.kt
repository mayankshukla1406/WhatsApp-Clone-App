package com.example.whatsapp.presentation

import android.app.Dialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.whatsapp.R
import com.example.whatsapp.databinding.FragmentOTPBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OTPFragment : BottomSheetDialogFragment(),IViewsHandling {

    private var binding : FragmentOTPBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentOTPBinding.inflate(inflater,container,false)
        setUpOTPInput()
        requireDialog().setTitle("Otp Verification")

        return binding!!.root
    }

    private fun setUpOTPInput() {
        binding?.let {it->
            it.otpBox1.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {first->
                        if(first.length == 1) {
                            it.otpBox2.requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            it.otpBox2.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {second->
                        if(second.length == 1) {
                            it.otpBox3.requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            it.otpBox3.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {third->
                        if(third.length == 1) {
                            it.otpBox4.requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            it.otpBox4.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {fourth->
                        if(fourth.length == 1) {
                            it.otpBox5.requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            it.otpBox5.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {fifth->
                        if(fifth.length == 1) {
                            it.otpBox6.requestFocus()
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
            it.otpBox6.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

                }

                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                    p0?.let {sixth->
                        if(sixth.length == 1) {
                            val otpValue = it.otpBox1.text.toString() + it.otpBox2.text.toString()+
                                    it.otpBox3.text.toString() + it.otpBox4.text.toString() +
                                    it.otpBox5.text.toString() + it.otpBox6.text.toString()
                            (activity as MainActivity).otpValue.value = otpValue
                        }
                    }
                }

                override fun afterTextChanged(p0: Editable?) {

                }

            })
        }
    }

    override fun dismissOtpBottomSheetDialogFragment() {
        dismiss()
    }
}