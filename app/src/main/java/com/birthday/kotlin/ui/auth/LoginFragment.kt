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

        setupView()
        setupCollector()
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

    override fun setupView() {
        binding.loginBtn.setOnClickListener {
            viewModel.signIn(binding.loginidEt.text.toString(), binding.passwordEt.text.toString())
        }
    }

    override fun setupCollector() {

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
        collectFlow(viewModel.isLoading, function = {
            showProgress(it)
        })

        collectFlow(viewModel.isLogIn, function = {
            if (it.isSuccess) {
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationBirthdayList())
            } else {
                showToast(it.exceptionOrNull().toString())
            }
        })

        collectFlow(viewModel.isAlreadyLoggedIn) {
            if (it) findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToNavigationBirthdayList())
        }
    }
}