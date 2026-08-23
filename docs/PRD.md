# PRD — Focca

### Workout consistency app (Android)

Document version: 1.0 · Base development document
---

## 1. Overview

**Focca** is not a bodybuilding app — it's a **habit** app. The core premise,
inherited from the product discussion that originated this project, is that most
workout apps optimize for the wrong metric (load, volume, PRs) when the real problem
most people have is **not being able to stick to the routine**.

The product is organized around a single question, repeated on almost every screen:
**"did you keep the pace this week?"**. Smart workout building, exercises, and load
progression exist in the product, but as support for that central goal — not as the
goal itself.

### 1.1 The four pillars

| Pillar | Question it answers |
|---|---|
| **Routine** | When should I train? |
| **Action** | Did I train today? |
| **Log** | What happened in the workout? |
| **Progress** | Am I improving? |

---

## 2. Problem and target audience

**Problem:** most people who start working out drop off not for lack of a workout
plan, but for lack of consistency. Apps focused on "building the perfect workout"
solve the wrong problem for this audience.

**Primary target audience:** gym beginners and intermediates who already know (or
have a ready-made plan from a personal trainer/friend) what to do, but struggle to
keep up a regular frequency. Not the audience looking for advanced periodization.

**Not the audience (for now):** advanced athletes who need fine-grained load
progression control, deload, RPE, etc. That's V2/V3.

---

## 3. MVP scope

### 3.1 Included in the MVP
- Weekly routine definition (training days vs. rest days)
- A/B/C workout split system, rotating by completed session (not by fixed weekday)
- Pre-loaded, searchable database of classic gym exercises
- Exercise checklist per workout (done/not done — without requiring load logging)
- Exercise detail modal (example video, instructions, optional set logging)
- In-progress workout timer
- Post-workout check-in (intensity, mood, muscle groups worked)
- History (calendar + timeline)
- Streak and simple weekly/monthly goals
- Physical progress (weight, photos, personal records)
- Basic statistics (frequency, time trained, consistency)
- Achievements (light gamification milestones)
- Quick log ("+" shortcut to log a workout/weight/measurement/photo/record outside
  the standard flow)
- Profile / settings hub

