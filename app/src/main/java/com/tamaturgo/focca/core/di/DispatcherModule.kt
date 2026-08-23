package com.tamaturgo.focca.core.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Qualifier

/**
 * Provides qualified [CoroutineDispatcher]s so use cases and repositories never reference
 * [Dispatchers] directly. Injecting the dispatcher (instead of hard-coding it) lets tests
 * substitute a [kotlinx.coroutines.test.TestDispatcher] without touching production code.
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main
}

/** Dispatcher for disk/database/network I/O. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class IoDispatcher

/** Dispatcher for CPU-intensive work (sorting, parsing, mapping large lists). */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DefaultDispatcher

/** Dispatcher for UI-confined work. */
@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainDispatcher
