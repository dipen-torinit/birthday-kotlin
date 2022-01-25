package com.birthday.kotlin.ui.birthdaylist

import android.os.Bundle
import android.util.Log
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

        binding.swiperefreshLayout.setOnRefreshListener {
            viewModel.initialCalls()
        }

    }

    override fun executeCallsWhenResume() {
        /*
        * We can move this call to init method of ViewModel
        * and avoid unnecessary execution of this method, when device rotated
        * */
//        viewModel.fetchBirthdays()
    }

    override fun setupCollectors() {

        collectFlow(viewModel.personsList) { it ->

            binding.birthdayListRv.adapter =
                BirthdayListAdapter(it.getOrDefault(defaultValue = listOf())) { person ->
                    findNavController().navigate(
                        BirthdayListFragmentDirections.actionNavigationBirthdayListToBirthdayDetailFragment(
                            person
                        )
                    )
                }

            binding.swiperefreshLayout.isRefreshing = false
        }

        connectionLiveData.observe(this) {
            Log.d("NETWORK", "Fragment: Network status $it")
        }
    }

}