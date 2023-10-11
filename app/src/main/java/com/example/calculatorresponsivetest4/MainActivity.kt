package com.example.calculatorresponsivetest4

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
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
import androidx.navigation.NavController
import com.example.calculatorresponsivetest4.databinding.ActivityMainBinding
import com.example.calculatorresponsivetest4.ui.history.DeleteHistoryDialogFragment
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    var isInHistoryFragment: Boolean = false
    private lateinit var editor: SharedPreferences.Editor
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("SP_Calculator", MODE_PRIVATE)
        initTheme()
        initFont()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        editor = sharedPreferences.edit()

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        navController = findNavController(R.id.nav_host_fragment_content_main)
        setSupportActionBar(binding.appBarMain.toolbar)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_scientific, R.id.nav_currency, R.id.nav_history, R.id.nav_settings
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        if (resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            supportActionBar!!.hide();
        }
    }

    private fun initFont() = setTheme(sharedPreferences.getInt("Font_key", R.style.MyTheme_Acme))

    private fun initTheme() {
        if (sharedPreferences.getBoolean("DarkMode_key", false)) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)

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
        return when (item.itemId) {
            R.id.action_delete -> {
                DeleteHistoryDialogFragment().show(supportFragmentManager, "Confirm delete dialog")
                true
            }
            R.id.action_settings -> {
                val anchorView = findViewById<View>(R.id.action_settings) // You can replace this with any suitable anchor view
                showSettingsPopupMenu(anchorView)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showSettingsPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        popupMenu.menuInflater.inflate(R.menu.popup_menu, popupMenu.menu)

        // Set item click listener for the popup menu
        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.menu_settings -> {
                    navController.navigate(R.id.nav_settings)
                    true
                }
                R.id.menu_history -> {
                    navController.navigate(R.id.nav_history)
                    true
                }
                else -> false
            }
        }

        // Show the popup menu
        popupMenu.show()
        navController.popBackStack()
    }
}