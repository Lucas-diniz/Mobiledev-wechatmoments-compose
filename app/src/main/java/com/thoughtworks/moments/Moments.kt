package com.thoughtworks.moments

import android.app.Application
import com.thoughtworks.moments.di.dataDi
import com.thoughtworks.moments.di.domainDi
import com.thoughtworks.moments.di.uiDi
import org.koin.core.context.startKoin

class Moments : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(
                uiDi, domainDi, dataDi
            )
        }
    }
}