package com.tamaturgo.focca.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Provides the singleton `FoccaDatabase` instance and its DAOs to the Hilt graph.
 *
 * Empty until the first feature adds an entity (Room requires `@Database` to declare at
 * least one, see `data.local`'s README). Once `FoccaDatabase` exists, add:
 *
 * ```
 * @Provides
 * @Singleton
 * fun provideFoccaDatabase(@ApplicationContext context: Context): FoccaDatabase =
 *     Room.databaseBuilder(context, FoccaDatabase::class.java, "focca.db").build()
 *
 * @Provides
 * fun provideExampleDao(database: FoccaDatabase) = database.exampleDao()
 * ```
 *
 * Note: a module that only has `@Provides` functions can stay an `object`, as this one will.
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule
