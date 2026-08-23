# domain.usecase

One class per business operation (e.g. `GetTasksUseCase`, `SaveTaskUseCase`), each exposing
a single `operator fun invoke(...)`. A use case depends only on `domain.repository`
interfaces — never on `data` or `presentation` — and is what a ViewModel calls into.
Injected via constructor (`@Inject constructor`); no Hilt module needed for these.
