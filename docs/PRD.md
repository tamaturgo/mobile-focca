# PRD — Focca

### App de consistência de treino (Android)

Versão do documento: 1.0 · Documento base de desenvolvimento
---

## 1. Visão geral

**Focca** não é um app de musculação — é um app de **hábito**. A premissa central, herdada da discussão de produto que originou este projeto, é que a maior parte dos apps de treino otimiza para o dado errado (carga, volume, PRs) quando o problema real da maioria das pessoas é **não conseguir manter a rotina**.

O produto se organiza em torno de uma pergunta única, repetida em quase toda tela: **"você manteve o ritmo essa semana?"**. Montagem inteligente de treino, exercícios e progressão de carga existem no produto, mas como suporte a esse objetivo central — não como o objetivo em si.

### 1.1 Os quatro pilares

| Pilar | Pergunta que responde |
|---|---|
| **Rotina** | Quando eu deveria treinar? |
| **Ação** | Eu treinei hoje? |
| **Registro** | O que aconteceu no treino? |
| **Evolução** | Eu estou melhorando? |

---

## 2. Problema e público-alvo

**Problema:** a maioria das pessoas que começa a treinar abandona não por falta de plano de treino, mas por falta de consistência. Apps focados em "montar o treino perfeito" resolvem o problema errado para esse público.

**Público-alvo primário:** iniciantes e intermediários de academia que já sabem (ou têm um treino pronto de um personal/amigo) o que fazer, mas têm dificuldade em manter frequência. Não é o público que quer um app de periodização avançada.

**Não-público (por ora):** atletas avançados que precisam de controle fino de progressão de carga, deload, RPE etc. Isso é V2/V3.

---

## 3. Escopo do MVP

### 3.1 Incluído no MVP
- Definição de rotina semanal (dias de treino x descanso)
- Sistema de divisão de treino **A/B/C**, com rotação por sessão concluída (não por dia fixo)
- Banco de exercícios clássicos de academia, pré-cadastrado, pesquisável
- Checklist de exercícios por treino (feito/não feito — sem obrigar log de carga)
- Modal de detalhe do exercício (vídeo de exemplo, instrução, registro opcional de séries)
- Timer de treino em andamento
- Check-in pós-treino (intensidade, humor, grupos musculares trabalhados)
- Histórico (calendário + linha do tempo)
- Sequência (streak) e metas semanais/mensais simples
- Evolução física (peso, fotos, recordes pessoais)
- Estatísticas básicas (frequência, tempo treinado, consistência)
- Conquistas (marcos de gamificação leve)
- Registro rápido (atalho "+" para logar treino/peso/medida/foto/recorde fora do fluxo padrão)
- Perfil / hub de configurações

### 3.2 Fora do MVP (V2+)
- Progressão automática de carga sugerida pelo app ("na última vez você fez 60kg×8, tente 60kg×9 hoje")
- Geração de treino por IA a partir de objetivo/equipamento/disponibilidade
- Mapa corporal ilustrado por grupo muscular — **tentamos no design e removemos**; a ilustração não comunicava bem e o dado (frequência por grupo muscular) já é coberto de forma mais confiável pelos chips de check-in + histórico. Pode voltar em V2 com um formato mais simples (ex.: lista/heatmap tabular em vez de ilustração de corpo).
- Deload, RPE, periodização
- Comunidade / comparação social

---

## 4. Arquitetura de navegação

Bottom navigation com 5 abas fixas:

```
⌂ Início   ▤ Rotina   🏆 Conquistas   ◷ Histórico   ◍ Perfil
```

Fluxos modais (sem tab bar, ocupam a tela inteira "por cima" do fluxo normal):
- Treino do dia → Exercício (modal) → Treino em andamento (timer) → Como foi? (check-in)
- Registro rápido (sheet acionado pelo FAB "+" na Início)

Sub-telas alcançadas por navegação, não por tab própria:
- Divisão A/B/C (a partir de Rotina)
- Evolução, Estatísticas, Metas, Notificações, Preferências (a partir de Perfil)

### Mapa completo de telas

