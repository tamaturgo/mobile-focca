package com.tamaturgo.focca.core.di

import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Binds `domain.repository` interfaces to their `data.repository` implementations.
 *
 * Kept empty until the first feature is added. Usage pattern, once a repository exists:
 *
 * ```
 * @Module
 * @InstallIn(SingletonComponent::class)
 * abstract class RepositoryModule {
 *
 *     @Binds
 *     abstract fun bindExampleRepository(
 *         impl: ExampleRepositoryImpl
 *     ): ExampleRepository
 * }
 * ```
 *
 * Note: a module with `@Binds` must be an `abstract class`/`interface`, not an `object`.
 */
@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule
