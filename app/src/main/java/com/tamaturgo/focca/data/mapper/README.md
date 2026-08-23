# data.mapper

Extension functions converting between `data.local.entity` and `domain.model`
(e.g. `fun TaskEntity.toDomain(): Task`, `fun Task.toEntity(): TaskEntity`). Keeps the
translation logic out of the repository implementations.
