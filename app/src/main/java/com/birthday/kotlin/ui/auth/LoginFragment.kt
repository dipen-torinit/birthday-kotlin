package com.birthday.kotlin.ui.auth

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentLoginBinding
import com.birthday.kotlin.ui.BaseFragment
import com.birthday.kotlin.ui.LauncherViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFullScreen(false)
        binding.loginBtn.setOnClickListener {
            viewModel.signIn("", "")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setFullScreen(true)
    }
}