| # | Tela | Como se chega | Tab ativa |
|---|---|---|---|
| 01 | Início | tab bar | Início |
| 02 | Treino do dia | "Começar treino" (Início) | Início |
| 03 | Exercício · detalhe (modal) | tap num exercício da checklist | — (modal) |
| 04 | Treino em andamento (timer) | "Iniciar treino" | — |
| 05 | Como foi? (check-in) | "Finalizar treino" | — |
| 06 | Rotina | tab bar | Rotina |
| 07 | Divisão de treino A/B/C | "Divisão A/B/C ›" (Rotina) | Rotina |
| 08 | Conquistas | tab bar | Conquistas |
| 09 | Histórico | tab bar | Histórico |
| 10 | Perfil (hub) | tab bar | Perfil |
| 11 | Perfil · Evolução | menu (Perfil) | Perfil |
| 12 | Perfil · Estatísticas | menu (Perfil) | Perfil |
| 13 | Registro rápido (sheet) | FAB "+" (Início) | — (modal) |

---

## 5. Especificação funcional por tela

### 5.1 Início
**Objetivo:** responder "o que eu preciso fazer hoje" em menos de 3 segundos.

Elementos, de cima para baixo:
1. **Traço de cadência** — 7 marcas (uma por dia da semana atual), estado visual por dia: treinado / descanso / hoje / perdido / futuro.
2. **Streak card** — número grande de dias consecutivos cumprindo a rotina + ícone de chama.
3. **Card "treino de hoje"** — selo do treino (A/B/C), nome do grupo muscular do dia, horário, local, preview de 3 exercícios (nome + séries×reps) com indicador "+N exercícios", botão primário "Começar treino", botão secundário "Remarcar para outro dia".
4. **Meta semanal** — barra de progresso simples (ex.: 3/4).
5. **FAB "+"** — abre o Registro rápido.

**Regra de negócio:** dia sem treino programado mostra estado de descanso, sem CTA de treino (não force ação em dia de descanso).

### 5.2 Treino do dia (checklist)
**Objetivo:** dar visibilidade do que compõe o treino antes de começar, sem obrigar a pessoa a "montar" nada.

- Cabeçalho com selo do treino (A/B/C) + nome do grupo muscular + horário.
- Lista de exercícios: nome, séries×reps alvo, equipamento, checkbox de concluído. Cada linha é tocável e abre o modal de detalhe.
- Linha final "+ Adicionar exercício" → busca na base de exercícios.
- CTA primário "Iniciar treino" → abre o timer.

### 5.3 Exercício · detalhe (modal)
**Objetivo:** dar contexto de execução sem sair do fluxo do treino.

Bottom sheet com:
- Nome do exercício + tags (grupo muscular, equipamento)
- Vídeo de exemplo (thumbnail 16:9 com play — player real na implementação)
- Instrução curta de execução (1 parágrafo)
- Registro de séries: linha por série (peso, reps, check de concluída) + a meta (ex. "3×12")
- CTA "Concluir exercício"

**Nota de dado:** o registro de peso/reps aqui é **opcional** — o MVP não exige isso para marcar o exercício como feito na checklist principal; é uma camada extra para quem quer.

### 5.4 Treino em andamento (timer)
**Objetivo:** tela de foco durante o treino, mínima distração.

- Timer central (círculo) contando o tempo decorrido.
- Tag de status ("ritmo constante hoje").
- CTA único "Finalizar treino".
- **Sem tab bar** — é um estado modal/imersivo.

### 5.5 Como foi? (check-in)
**Objetivo:** capturar o essencial do treino em poucos toques, sem fricção.

- Duração total (calculada automaticamente pelo timer).
- Chips de seleção múltipla "O que você treinou?" (Peito, Costas, Ombro, Bíceps, Tríceps, Perna, Abdômen, Glúteo) — pré-marcados de acordo com o treino A/B/C do dia, editáveis.
- Seleção de intensidade (Fácil / Normal / Pesado).
- Seleção de humor pós-treino (5 níveis, emoji).
- CTA "Salvar registro" → volta para Início, atualiza streak e histórico.

### 5.6 Rotina
**Objetivo:** definir/visualizar em quais dias da semana a pessoa pretende treinar e qual treino (A/B/C) cai em cada dia.

- Lista dos 7 dias. Cada linha: badge do dia da semana, toggle liga/desliga, e se ativo, selo do treino (A/B/C) + grupo muscular + horário.
- Dias de descanso não têm treino associado.
- Link "Divisão A/B/C ›" leva à tela de gestão dos treinos.

**Regra de negócio importante:** os dias aqui são **sugestão de agenda**, não a fonte de verdade da sequência A→B→C. A sequência real avança por treino concluído (ver 5.7).

### 5.7 Divisão de treino (A/B/C)
**Objetivo:** gerenciar os treinos-modelo que compõem a rotina.

- Um card por treino: selo grande, nome (Treino A/B/C), grupos musculares, nº de exercícios, duração estimada. Tap → edição da lista de exercícios (reaproveita o layout da tela 5.2).
- Linha "+ Criar novo treino" (para D, cardio, mobilidade etc. — o sistema não é limitado a 3 letras).
- Aviso fixo: *"Próximo da sequência: Treino B — mesmo que você troque o dia, a ordem A→B→C se mantém."*

**Regra de negócio central do MVP:** a rotação A→B→C avança **a cada treino concluído**, independentemente de qual dia da semana ele caiu. Se a pessoa pula um dia, ela não "perde" o treino B — ele simplesmente é o próximo, seja quando for.

### 5.8 Conquistas
**Objetivo:** reforço positivo leve, sem virar sistema de pontos complexo.

- Barra de progresso "X de Y desbloqueadas".
- Grid de selos (3 colunas): desbloqueado = colorido com glow; bloqueado = contorno tracejado, com contador de progresso quando aplicável (ex. "18/25").
- Lista inicial de marcos: primeiro treino, 7 dias de rotina, 10/25/50/100 treinos, primeiro mês completo, 10h treinadas, 5 semanas consecutivas.

### 5.9 Histórico
**Objetivo:** visão retrospectiva — o que eu fiz e quando.

- Calendário do mês com dias treinados marcados.
- Linha do tempo abaixo: cada treino com data, tipo, duração, intensidade.

### 5.10 Perfil (hub)
**Objetivo:** ponto central de configuração e acesso aos dados de longo prazo.

- Resumo do usuário (avatar, nome, tempo de app).
- Mini-stats (treinos totais, streak atual).
- Menu: Evolução, Estatísticas, Metas, Conquistas, Notificações, Preferências.

### 5.11 Perfil · Evolução
- Peso atual + delta desde o registro anterior.
- Comparação de fotos (antes/depois).
- Lista de recordes pessoais por exercício (ex.: Supino 80kg).

### 5.12 Perfil · Estatísticas
- Cards de números do mês: treinos, tempo total, % de consistência, streak.
- Gráfico de barras simples (treinos por semana).
- Insight textual gerado a partir do histórico (ex.: "Você costuma treinar melhor às quartas-feiras") — regra simples, não precisa de ML no MVP.

### 5.13 Registro rápido
**Objetivo:** permitir logar algo fora do fluxo padrão (sem precisar "iniciar um treino" formalmente).

- Sheet com atalhos: Treino, Peso, Medida, Foto, Recorde, Cancelar.

---

## 6. Modelo de dados (visão lógica)

Os arquivos `exercises.json` e `training_splits.json` (entregues junto com o design) já modelam a base de exercícios e as divisões A/B/C. As entidades abaixo cobrem o restante do produto e servem de referência para as tabelas Room:

```
User
 └─ id, name, created_at

Exercise                       (seed: exercises.json)
 └─ id, name, muscle_group, equipment

TrainingSplit                  (seed: training_splits.json)
 └─ id, label (A/B/C...), name, muscle_groups[], estimated_minutes

SplitExercise                  (junção TrainingSplit ↔ Exercise)
 └─ split_id, exercise_id, sets, reps, order

RoutineDay
 └─ weekday, is_training_day, split_id_hint, time

WorkoutSession
 └─ id, date, split_id, started_at, finished_at, duration_seconds,
    intensity (fácil/normal/pesado), mood (1-5), muscle_groups_tagged[]

SessionExerciseLog              (opcional, por sessão)
 └─ session_id, exercise_id, set_number, weight, reps, completed

BodyMeasurement
 └─ date, weight, arm, chest, waist, hip, thigh, calf

ProgressPhoto
 └─ date, angle (frente/lado/costas), uri

PersonalRecord
 └─ exercise_id, weight, date

Achievement
 └─ id, name, icon, unlocked_at (nullable), progress_current, progress_target

StreakState (derivado, pode ser calculado on-the-fly)
 └─ current_streak, longest_streak, last_trained_date
```

**Observação de arquitetura:** `WorkoutSession.split_id` é o que define a rotação A→B→C — a "próxima letra" é sempre calculada como a próxima na sequência definida em `TrainingSplit`, a partir da última sessão concluída. `RoutineDay.split_id_hint` é só uma sugestão de agenda para a UI, não uma regra rígida.

---

## 7. Sistema de design

