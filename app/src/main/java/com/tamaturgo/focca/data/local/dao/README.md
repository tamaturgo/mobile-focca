# data.local.dao

Room `@Dao` interfaces, one per entity (e.g. `TaskDao`). Return `Flow<T>` for queries the UI
observes, and `suspend fun` for one-shot writes. Registered as abstract functions on
`data.local.FoccaDatabase` and exposed to Hilt in `core.di.DatabaseModule`.
