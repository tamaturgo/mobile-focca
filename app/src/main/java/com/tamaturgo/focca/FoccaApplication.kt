package com.tamaturgo.focca

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class annotated with [HiltAndroidApp], which triggers Hilt's code generation
 * and creates the app-level dependency container. Every Hilt component in the app is a
 * descendant of the one attached here.
 */
@HiltAndroidApp
class FoccaApplication : Application()