### 7.1 Direção de marca
Não é um app "gym bro" (vermelho/preto agressivo) nem o dark-mode genérico. É um **painel de constância**: fundo quase-preto com leve tom azulado, acentos com trabalho semântico específico, tipografia técnica com um único elemento assinatura recorrente.

### 7.2 Paleta de cores

| Token | Hex | Uso |
|---|---|---|
| `--page` | `#0B0C10` | fundo da página/app shell |
| `--bg` | `#0F1115` | fundo das telas |
| `--surface` | `#181B21` | cards |
| `--surface-2` | `#1F232B` | elementos dentro de cards (rows, inputs) |
| `--surface-3` | `#262B34` | elementos de destaque neutro (trilhos de barra, bordas de foto) |
| `--line` | `#2A2E37` | bordas, divisores |
| `--ember` | `#FF6A3D` | ação/energia — streak, CTA primário, dias treinados, Treino A |
| `--ember-dim` | `#7A3B27` | borda do card de streak |
| `--gold` | `#FFC857` | "agora"/atenção — dia atual, humor, Treino B |
| `--pulse` | `#5EEAD4` | dado neutro de progresso — delta de peso, Treino C |
| `--text` | `#F5F3EE` | texto principal |
| `--text-muted` | `#90959F` | texto secundário |
| `--text-faint` | `#565A63` | texto terciário/desabilitado |

**Regra de uso:** cada cor tem um trabalho fixo, não é decorativa. Ember = ação/hoje. Gold = atenção/agora. Pulse = dado neutro. Evitar usar as três de forma intercambiável.

### 7.3 Tipografia

| Papel | Fonte | Peso | Uso |
|---|---|---|---|
| Display | **Space Grotesk** | 600–700 | títulos de tela, números grandes (streak, timer, peso) |
| Corpo | **Inter** | 400–600 | texto corrido, labels, botões |
| Utilitário/dado | **JetBrains Mono** | 400–600 | horários, datas, timer, estatísticas, siglas de dia da semana, eyebrows |

Escala aproximada (mobile):
- Número hero (streak/peso): 40px / 700 / Space Grotesk
- Título de tela (`h2.title`): 21px / 700 / Space Grotesk
- Nome de card (split/exercício): 14.5px / 700 / Space Grotesk
- Corpo: 12–13px / 400–500 / Inter
- Eyebrow/label (uppercase, letter-spacing 0.1em): 10.5–12px / JetBrains Mono
- Micro (captions, contadores): 9–10px / JetBrains Mono

### 7.4 Forma e espaçamento

Depois de duas iterações (raio cortante demais → depois arredondamento excessivo), o padrão final é **moderado**:

| Token | Valor | Uso |
|---|---|---|
| `--radius-lg` | 18px | cards grandes, sheets |
| `--radius-md` | 12px | cards padrão, botões |
| `--radius-sm` | 8px | inputs, badges pequenos |
| — | 50% | timer (círculo), avatar circular quando aplicável, mood buttons |
| — | 16–20px | chips/pills (formato cápsula) |
| — | 34px | moldura do celular (canto de hardware, não é um valor de design system) |

Grid de conteúdo: padding lateral de tela = 18px. Gap padrão entre blocos = 12–16px.

### 7.5 Elemento-assinatura: traço de cadência

O elemento recorrente que dá identidade ao produto: uma fileira de 7 marcas verticais (uma por dia da semana), usada na Início. Estados:

- **Treinado**: traço alto (24px), cor ember, leve glow.
- **Descanso planejado**: traço curto (10px), `--surface-3`, sem glow.
- **Hoje**: traço alto (24px), cor gold, glow.
- **Perdido**: traço alto (24px), vazado, borda tracejada `--text-faint`.
- **Futuro**: traço curto (10px), `--surface-2`.

Esse padrão visual (cheio/vazado/curto) é o que também informa o calendário do Histórico, mantendo a mesma linguagem em escala de mês.

### 7.6 Selo de treino A/B/C

Componente reutilizado em Início, Rotina, Treino do dia e Divisão de treino:

- Formato: quadrado com raio 7px (versão pequena, 22px) ou 10px (versão grande, 34px).
- Letra em Space Grotesk 700.
- Cor por split: A = ember (fundo ember a 16% opacidade), B = gold (fundo gold a 16%), C = pulse (fundo pulse a 16%). Splits além de C devem ciclar para uma quarta cor neutra ou reutilizar o padrão com um indicador extra — **não está definido no MVP**, ver seção 9.

### 7.7 Componentes principais

