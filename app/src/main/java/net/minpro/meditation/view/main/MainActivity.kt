package net.minpro.meditation.view.main

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import net.minpro.meditation.R
import net.minpro.meditation.util.FragmentTag
import net.minpro.meditation.util.PlayStatus
import net.minpro.meditation.view.dialog.LevelSelectDialog
import net.minpro.meditation.view.dialog.ThemeSelectDialog
import net.minpro.meditation.view.dialog.TimeSelectDialog
import net.minpro.meditation.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.screen_container, MainFragment())
                .commit()
        }

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        observeViewModel()

        btmNavi.setOnNavigationItemSelectedListener { item ->
            when(item.itemId) {
                R.id.item_select_level -> {
                     LevelSelectDialog().show(supportFragmentManager, FragmentTag.LEVEL_SELECT.name)
                    true
                }
                R.id.item_select_theme -> {
                     ThemeSelectDialog().show(supportFragmentManager, FragmentTag.THEME_SELECT.name)
                    true
                }
                R.id.item_select_time -> {
                     TimeSelectDialog().show(supportFragmentManager, FragmentTag.TIME_SELECT.name)
                    true
                }
                else -> { false }
            }
        }
    }

    private fun observeViewModel() {
        viewModel.playStatus.observe(this, Observer { status ->
            when(status){
                PlayStatus.BEFORE_START -> {
                    btmNavi.visibility = View.VISIBLE
                }
                PlayStatus.ON_START -> {
                    btmNavi.visibility = View.INVISIBLE
                }
                PlayStatus.RUNNING -> {
                    btmNavi.visibility = View.INVISIBLE
                }
                PlayStatus.PAUSE -> {
                    btmNavi.visibility = View.INVISIBLE
                }
                PlayStatus.END -> {

                }
            }
        })
    }
}
