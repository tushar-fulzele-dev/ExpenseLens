# ADR 0001: Core Architecture Decisions (v1 MVP)

**Status:** Accepted
**Date:** 2026-08-01
**Related:** Issue #18, `expense-tracker-roadmap.md` Section 0, `CLAUDE.md`

## Context

ExpenseLens is a solo, personal-use Android app for parsing bank/card SMS
into a local expense tracker. Before any feature code is written, the
following architecture decisions need to be locked in so later tickets have
a stable foundation to build against.

## Decisions

### 1. SMS Access Strategy
- **Primary:** `READ_SMS` + `RECEIVE_SMS` permissions, with a `BroadcastReceiver`
  for real-time ingestion and a one-time historical import on first launch.
- **Backup:** `NotificationListenerService` as a fallback path, for cases
  where a `BroadcastReceiver` is missed (e.g., device was off, OEM
  background-kill behavior on MIUI/OneUI-style ROMs).
- **Rationale:** Two independent capture paths reduce the risk of silently
  missing a transaction, without introducing any network dependency.

### 2. Distribution Model
- Sideloaded, self-signed APK installed via `adb install` or direct file
  transfer to the developer's own device(s) only.
- No Play Store listing, no privacy policy page, no Data Safety Form, no
  staged rollout — none of the Play Store compliance package applies to v1.

### 3. Local Database Encryption
- Room database encrypted at rest via **SQLCipher**.
- Encryption key generated and stored in the **Android Keystore** —
  never hardcoded, never stored in plaintext, never committed to source
  control.

### 4. Categorization Approach
- **Rule table only.** Merchant-keyword-to-category matching
  (e.g., `SWIGGY` → Food), backed by a `MerchantRule` table that learns
  from inline user recategorization.
- Explicitly **no AI/ML model** and **no cloud API calls** for
  categorization — this keeps the app's zero-network-calls guarantee intact.

### 5. App Lock
- `BiometricPrompt` gate shown on every app open (cold start and
  resume-from-background), before any transaction data is rendered.

### 6. SDK Targets
- `minSdk = 26`
- `targetSdk` = latest stable Android SDK at time of build.

### 7. Backup Strategy
- Simple, optional **"Copy DB to Downloads"** button.
- Unencrypted, on-device only, no export/share UI, no cloud sync.
- Purpose is solely to protect against data loss on factory reset — not a
  full backup/restore system.

## Consequences

- These decisions are binding for all v1 tickets under Phases 1–7 of the
  roadmap. Any ticket that would contradict one of them (e.g., adding a
  cloud categorization call, or adding Play Store compliance work) is
  out of scope and should be flagged rather than built silently, per the
  Scope Discipline rule in `CLAUDE.md`.
- These are **not** frozen forever — they're v1 defaults. Revisiting any of
  them (e.g., adding encrypted export in v2) should be a new ADR, not a
  silent edit to this one.

## Acceptance Criteria (Issue #18)

- [x] SMS access strategy documented (primary + backup)
- [x] Distribution model documented
- [x] Local DB encryption approach documented
- [x] Categorization approach documented (rule-based, no AI/cloud)
- [x] App lock mechanism documented
- [x] Min/target SDK documented
- [x] Backup approach documented
