# ExpenseLens — SMS-Based Expense Tracker (Personal Use, India)

**Solo, personal-use Android app.** Sideloaded APK only — no Play Store, no AI/cloud calls, no server backend.  
**Roadmap reference:** `expense-tracker-roadmap.md` (v3).

---

## 1. Scope Discipline

This is a v1 personal-use MVP. Before implementing anything, verify that it is strictly in scope.

### In Scope for v1
* **Bank Parsers:** SBI, ICICI (bank accounts)
* **Credit Card Parsers:** ICICI, SBI, HDFC, Axis, Federal, IndusInd
* **Pipeline:** SMS Import → Parser → Database → Categorization → Dashboard → Transaction List
* **Inline Recategorization:** Tap a category chip (ships day one, trains `MerchantRule`)
* **Simple Unencrypted Local Backup:** "Copy DB to Downloads" button
* **Biometric App Lock**

### Explicitly Deferred to v2+ (Do not build without being asked)
* Recurring transaction detection, budget alerts
* PDF/CSV export, advanced analytics/reports screen
* Dedicated category-management screen (custom category creation/merging)
* Any bank/card parser outside the initial eight issuers listed above
* Manual transaction entry UI (schema is ready via `TransactionSource`, but do not build the UI yet)
* Play Store compliance package

> **Rule:** If a request would add scope beyond this list, flag it before building — do not silently expand scope.

---

## 2. Architecture & Tech Stack

* **Core Language & UI:** Kotlin, Jetpack Compose, Hilt DI, MVVM + Clean Architecture
* **Database & Security:** Room with SQLCipher encryption-at-rest; key stored in Android Keystore (never hardcoded)
* **Background Processing:** WorkManager for background sync/reprocessing
* **Concurrency:** StateFlow / Flow end to end (no LiveData mixing)
* **Project Modules:** `:app`, `:core:data`, `:core:domain`, `:core:sms-parser`, `:feature:dashboard`
* **SDK Targets:** `minSdk 26`, `targetSdk` latest stable

---

## 3. Build, Test, & Quality Commands

* **Unit Tests:** `./gradlew test`
* **Instrumented Tests:** `./gradlew connectedAndroidTest`
* **Linting:** `./gradlew detekt` / `./gradlew ktlintCheck`
* **Test Coverage:** Target >90% coverage on `:core:sms-parser` (highest-risk code in the app)

---

## 4. Parser Rules (The Money Feature — Test-First, Always)

* **Isolation:** One `BankSmsParser` implementation per issuer. Never use a shared regex blob across banks.
* **Fixture-First (No Exceptions):** Every parser change starts by adding/updating a sample in `core/sms-parser/test-fixtures/<issuer>/`, watching it fail against the current parser, and then fixing the parser. Never write parser logic against a guess.
* **Filtering:** Non-transactional messages (OTP, promotional, balance-only) must be filtered out and never parsed as transactions.
* **Deduplication:** Duplicate SMS (same amount + ref number + timestamp window) → exactly one transaction record.
* **Fallback / Unparsed Queue:** Low-confidence or unrecognized formats route to the "unparsed" review queue. Never silently drop messages, never guess an amount, and never crash the pipeline on one bad message.
* **Unified Flow Handling:** Debit card spends and UPI debits are NOT separate entities — they arrive as ordinary "debited from a/c" bank SMS and are handled by the bank parser, not a dedicated parser.

---

## 5. Database Rules

* **Migrations:** Provide migration objects for every schema change from day one. Never use `fallbackToDestructiveMigration()` in this repo, including test scaffolding.
* **Transaction Source:** The `TransactionSource` enum is scoped to SMS only for now — do not pre-add `MANUAL`/`IMPORT` until the manual-entry feature is actively being built.

---

## 6. Privacy & Security — Non-Negotiable

* **Sanitization:** Never commit real or unredacted SMS content (no real account numbers, phone numbers, full names, or exact balances) anywhere under `test-fixtures/`. Sanitize first.
* **Credentials:** Never commit release keystores (`*.jks`, `*.keystore`) or any signing credentials.
* **Encryption Key:** Never hardcode the SQLCipher key — Android Keystore only.
* **Network Isolation:** This app makes zero network calls by design. Flag any change that would introduce a network call (including analytics or crash-reporting SDKs) before adding it.

---

## 7. Conventions & UI Guidelines

* **Accessibility:** Add content descriptions on charts and icons; verify contrast in both light and dark themes.
* **Pagination:** Paginate transaction lists using Paging3 rather than loading everything at once.
* **Error Logging:** Every SMS parse failure is logged locally and routed to the unparsed queue — never silently dropped, never crashing the app.

---

## 8. Workflow: Picking Up a Roadmap Ticket

* Always reference the specific ticket ID (e.g., `"Ticket 1.3.4"`) in your response and commit message.
* If a ticket's acceptance criteria/test aren't fully met, state so explicitly rather than marking it done.
