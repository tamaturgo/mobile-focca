# Focca — Agent guidelines

## Language policy

- **Conversation** with the user happens in whatever language they use (typically
  Portuguese).
- **Every artifact this repo produces must be written in English**, with no
  exceptions: source code, identifiers, comments, KDoc, commit messages, PRD/docs,
  and all openspec artifacts (proposals, design docs, specs, tasks).
- Never translate this rule away for a specific request just because the request
  itself was made in Portuguese.

## Project context

Focca is an Android app about workout consistency, built with Kotlin + Jetpack
Compose, Hilt, Room, and Coroutines, following Clean Architecture + MVVM
(`data` / `domain` / `presentation` / `core`). Full product spec, screen flows, data
model, and design system: `docs/PRD.md`. OpenSpec project context and per-artifact
rules (tech stack details, package conventions, language policy for artifacts):
`openspec/config.yaml`.