- **Botão primário**: fundo ember sólido, texto escuro (`#1a0e08`), Space Grotesk 700, raio 12px.
- **Botão secundário/ghost**: borda `--line`, texto `--text-muted`, transparente.
- **Chip**: pílula, borda `--line` no estado inativo; borda + fundo ember a 14% + texto ember no estado ativo.
- **Card**: `--surface` + borda `--line` 1px + raio 18px.
- **Bottom sheet/modal**: backdrop `rgba(5,6,10,0.72)`, sheet com raio 22px só no topo, handle central de 36×4px.
- **Toggle switch**: trilho 38×22px, thumb circular; ligado = fundo ember.
- **Bottom navigation**: 5 itens, ícone + label em mono, item ativo em ember com ponto indicador abaixo.

### 7.8 Iconografia

Ícones da tab bar são glifos unicode simples (⌂ ▤ 🏆 ◷ ◍), não uma biblioteca de ícones ilustrados — mantém a linguagem "técnica/painel" e evita depender de um icon set externo antes da implementação real (que deve trocar por uma lib de ícones vetoriais consistente, ex. Material Symbols ou Phosphor, mantendo pesos e proporções similares).

### 7.9 Motion (para implementação — não prototipado no mockup estático)
- Transições de tela: slide horizontal padrão de navegação (não definido em detalhe — seguir Material Motion padrão do Compose Navigation).
- Bottom sheets: slide-up + fade do backdrop, ~200–250ms.
- Toggle/checkbox: transição de 150ms na posição do thumb (já especificado no CSS de referência).
- Evitar animação decorativa fora de: feedback de conclusão de treino/exercício, abertura de sheets, e o glow do streak quando ele incrementa.

---

## 8. Notas para implementação em Jetpack Compose

- Os tokens de cor (seção 7.2) mapeiam diretamente para `Color.kt` / `ColorScheme` customizado (o produto é dark-first; não há especificação de light mode neste MVP).
- Os três pesos de raio (7.4) viram `Shapes` no `MaterialTheme` (small/medium/large).
- Tipografia (7.3) vira `Typography` customizado — três `FontFamily` (Space Grotesk, Inter, JetBrains Mono via `google-fonts` provider ou embutidas em `res/font`).
- O traço de cadência (7.5) e o selo A/B/C (7.6) devem virar **composables reutilizáveis** (`CadenceRow`, `SplitBadge`) desde o início — são usados em no mínimo 3 telas cada.
- O modal de exercício e o registro rápido são bons candidatos a `ModalBottomSheet` do Compose.
- O timer (5.4) precisa de um `Service`/`ViewModel` que sobreviva a mudanças de configuração e, idealmente, rode em foreground service se o app puder ser minimizado durante o treino.

---

## 9. Questões em aberto

1. **Divisão além de C (D, E...)**: o design permite criar novos treinos, mas o sistema de cor dos selos só cobre A/B/C. Definir paleta estendida ou trocar para um esquema procedural (hash da letra → cor) antes de implementar.
2. **Peso/reps no check-in vs. no modal**: hoje o registro de série é opcional e vive só no modal do exercício. Decidir se isso deve refletir automaticamente em Recordes Pessoais (Evolução) quando a carga registrada supera o recorde atual.
3. **Regra de "treino perdido"**: o traço de cadência marca visualmente um dia como "perdido", mas a regra de negócio de quando isso acontece (fim do dia? N horas depois do horário programado?) não está definida.
4. **Notificações**: mencionadas no menu de Perfil, mas o conteúdo/timing dos lembretes (do documento original: lembrete pré-treino, lembrete de treino perdido, lembrete diário) ainda não tem tela própria nem regras de disparo especificadas.
5. **Metas**: item de menu criado em Perfil, sem tela — hoje a meta semanal vive só como um valor fixo (3/4) no card da Início.

---

## 10. Métricas de sucesso (sugestão)

Como o produto é sobre consistência, as métricas norte devem refletir isso, não volume de uso:

- **Retenção de streak**: % de usuários que mantêm streak ≥ 1 depois de 2, 4 e 8 semanas.
- **Taxa de conclusão de treino programado**: treinos concluídos ÷ treinos programados na Rotina.
- **Uso do "Remarcar"**: sinal saudável se usado (mostra que a pessoa está ajustando em vez de abandonar) — vigiar se está sendo usado como forma de evitar o treino indefinidamente.
- **Profundidade de registro**: % de check-ins com chips de grupo muscular preenchidos vs. pulados (indica se o dado é considerado útil o suficiente pelo usuário para preencher).
