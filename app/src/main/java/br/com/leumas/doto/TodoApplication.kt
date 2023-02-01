package br.com.leumas.doto

import android.app.Application
import br.com.leumas.doto.di.ApplicationComponent
import br.com.leumas.doto.di.DaggerApplicationComponent

class TodoApplication: Application() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()
        //instanciates the graph of fragments and activities
        appComponent = DaggerApplicationComponent
            .factory()
            .create(this)
    }
}
