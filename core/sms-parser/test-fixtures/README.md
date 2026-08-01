# test-fixtures

Only **sanitized** samples go here. Before committing any SMS sample:

- Redact account/card numbers
- Redact phone numbers
- Redact real names
- Redact exact balances (use round/fake numbers instead)

Keep raw, pre-sanitization originals in a local-only, gitignored folder
(`test-fixtures/_raw/`) if you need them for reference — never commit
that folder.

## Layout

One folder per bank/card issuer:

```
test-fixtures/
  sbi/
  icici/
  icici_cc/
  hdfc_cc/
  axis_cc/
  federal_cc/
  indusind_cc/
```

Each sample should have a hand-written expected parsed JSON output
alongside it (`amount`, `type`, `merchant`, `account`, `date`,
`balance`, `refNo`) — this becomes the regression suite (see Phase 0.5).

Include deliberate edge cases: OTP messages, promotional messages,
failed-transaction alerts, duplicate sends of the same transaction, and
at least one malformed/unrecognized sample per issuer.
