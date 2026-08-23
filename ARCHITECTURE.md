# Architecture

Clean Architecture + MVVM, Jetpack Compose UI, Hilt for DI, Room for local-only persistence
(no network/remote layer).

## Layers and dependency direction

```
presentation  →  domain  ←  data
   (UI/VM)      (business)   (Room)
```

`domain` has no dependency on `data` or `presentation` — it only defines interfaces and
models. `data` and `presentation` both depend on `domain`, never on each other directly.
Wiring between `data`'s implementations and `domain`'s interfaces happens exclusively in
`core.di`.

## Package layout

```
com.tamaturgo.focca
├── FoccaApplication.kt        @HiltAndroidApp entry point
├── MainActivity.kt            @AndroidEntryPoint, hosts the Compose tree
├── core/
│   ├── di/                    Hilt modules (DatabaseModule, RepositoryModule, DispatcherModule)
│   └── util/                  Cross-cutting helpers (e.g. Resource<T>)
├── data/
│   ├── local/
│   │   ├── FoccaDatabase.kt   Room database (single source of truth, local-only)
│   │   ├── dao/               @Dao interfaces
│   │   └── entity/            @Entity classes
│   ├── repository/            domain.repository implementations
│   └── mapper/                entity <-> domain.model conversions
├── domain/
│   ├── model/                 Plain Kotlin business models
│   ├── repository/             Repository interfaces
│   └── usecase/                One class per business operation
└── presentation/
    ├── theme/                  Material 3 theme
    └── <feature>/              Screen composable + ViewModel + UiState, per feature
```

Every otherwise-empty package above has a short `README.md` explaining what belongs there
and the naming convention to follow.

## Adding a new feature (end to end)

1. `domain/model/Foo.kt` — the business model.
2. `domain/repository/FooRepository.kt` — interface, in terms of the domain model.
3. `domain/usecase/GetFooUseCase.kt` (etc.) — one use case per operation, `@Inject constructor`.
4. `data/local/entity/FooEntity.kt` + `data/local/dao/FooDao.kt`.
5. Register the entity in `FoccaDatabase.entities` and add `abstract fun fooDao(): FooDao`;
   bump the DB `version` and add a `Migration` if this ships after a release.
6. `data/mapper/FooMapper.kt` — `FooEntity.toDomain()` / `Foo.toEntity()`.
7. `data/repository/FooRepositoryImpl.kt` — implements the interface using the DAO + mapper.
8. Bind it in `core/di/RepositoryModule.kt` with `@Binds`.
9. Expose the DAO from `core/di/DatabaseModule.kt`.
10. `presentation/foo/` — `FooUiState.kt`, `FooViewModel.kt` (`@HiltViewModel`), `FooScreen.kt`.

## Dependency injection (Hilt)

- `FoccaApplication` is annotated `@HiltAndroidApp`; `MainActivity` is `@AndroidEntryPoint`.
- ViewModels use `@HiltViewModel` + constructor injection, and are obtained in composables
  via `hiltViewModel()`.
- All current modules install into `SingletonComponent` (app-lifetime singletons) — introduce
  a narrower scope (e.g. `ViewModelComponent`) only if a feature actually needs it.
- Coroutine dispatchers are injected via the qualifiers in `core.di.DispatcherModule`
  (`@IoDispatcher`, `@DefaultDispatcher`, `@MainDispatcher`) instead of referencing
  `Dispatchers.IO` etc. directly, so tests can swap in a `TestDispatcher`.

## Persistence (Room)

Local-only — there is no remote API or sync in this app, so `data.repository`
implementations talk to Room DAOs directly. Room schema history is exported to
`app/schemas/` on each build (`room.schemaLocation` in `app/build.gradle.kts`) and should be
committed, since it's required to validate migrations between versions.

## Build tooling

- Both Room and Hilt use **KSP** for annotation processing (no kapt) — kapt was dropped
  because it breaks under JDK 21+ (`IllegalAccessError` accessing `com.sun.tools.javac`
  internals), which is what this project's Gradle daemon runs on.
