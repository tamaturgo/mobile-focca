# domain.model

Plain Kotlin data classes representing the app's business objects — no Android, Room, or
Compose imports here. These are what use cases and the presentation layer work with; they
are mapped to/from `data.local.entity` classes in `data.mapper`.
