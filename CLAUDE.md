# ExpenseLens — Project Instructions

Personal-use Android app that parses bank/card SMS into categorized expense
data. Single-user, no backend, no Play Store distribution (v1).

## Module layout

- `:app` — application shell, navigation, DI wiring
- `:core:domain` — use cases, plain Kotlin, no Android framework deps
- `:core:data` — Room DB, repositories, SQLCipher
- `:core:sms-parser` — SMS parsing engine + `test-fixtures/`
- `:feature:dashboard` — dashboard, transaction list, account/card screens

## Privacy rule (non-negotiable)

Never commit real SMS content, account/card numbers, phone numbers, real
names, or exact balances. Only sanitized samples go in
`core/sms-parser/test-fixtures/`. Raw originals stay in a local,
gitignored folder (`test-fixtures/_raw/`). See that folder's README for
the exact sanitization checklist before adding any new fixture.

Repo is private. SQLCipher key lives in Android Keystore only — never a
hardcoded string, never a committed properties file.

## Current scope (v1 — in)

Core pipeline: SMS Import → Parser → Database → Categorization →
Dashboard → Transaction List. Plus: biometric app lock, simple local
backup button, background sync via WorkManager.

Covered issuers: SBI, ICICI (bank), ICICI, SBI, HDFC, Axis, Federal,
IndusInd (credit cards).

## Deferred scope (v1 — explicitly out)

Do not build these unless the roadmap is updated first:
- Additional banks/cards beyond the 8 covered issuers (e.g. Kotak, PNB, etc.)
- Recurring transaction detection
- Budget alerts / notification digests
- PDF/CSV export
- Dedicated category-management screen (creating/merging custom categories)
- Advanced reports screen
- Play Store distribution / public listing

If asked to add something from this list, push back and point at this
section rather than starting the work.

## Conventions

- Rule-based categorization first (merchant keyword matching); user
  overrides persist via `MerchantRule` and auto-apply going forward — no
  ML/AI categorization in v1.
- Unknown merchant → `Uncategorized`, never silently misassigned.
- Use cases live in `:core:domain`, take repository interfaces, no
  Android framework dependency — keeps them unit-testable without
  instrumentation.
- Room `@Query` + `GROUP BY` is enough for aggregation at personal-data
  volume — skip nightly pre-aggregation jobs.
- Target >90% coverage on the parser layer specifically; it's the
  highest-risk code in the app.

## CI

`.github/workflows/ci.yml` runs lint (detekt/ktlint) + unit tests on
every PR. Should stay green on trivial/no-op PRs — if it doesn't,
that's a CI config problem, not a "skip CI" problem.

## Ticket references

Commit messages should reference the ticket ID they close (e.g.
`0.2.1`, `3.2`, `5.4`) so history stays traceable against the GitHub
Projects board.
