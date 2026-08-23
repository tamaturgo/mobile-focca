# Focca

Focca é um app Android de **consistência de treino** — o foco não é montar o treino
perfeito, é responder uma pergunta simples toda semana: *"você manteve o ritmo?"*.
Rotina, checklist de exercícios, timer, streak e histórico existem para sustentar esse
objetivo, não o contrário.

> Documento de produto completo em [`docs/PRD.md`](docs/PRD.md) — visão, escopo do MVP,
> especificação tela a tela, modelo de dados e sistema de design. Leia esse arquivo antes
> de propor ou implementar qualquer feature nova.

## Stack

- **Kotlin** + **Jetpack Compose** (UI declarativa, Material 3)
- **Hilt** (injeção de dependência)
- **Room** (persistência local — não há backend/rede neste app)
- Clean Architecture + MVVM (`presentation` → `domain` ← `data`)

## Requisitos

- Android Studio (versão atual com suporte a Kotlin/Compose/KSP)
- JDK 21+
- `compileSdk` 34 · `minSdk` 26 · `targetSdk` 34

## Rodando o projeto

```bash
./gradlew assembleDebug     # build
./gradlew installDebug      # instala num device/emulador conectado
./gradlew test              # testes unitários (app/src/test)
./gradlew connectedAndroidTest  # testes instrumentados (app/src/androidTest)
```

## Documentação

| Documento | Conteúdo |
|---|---|
| [`docs/PRD.md`](docs/PRD.md) | Product Requirements Document — visão de produto, público-alvo, escopo do MVP, mapa de telas, especificação funcional por tela, modelo de dados, sistema de design (cores, tipografia, componentes) e questões em aberto. Fonte de verdade de **o quê** e **por quê** construir. |
| [`ARCHITECTURE.md`](ARCHITECTURE.md) | Arquitetura de código — camadas, layout de pacotes, fluxo de dependência, passo a passo para adicionar uma feature nova, convenções de DI (Hilt) e persistência (Room). Fonte de verdade de **como** implementar. |

Ao trabalhar em uma feature: consulte o PRD para entender o comportamento esperado da
tela/fluxo, e o `ARCHITECTURE.md` para saber onde cada peça de código deve viver.

## Estrutura do projeto

```
com.tamaturgo.focca
├── core/           # DI (Hilt) e utilitários cross-cutting
├── data/           # Room (entities, DAOs), repositórios, mappers
├── domain/         # Modelos de negócio, interfaces de repositório, use cases
└── presentation/   # Tema Compose + telas (Composable + ViewModel) por feature
```

Detalhes de cada pacote em [`ARCHITECTURE.md`](ARCHITECTURE.md).
