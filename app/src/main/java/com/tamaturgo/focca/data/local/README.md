# data.local

Room requires its `@Database` to declare at least one `@Entity`, so `FoccaDatabase.kt` isn't
created until the first feature needs persistence. When that happens, add it here:

```kotlin
package com.tamaturgo.focca.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [/* e.g. TaskEntity::class */],
    version = 1,
    exportSchema = true
)
abstract class FoccaDatabase : RoomDatabase() {
    // abstract fun taskDao(): TaskDao
}
```

Then wire it into `core.di.DatabaseModule` (see the `@Provides` example documented there) and
bump `version` + add a `Migration` on every schema change after a release.
