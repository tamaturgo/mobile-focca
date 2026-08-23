# data.repository

Implementations of `domain.repository` interfaces (e.g. `TaskRepositoryImpl : TaskRepository`).
Each takes the relevant DAO via constructor injection, converts between `data.local.entity`
and `domain.model` using `data.mapper`, and is bound to its interface in
`core.di.RepositoryModule` with `@Binds`.
