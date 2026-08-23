# domain.repository

Interfaces only, expressed in terms of `domain.model` types (e.g. `Flow<List<Task>>`,
`suspend fun save(task: Task)`). The domain layer depends on nothing outside itself — it
declares what it needs, and `data.repository` provides the implementation, wired together
in `core.di.RepositoryModule`.
