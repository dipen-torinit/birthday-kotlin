package com.birthday.kotlin.ui.auth

import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentLoginBinding
import com.birthday.kotlin.ui.BaseFragment


class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullScreen(false)
        binding.loginidEt.setOnClickListener {

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setFullScreen(true)
    }
}