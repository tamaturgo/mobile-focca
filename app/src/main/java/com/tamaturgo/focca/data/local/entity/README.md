# data.local.entity

Room `@Entity` classes — the on-disk schema. These mirror `domain.model` classes but are
allowed to carry storage-specific concerns (column names, indices, foreign keys) that the
domain layer shouldn't know about. Converted via `data.mapper`, never passed to
`domain`/`presentation` directly.
