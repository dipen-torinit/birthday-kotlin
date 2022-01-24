package com.birthday.kotlin.ui

import android.os.Bundle
import android.view.WindowManager
import androidx.activity.viewModels
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
import java.util.concurrent.atomic.AtomicInteger

@AndroidEntryPoint
class LauncherActivity : BaseActivity() {

    private lateinit var binding: ActivityLauncherBinding
    private val viewModel: LauncherViewModel by viewModels()

    companion object {
        private var progressCounter = AtomicInteger(0)
    }

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
                R.id.navigation_birthday_list,
                R.id.navigation_add_birthday,
                R.id.navigation_settings
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

    private fun showProgress() {
        progressCounter.getAndIncrement()
        binding.progressBar.show()
        freezeScreen()
    }

    private fun hideProgress() {
        progressCounter.decrementAndGet().let {
            if (it <= 0) {
                binding.progressBar.hide()
                unfreezeScreen()
                progressCounter.set(0)
            }
        }
    }

    private fun freezeScreen() {
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
            WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
        )
    }

    private fun unfreezeScreen() {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
    }
}