package com.example.calculatorresponsivetest4

import android.app.usage.UsageEvents.Event.NONE
import android.content.SharedPreferences
import android.content.res.Configuration
import androidx.appcompat.widget.Toolbar
import android.graphics.Typeface
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
//import android.widget.Toolbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.res.ResourcesCompat
import com.example.calculatorresponsivetest4.databinding.ActivityMainBinding
import com.example.calculatorresponsivetest4.ui.history.DeleteHistoryDialogFragment
import com.example.calculatorresponsivetest4.utils.CustomTypefaceSpan
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity(), ColorPickerDialogListener {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var customTypeface: Typeface
    private lateinit var sharedPreferences: SharedPreferences
    var isInHistoryFragment: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("SP_Calculator", MODE_PRIVATE)
        initTheme()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        setSupportActionBar(binding.appBarMain.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_scientific, R.id.nav_currency, R.id.nav_history, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

//        // Get the action_settings item from the toolbar
//        val settingsItem = findViewById<View>(R.id.action_settings)
//
//        // Set a click listener for the action_settings item
//        settingsItem.setOnClickListener {
//            showPopupMenu(it)
//        }
    }

//    private fun showPopupMenu(view: View) {
//        val popupMenu = PopupMenu(this, view)
//        popupMenu.inflate(R.menu.popup_menu)
//
//        // Set a listener for menu item clicks
//        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
//            when (item.itemId) {
//                R.id.menu_settings -> {
//                    // Handle "Settings" option click
//                    // Add your code here
//                    true
//                }
//                R.id.menu_history -> {
//                    // Handle "History" option click
//                    // Add your code here
//                    true
//                }
//                else -> false
//            }
//        }
//
//        // Show the PopupMenu
//        popupMenu.show()
//    }

    private fun initTheme() {
        if (sharedPreferences.getBoolean("DarkMode_key", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
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

    // Implement the ColorPickerDialogListener interface
    override fun onColorSelected(dialogId: Int, color: Int) {
        // Handle the selected color here
        val hexColor = String.format("#%06X", 0xFFFFFF and color)
        Log.d("MyTag", "onColorSelected: $hexColor")
        // Use hexColor as needed
    }

    override fun onDialogDismissed(dialogId: Int) {
        // Handle dialog dismissal if needed
    }
}