### 3.2 Out of MVP scope (V2+)
- Automatic load progression suggested by the app ("last time you did 60kg×8, try
  60kg×9 today")
- AI-generated workouts from goal/equipment/availability
- Illustrated body map by muscle group
- Deload, RPE, periodization
- Community / social comparison

---

## 4. Navigation architecture

Bottom navigation with 5 fixed tabs:

```
⌂ Home   ▤ Routine   🏆 Achievements   ◷ History   ◍ Profile
```

Modal flows (no tab bar, take over the full screen "on top of" the normal flow):
- Today's workout → Exercise (modal) → Workout in progress (timer) → How did it go?
  (check-in)
- Quick log (sheet triggered by the "+" FAB on Home)

Sub-screens reached via navigation, not their own tab:
- A/B/C split (from Routine)
- Progress, Statistics, Goals, Notifications, Preferences (from Profile)

### Full screen map

| # | Screen | How you get there | Active tab |
|---|---|---|---|
| 01 | Home | tab bar | Home |
| 02 | Today's workout | "Start workout" (Home) | Home |
| 03 | Exercise · detail (modal) | tap an exercise in the checklist | — (modal) |
| 04 | Workout in progress (timer) | "Start workout" | — |
| 05 | How did it go? (check-in) | "Finish workout" | — |
| 06 | Routine | tab bar | Routine |
| 07 | A/B/C workout split | "A/B/C Split ›" (Routine) | Routine |
| 08 | Achievements | tab bar | Achievements |
| 09 | History | tab bar | History |
| 10 | Profile (hub) | tab bar | Profile |
| 11 | Profile · Progress | menu (Profile) | Profile |
| 12 | Profile · Statistics | menu (Profile) | Profile |
| 13 | Quick log (sheet) | "+" FAB (Home) | — (modal) |

---

## 5. Functional spec per screen

### 5.1 Home
**Goal:** answer "what do I need to do today" in under 3 seconds.

Elements, top to bottom:
1. **Cadence strip** — 7 marks (one per day of the current week), visual state per
   day: trained / rest / today / missed / future.
2. **Streak card** — large number of consecutive days sticking to the routine + a
   flame icon.
3. **"Today's workout" card** — split badge (A/B/C), muscle group name for the day,
   time, location, a preview of 3 exercises (name + sets×reps) with a "+N exercises"
   indicator, primary "Start workout" button, secondary "Reschedule to another day"
   button.
4. **Weekly goal** — simple progress bar (e.g. 3/4).
5. **"+" FAB** — opens Quick log.

**Business rule:** a day with no scheduled workout shows the rest state, with no
workout CTA (don't force action on a rest day).

### 5.2 Today's workout (checklist)
**Goal:** give visibility into what makes up the workout before starting, without
forcing the person to "build" anything.

- Header with split badge (A/B/C) + muscle group name + time.
- Exercise list: name, target sets×reps, equipment, completed checkbox. Each row is
  tappable and opens the detail modal.
- Final "+ Add exercise" row → search in the exercise database.
- Primary CTA "Start workout" → opens the timer.

### 5.3 Exercise · detail (modal)
**Goal:** give execution context without leaving the workout flow.

Bottom sheet with:
- Exercise name + tags (muscle group, equipment)
- Example video (16:9 thumbnail with play — real player in the implementation)
- Short execution instructions (1 paragraph)
- Set logging: one row per set (weight, reps, completed check) + the target (e.g.
  "3×12")
- "Complete exercise" CTA

**Data note:** weight/reps logging here is **optional** — the MVP doesn't require it
to mark the exercise as done in the main checklist; it's an extra layer for those who
want it.

### 5.4 Workout in progress (timer)
**Goal:** a focus screen during the workout, minimal distraction.

- Central timer (circle) counting elapsed time.
- Status tag ("steady pace today").
- Single CTA "Finish workout".
- **No tab bar** — this is a modal/immersive state.

### 5.5 How did it go? (check-in)
**Goal:** capture the essentials of the workout in a few taps, with minimal friction.

- Total duration (automatically computed by the timer).
- Multi-select chips "What did you train?" (Chest, Back, Shoulders, Biceps, Triceps,
  Legs, Abs, Glutes) — pre-selected based on the day's A/B/C workout, editable.
- Intensity selection (Easy / Normal / Heavy).
- Post-workout mood selection (5 levels, emoji).
- "Save log" CTA → returns to Home, updates streak and history.

### 5.6 Routine
**Goal:** define/view which days of the week the person plans to train and which
workout (A/B/C) falls on each day.

- List of the 7 days. Each row: weekday badge, on/off toggle, and if active, workout
  badge (A/B/C) + muscle group + time.
- Rest days have no associated workout.
- "A/B/C Split ›" link leads to the split management screen.

**Important business rule:** the days here are a **schedule suggestion**, not the
source of truth for the A→B→C sequence. The actual sequence advances by completed
workout (see 5.7).

### 5.7 Workout split (A/B/C)
**Goal:** manage the template workouts that make up the routine.

- One card per workout: large badge, name (Workout A/B/C), muscle groups, number of
  exercises, estimated duration. Tap → edit the exercise list (reuses the layout of
  screen 5.2).
- "+ Create new workout" row (for D, cardio, mobility, etc. — the system is not
  limited to 3 letters).
- Fixed notice: *"Next in sequence: Workout B — even if you change the day, the
  A→B→C order is preserved."*

**Core MVP business rule:** the A→B→C rotation advances **on every completed
workout**, regardless of which day of the week it fell on. If the person skips a
day, they don't "lose" workout B — it's simply next, whenever that is.

### 5.8 Achievements
**Goal:** light positive reinforcement, without turning into a complex points
system.

- Progress bar "X of Y unlocked".
- Badge grid (3 columns): unlocked = colored with glow; locked = dashed outline,
  with a progress counter when applicable (e.g. "18/25").
- Initial milestone list: first workout, 7 days on routine, 10/25/50/100 workouts,
  first full month, 10h trained, 5 consecutive weeks.

### 5.9 History
**Goal:** retrospective view — what I did and when.

- Month calendar with trained days marked.
- Timeline below: each workout with date, type, duration, intensity.

### 5.10 Profile (hub)
**Goal:** central hub for configuration and access to long-term data.

- User summary (avatar, name, time using the app).
- Mini-stats (total workouts, current streak).
- Menu: Progress, Statistics, Goals, Achievements, Notifications, Preferences.

### 5.11 Profile · Progress
- Current weight + delta since the previous entry.
- Photo comparison (before/after).
- List of personal records per exercise (e.g. Bench press 80kg).

### 5.12 Profile · Statistics
- Monthly number cards: workouts, total time, % consistency, streak.
- Simple bar chart (workouts per week).
- Textual insight generated from history (e.g. "You tend to train better on
  Wednesdays") — a simple rule, no ML needed in the MVP.

### 5.13 Quick log
**Goal:** allow logging something outside the standard flow (without needing to
formally "start a workout").

- Sheet with shortcuts: Workout, Weight, Measurement, Photo, Record, Cancel.

---

## 6. Data model (logical view)

The `exercises.json` and `training_splits.json` files (delivered together with the
design) already model the exercise database and the A/B/C splits. The entities below
cover the rest of the product and serve as a reference for the Room tables:

```
User
 └─ id, name, created_at

Exercise                       (seed: exercises.json)
 └─ id, name, muscle_group, equipment

TrainingSplit                  (seed: training_splits.json)
 └─ id, label (A/B/C...), name, muscle_groups[], estimated_minutes

SplitExercise                  (join TrainingSplit ↔ Exercise)
 └─ split_id, exercise_id, sets, reps, order

RoutineDay
 └─ weekday, is_training_day, split_id_hint, time

WorkoutSession
 └─ id, date, split_id, started_at, finished_at, duration_seconds,
    intensity (easy/normal/heavy), mood (1-5), muscle_groups_tagged[]

SessionExerciseLog              (optional, per session)
 └─ session_id, exercise_id, set_number, weight, reps, completed

BodyMeasurement
 └─ date, weight, arm, chest, waist, hip, thigh, calf

ProgressPhoto
 └─ date, angle (front/side/back), uri

PersonalRecord
 └─ exercise_id, weight, date

Achievement
 └─ id, name, icon, unlocked_at (nullable), progress_current, progress_target

StreakState (derived, can be computed on-the-fly)
 └─ current_streak, longest_streak, last_trained_date
```

**Architecture note:** `WorkoutSession.split_id` is what defines the A→B→C
rotation — the "next letter" is always computed as the next one in the sequence
defined in `TrainingSplit`, based on the last completed session.
`RoutineDay.split_id_hint` is only a scheduling suggestion for the UI, not a hard
rule.

---

## 7. Design system

### 7.1 Brand direction
Not a "gym bro" app (aggressive red/black) nor a generic dark mode. It's a
**consistency dashboard**: near-black background with a slight blue tint, accents
with specific semantic jobs, technical typography with a single recurring signature
element.

### 7.2 Color palette

| Token | Hex | Use |
|---|---|---|
| `--page` | `#0B0C10` | page/app shell background |
| `--bg` | `#0F1115` | screen background |
| `--surface` | `#181B21` | cards |
| `--surface-2` | `#1F232B` | elements inside cards (rows, inputs) |
| `--surface-3` | `#262B34` | neutral highlight elements (bar tracks, photo borders) |
| `--line` | `#2A2E37` | borders, dividers |
| `--ember` | `#FF6A3D` | action/energy — streak, primary CTA, trained days, Workout A |
| `--ember-dim` | `#7A3B27` | streak card border |
| `--gold` | `#FFC857` | "now"/attention — current day, mood, Workout B |
| `--pulse` | `#5EEAD4` | neutral progress data — weight delta, Workout C |
| `--text` | `#F5F3EE` | primary text |
| `--text-muted` | `#90959F` | secondary text |
| `--text-faint` | `#565A63` | tertiary/disabled text |

**Usage rule:** every color has a fixed job, not a decorative one. Ember = action/
today. Gold = attention/now. Pulse = neutral data. Avoid using the three
interchangeably.

### 7.3 Typography

| Role | Font | Weight | Use |
|---|---|---|---|
| Display | **Space Grotesk** | 600–700 | screen titles, large numbers (streak, timer, weight) |
| Body | **Inter** | 400–600 | running text, labels, buttons |
| Utility/data | **JetBrains Mono** | 400–600 | times, dates, timer, statistics, weekday abbreviations, eyebrows |

Approximate scale (mobile):
- Hero number (streak/weight): 40px / 700 / Space Grotesk
- Screen title (`h2.title`): 21px / 700 / Space Grotesk
- Card name (split/exercise): 14.5px / 700 / Space Grotesk
- Body: 12–13px / 400–500 / Inter
- Eyebrow/label (uppercase, letter-spacing 0.1em): 10.5–12px / JetBrains Mono
- Micro (captions, counters): 9–10px / JetBrains Mono

### 7.4 Shape and spacing

After two iterations (too sharp a radius → then excessive rounding), the final
standard is **moderate**:

| Token | Value | Use |
|---|---|---|
| `--radius-lg` | 18px | large cards, sheets |
| `--radius-md` | 12px | default cards, buttons |
| `--radius-sm` | 8px | inputs, small badges |
| — | 50% | timer (circle), circular avatar where applicable, mood buttons |
| — | 16–20px | chips/pills (capsule shape) |
| — | 34px | phone frame (hardware corner, not a design system value) |

Content grid: screen side padding = 18px. Default gap between blocks = 12–16px.

### 7.5 Signature element: cadence strip

The recurring element that gives the product its identity: a row of 7 vertical
marks (one per weekday), used on Home. States:

- **Trained**: tall mark (24px), ember color, slight glow.
- **Planned rest**: short mark (10px), `--surface-3`, no glow.
- **Today**: tall mark (24px), gold color, glow.
- **Missed**: tall mark (24px), hollow, dashed `--text-faint` border.
- **Future**: short mark (10px), `--surface-2`.

This visual pattern (filled/hollow/short) also informs the History calendar,
keeping the same visual language at month scale.

### 7.6 A/B/C workout badge

Component reused on Home, Routine, Today's workout, and Workout split:

- Shape: square with 7px radius (small version, 22px) or 10px (large version, 34px).
- Letter in Space Grotesk 700.
- Color by split: A = ember (ember background at 16% opacity), B = gold (gold
  background at 16%), C = pulse (pulse background at 16%). Splits beyond C should
  either cycle to a fourth neutral color or reuse the pattern with an extra
  indicator — **not defined in the MVP**, see section 9.

### 7.7 Main components

- **Primary button**: solid ember background, dark text (`#1a0e08`), Space Grotesk
  700, 12px radius.
- **Secondary/ghost button**: `--line` border, `--text-muted` text, transparent.
- **Chip**: pill shape, `--line` border in inactive state; border + ember background
  at 14% + ember text in active state.
- **Card**: `--surface` + 1px `--line` border + 18px radius.
- **Bottom sheet/modal**: `rgba(5,6,10,0.72)` backdrop, sheet with 22px radius on
  top corners only, 36×4px centered handle.
- **Toggle switch**: 38×22px track, circular thumb; on = ember background.
- **Bottom navigation**: 5 items, icon + mono label, active item in ember with an
  indicator dot below.

### 7.8 Iconography

Tab bar icons are simple unicode glyphs (⌂ ▤ 🏆 ◷ ◍), not an illustrated icon
library — this keeps the "technical/dashboard" language and avoids depending on an
external icon set before the real implementation (which should swap in a consistent
vector icon library, e.g. Material Symbols or Phosphor, keeping similar weights and
proportions).

### 7.9 Motion (for implementation — not prototyped in the static mockup)
- Screen transitions: standard horizontal navigation slide (not defined in detail —
  follow Compose Navigation's default Material Motion).
- Bottom sheets: slide-up + backdrop fade, ~200–250ms.
- Toggle/checkbox: 150ms thumb position transition (already specified in the
  reference CSS).
- Avoid decorative animation outside of: exercise/workout completion feedback,
  sheet opening, and the streak glow when it increments.

---

## 8. Notes for Jetpack Compose implementation

- The color tokens (section 7.2) map directly to `Color.kt` / a custom
  `ColorScheme` (the product is dark-first; no light mode spec in this MVP).
- The three radius weights (7.4) become `Shapes` in `MaterialTheme`
  (small/medium/large).
- Typography (7.3) becomes a custom `Typography` — three `FontFamily`s (Space
  Grotesk, Inter, JetBrains Mono via the `google-fonts` provider or bundled in
  `res/font`).
- The cadence strip (7.5) and the A/B/C badge (7.6) should become **reusable
  composables** (`CadenceRow`, `SplitBadge`) from the start — each is used on at
  least 3 screens.
- The exercise modal and quick log are good candidates for Compose's
  `ModalBottomSheet`.
- The timer (5.4) needs a `Service`/`ViewModel` that survives configuration
  changes and, ideally, runs as a foreground service in case the app can be
  minimized during the workout.

---

## 9. Open questions

1. **Splits beyond C (D, E...)**: the design allows creating new workouts, but the
   badge color system only covers A/B/C. Define an extended palette or switch to a
   procedural scheme (hash of the letter → color) before implementing.
2. **Weight/reps in check-in vs. in the modal**: today, set logging is optional and
   only lives in the exercise modal. Decide whether this should automatically feed
   Personal Records (Progress) when the logged load beats the current record.
3. **"Missed workout" rule**: the cadence strip visually marks a day as "missed",
   but the business rule for when that happens (end of day? N hours after the
   scheduled time?) is not defined.
4. **Notifications**: mentioned in the Profile menu, but the content/timing of the
   reminders (from the original document: pre-workout reminder, missed workout
   reminder, daily reminder) still has no dedicated screen or trigger rules.
5. **Goals**: a menu item created under Profile, with no screen — today the weekly
   goal only lives as a fixed value (3/4) on the Home card.

---

## 10. Success metrics (suggested)

Since the product is about consistency, the north-star metrics should reflect that,
not usage volume:

- **Streak retention**: % of users who keep a streak ≥ 1 after 2, 4, and 8 weeks.
- **Scheduled workout completion rate**: completed workouts ÷ workouts scheduled in
  Routine.
- **"Reschedule" usage**: a healthy signal if used (shows the person is adjusting
  instead of giving up) — watch for it being used as a way to indefinitely avoid the
  workout.
- **Logging depth**: % of check-ins with muscle group chips filled in vs. skipped
  (indicates whether the data is considered useful enough by the user to fill in).
