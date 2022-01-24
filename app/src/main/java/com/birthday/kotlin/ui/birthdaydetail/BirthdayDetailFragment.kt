package com.birthday.kotlin.ui.birthdaydetail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentBirthdayDetailBinding
import com.birthday.kotlin.ui.BaseFragment


class BirthdayDetailFragment : BaseFragment(R.layout.fragment_birthday_detail) {

    private val binding: FragmentBirthdayDetailBinding by viewBinding()
    private val viewModel: BirthdayDetailViewModel by viewModels()

    val args: BirthdayDetailFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupCollectors()
    }

    override fun onResume() {
        super.onResume()
        executeCallsWhenResume()
    }

    override fun setupViews() {
        binding.person = args.person
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun setupCollectors() {
    }

    override fun executeCallsWhenResume() {
    }
}