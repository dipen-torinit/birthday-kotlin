package com.birthday.kotlin.ui.auth

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentLoginBinding
import com.birthday.kotlin.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : BaseFragment(R.layout.fragment_login) {

    private val binding: FragmentLoginBinding by viewBinding()
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setFullScreen(false)

        setupViews()
        setupCollectors()
    }

    override fun executeCallsWhenResume() {
        viewModel.checkIfAlreadyLoggedIn()
    }

    override fun onResume() {
        super.onResume()
        executeCallsWhenResume()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        setFullScreen(true)
    }

    override fun setupViews() {
        binding.loginBtn.setOnClickListener {
            val loginId = binding.loginidEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (loginId.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signIn(loginId, password)
            } else {
                showMessage(binding.root, R.string.enter_loginid_password)
            }
        }

        binding.signupBtn.setOnClickListener {
            val loginId = binding.loginidEt.text.toString()
            val password = binding.passwordEt.text.toString()
            if (loginId.isNotEmpty() && password.isNotEmpty()) {
                viewModel.signUp(loginId, password)
            } else {
                showMessage(binding.root, R.string.enter_loginid_password)
            }
        }
    }

    override fun setupCollectors() {

        /*
        * For collecting different flow we have to create separate coroutine-scope(launchWhenStarted)
        * we can not collect multiple flow in single coroutine-scope(launchWhenStarted)
        * */
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.isLoading.collectLatest {
//                showProgress(it)
//            }
//        }
//
//        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
//            viewModel.isLogIn.collect {
//                if (it.isSuccess) {
//                    Toast.makeText(
//                        requireContext(),
//                        "Login success", Toast.LENGTH_LONG
//                    ).show()
//
//                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationHome())
//                } else {
//                    Toast.makeText(
//                        requireContext(),
//                        it.exceptionOrNull().toString(), Toast.LENGTH_LONG
//                    ).show()
//                }
//            }
//        }

        /*
        * Instead of using launchWhenStarted we can use "launch" scope which is more or less similar to "launchWhenStarted"
        * */
//        viewLifecycleOwner.lifecycleScope.launch {
//            lifecycle.whenStarted {
//                launch {
//                    viewModel.isLoading.collectLatest {
//                        showProgress(it)
//                    }
//                }
//
//                launch {
//                    viewModel.isLogIn.collect {
//                        if (it.isSuccess) {
//                            Toast.makeText(
//                                requireContext(),
//                                "Login success", Toast.LENGTH_LONG
//                            ).show()
//                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationHome())
//                        } else {
//                            Toast.makeText(
//                                requireContext(),
//                                it.exceptionOrNull().toString(), Toast.LENGTH_LONG
//                            ).show()
//                        }
//                    }
//                }
//            }
//        }

        /*
        * We can create a helper function which can remove redundant code
        * */

        collectFlow(viewModel.isLogIn, function = {
            if (it.isSuccess) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationBirthdayList())
            } else {
                showMessage(binding.root, it.exceptionOrNull().toString())
            }
        })

        collectFlow(viewModel.isSignUp, function = {
            if (it.isSuccess) {
                showMessage(binding.root, R.string.sign_up_success)
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationBirthdayList())
            } else {
                showMessage(binding.root, it.exceptionOrNull().toString())
            }
        })

        collectFlow(viewModel.isAlreadyLoggedIn) {
            if (it) findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationBirthdayList())
        }
    }
}