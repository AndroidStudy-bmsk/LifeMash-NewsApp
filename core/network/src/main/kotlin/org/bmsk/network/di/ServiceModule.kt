package org.bmsk.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.bmsk.network.service.GoogleNewsService
import org.bmsk.network.service.SbsNewsService
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {
    @Provides
    @Singleton
    fun providesGoogleNewsService(
        @GoogleRetrofit retrofit: Retrofit
    ): GoogleNewsService {
        return retrofit.create(GoogleNewsService::class.java)
    }

    @Provides
    @Singleton
    fun providesSbsNewsService(
        @SbsRetrofit retrofit: Retrofit
    ): SbsNewsService {
        return retrofit.create(SbsNewsService::class.java)
    }
}