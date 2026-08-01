# ExpenseLens 🇮🇳

> A secure, on-device, automated SMS-based expense tracker tailored for the Indian banking ecosystem. Built with Kotlin, Jetpack Compose, and Room (SQLCipher).

## 🚀 Overview

**ExpenseLens** is a personal-use, privacy-first mobile application designed to automatically parse, categorize, and track financial transactions from SMS notifications. Built strictly for personal sideloading, it requires zero cloud services, no AI/ML dependencies, and no Google Play Console overhead. Every rupee spent via your supported bank accounts, credit cards, and UPI handles is captured seamlessly in the background and stored securely on your device.

---

## 🛠️ Tech Stack & Architecture

- **Language:** Kotlin
- **UI Framework:** Jetpack Compose (Material You dynamic color system, dark/light themes)
- **Architecture:** MVVM + Clean Architecture (`UI` → `ViewModel` → `UseCase` → `Repository` → `DataSource`)
- **Dependency Injection:** Hilt
- **Local Database:** Room with **SQLCipher** encryption-at-rest (keys managed via Android Keystore)
- **Background Processing:** WorkManager (with battery-optimization exemption for reliability)
- **Security:** BiometricPrompt (Fingerprint/Face unlock gate)

---

## 📱 Core Features (MVP Scope)

1. **Automated SMS Ingestion:** Real-time capture via `BroadcastReceiver` and one-time historical import via `ContentResolver` (with `NotificationListenerService` fallback).
2. **Robust Multi-Issuer Parser Engine:** Strategy-pattern parsers built specifically for Indian banks and card issuers:
   - **Bank Accounts:** SBI, ICICI
   - **Credit Cards:** ICICI, SBI, HDFC, Axis, Federal, IndusInd
   - **UPI & Debit Cards:** Automatically routed and captured via underlying bank SMS formats.
3. **Smart Rule-Based Categorization:** Seed ruleset covering top merchants (`SWIGGY`, `ZOMATO`, `AMAZON`, etc.) with **inline recategorization** (tap a category chip on any transaction row to train `MerchantRule` instantly).
4. **Comprehensive Dashboard:** Monthly totals, income vs. expense, net cash flow, spend-by-account/card carousels, and category breakdown charts.
5. **Unparsed/Review Inbox:** Catches ambiguous or unrecognized SMS for manual review and fixture improvement.
6. **Local Backup & Security:** Biometric app lock and a simple, unencrypted "Copy DB to Downloads" button for disaster recovery.

---

## 📂 Project Structure

```text
app/
core/
  ├── data/                 # Room DB, SQLCipher, repositories, entities
  ├── domain/               # Use cases, models, business logic
  └── sms-parser/           # Bank-specific parsers & test fixture corpus
feature/
  ├── dashboard/            # Dashboard screen & charts
  ├── transactions/         # Transaction list, search, filters & inline categorization
  ├── accounts/             # Account & credit card detail views
  └── review/               # Unparsed SMS review inbox
```

---

## 🧪 Phase 0.5: SMS Fixture & Test Corpus

To ensure parser reliability, **ExpenseLens** implements a test-first approach using real-world anonymized SMS fixtures before writing parser logic.

- **Corpus Location:** `core:sms-parser/test-fixtures/`
- **Structure:** One folder per issuer (`sbi/`, `icici/`, `icici_cc/`, `hdfc_cc/`, etc.) containing raw text samples and expected JSON outputs.
- **Workflow:** When a parser misses a transaction in the wild, a new fixture is added to the corpus, validated to fail, and then fixed in the parser engine.

---

## 🚀 Getting Started & Roadmap

Estimated development timeline: **~4–6 weeks** of AI-assisted solo development.

| Phase | Focus Area | Status |
| :--- | :--- | :--- |
| **Phase 0** | Architecture decisions & security spec | Completed |
| **Phase 0.5** | 100-200 sample SMS fixture/test corpus setup | Next |
| **Phase 1** | SMS Ingestion & Parser Core (SBI, ICICI, HDFC, Axis, Federal, IndusInd) | Planned |
| **Phase 2** | Encrypted Data Layer (Room + SQLCipher + Keystore) | Planned |
| **Phase 3** | Categorization Engine & Monthly Aggregations | Planned |
| **Phase 4** | UI/UX Build-out (Dashboard, Transaction List, Review Inbox, Biometrics) | Planned |
| **Phase 5** | Reliability & Security Hardening (WorkManager, Doze mode testing) | Planned |
| **Phase 6** | Testing & QA against real historical SMS import | Planned |
| **Phase 7** | Local signed APK release & sideloading | Planned |

---

## 🔒 Privacy & Security

- **100% Offline:** No telemetry, no third-party analytics, no cloud sync.
- **Encrypted Storage:** Database files are encrypted at rest using SQLCipher.
- **Gated Access:** App requires biometric authentication upon opening.

---
*Built for personal use via sideloaded APK.*
