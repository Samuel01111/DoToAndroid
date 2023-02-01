package br.com.leumas.doto.di

import dagger.Module
import dagger.Provides

@Module
class StringModule {

    //to use with retrofit and another libraries
    @Provides
    fun provideString(): String {
        return "That's a test provided message"
    }
}
