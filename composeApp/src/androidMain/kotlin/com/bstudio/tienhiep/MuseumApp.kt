package com.bstudio.tienhiep

import android.app.Application
import com.bstudio.tienhiep.di.initKoin

class MuseumApp : Application() {
    override fun onCreate() {
        super.onCreate()
        initKoin()
    }
}
