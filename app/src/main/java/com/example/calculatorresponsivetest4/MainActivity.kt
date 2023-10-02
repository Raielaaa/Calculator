package com.example.calculatorresponsivetest4

import android.app.usage.UsageEvents.Event.NONE
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import com.example.calculatorresponsivetest4.databinding.ActivityMainBinding
import com.example.calculatorresponsivetest4.ui.history.DeleteHistoryDialogFragment
import com.example.calculatorresponsivetest4.utils.CustomTypefaceSpan

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var customTypeface: Typeface
    var isInHistoryFragment: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        supportActionBar?.apply {
//            setDisplayHomeAsUpEnabled(true)
//            setHomeAsUpIndicator(R.drawable.ic_hamburger)
//            title = "Standard Calculator"
//        }
        setSupportActionBar(binding.appBarMain.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_scientific, R.id.nav_currency, R.id.nav_history, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        customTypeface = ResourcesCompat.getFont(this, R.font.acme)!!
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        val spannableString = SpannableString("Standard Calculator")
        spannableString.setSpan(
            CustomTypefaceSpan(customTypeface),
            0,
            spannableString.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        supportActionBar?.title = spannableString

        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val deleteHistoryItem = menu!!.findItem(R.id.action_delete)
        // Show/hide the "Delete History" bin icon based on the current fragment
        deleteHistoryItem.isVisible = isInHistoryFragment

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> {
                DeleteHistoryDialogFragment().show(supportFragmentManager, "Confirm delete dialog")

                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }
}