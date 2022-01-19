package com.birthday.kotlin.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.birthday.kotlin.R
import com.birthday.kotlin.data.LoadingEvent
import com.birthday.kotlin.databinding.ActivityLauncherBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import hide
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import show

@AndroidEntryPoint
class LauncherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash screen configuration
        installSplashScreen().apply {
            setKeepVisibleCondition {
                viewModel.isSplashScreenLoading.value
            }
        }

        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViews()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    /* Handling the ProgressBar here
    *  Whenever BaseViewModel fire event from "startLoading" & "stopLoading" this method will be called and we can show/hide progress based on the EventType
    * */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadingEvent(loadingEvent: LoadingEvent) {
        if (loadingEvent.isLoading) showProgress() else hideProgress()
    }

    private fun setupViews() {
        val navView: BottomNavigationView = binding.navView

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
        val navController = navHostFragment.navController
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.navigation_birthday_list, R.id.navigation_add_birthday, R.id.navigation_settings
            ),
            fallbackOnNavigateUpListener = ::onSupportNavigateUp
        )
        binding.toolbar.setupWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    fun setFullScreen(showFullScreen: Boolean) {
        if (showFullScreen) {
            binding.toolbar.show()
            binding.navView.show()
        } else {
            binding.toolbar.hide()
            binding.navView.hide()
        }
    }

    fun showProgress(){
        binding.progressBar.show()
    }

    fun hideProgress(){
        binding.progressBar.hide()
    }
}