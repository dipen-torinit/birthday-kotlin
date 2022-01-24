package com.birthday.kotlin.ui.birthdaylist

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.birthday.kotlin.R
import com.birthday.kotlin.databinding.FragmentBirthdayListBinding
import com.birthday.kotlin.ui.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BirthdayListFragment : BaseFragment(R.layout.fragment_birthday_list) {

    private val binding: FragmentBirthdayListBinding by viewBinding()
    private val viewModel: BirthdayListViewModel by viewModels()

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

    }

    override fun executeCallsWhenResume() {
        viewModel.fetchBirthdays()
    }

    override fun setupCollectors() {

        collectFlow(viewModel.personsList) {
            if(it.isSuccess){
                binding.birthdayListRv.adapter = BirthdayListAdapter(
                    it.getOrDefault(defaultValue = listOf())
                ) {
                    findNavController().navigate(
                        BirthdayListFragmentDirections.actionNavigationBirthdayListToBirthdayDetailFragment(
                            it
                        )
                    )
                }
            }
        }
    }

}