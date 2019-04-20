package net.minpro.meditation.di

import android.app.Application
import net.minpro.meditation.model.UserSettingsRepository
import net.minpro.meditation.service.MusicServiceHelper
import net.minpro.meditation.util.NotificationHelper
import net.minpro.meditation.view.dialog.LevelSelectDialog
import net.minpro.meditation.view.dialog.ThemeSelectDialog
import net.minpro.meditation.view.dialog.TimeSelectDialog
import net.minpro.meditation.view.main.MainFragment
import net.minpro.meditation.viewmodel.MainViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val appModule = module {
    factory { MainFragment() }
    factory { LevelSelectDialog() }
    factory { ThemeSelectDialog() }
    factory { TimeSelectDialog() }
    factory { NotificationHelper(get()) }
    factory { MusicServiceHelper(get()) }
    factory { UserSettingsRepository() }

    viewModel { MainViewModel(androidContext() as Application) }
}