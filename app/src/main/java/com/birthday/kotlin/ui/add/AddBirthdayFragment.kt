package com.birthday.kotlin.ui.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentAddBirthdayBinding
import com.birthday.kotlin.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddBirthdayFragment : BaseFragment(R.layout.fragment_add_birthday) {

    private val binding: FragmentAddBirthdayBinding by viewBinding()
    private val viewModel: AddBirthdayViewModel by viewModels()

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
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        binding.addBtn.setOnClickListener {
            viewModel.addBirthday()
        }
    }

    override fun executeCallsWhenResume() {

    }

    override fun setupCollectors() {
        collectFlow(viewModel.addResponse) {
            if (it.isSuccess) {
                viewModel.cleanForm()
                showMessage(binding.root, R.string.birthday_added_successfully)
            } else {
                showMessage(binding.root, it.exceptionOrNull().toString())
            }
        }
    }
}