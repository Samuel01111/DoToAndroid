package br.com.leumas.doto

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import br.com.leumas.doto.ui.di.MainComponent

class MainActivity : AppCompatActivity() {

    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        mainComponent = (applicationContext as TodoApplication)
            .appComponent
            .mainComponent()
            .create()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}