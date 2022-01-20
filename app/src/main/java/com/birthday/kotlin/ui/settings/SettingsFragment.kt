package com.birthday.kotlin.ui.settings

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentSettingsBinding
import com.birthday.kotlin.ui.BaseFragment
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : BaseFragment(R.layout.fragment_settings) {
    private val binding: FragmentSettingsBinding by viewBinding()
    private val viewModel: SettingsViewModel by viewModels()

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupCollectors()
    }

    override fun setupViews() {
        firebaseAuth.currentUser?.email?.let {
            binding.userEmailTv.text = it
        }

        binding.logoutBtn.setOnClickListener {
            viewModel.logOut()
        }
    }

    override fun executeCallsWhenResume() {

    }

    override fun setupCollectors() {
        collectFlow(viewModel.logOut) {
            findNavController().navigate(SettingsFragmentDirections.actionNavigationSettingsToLoginFragment())
        }
    }
}