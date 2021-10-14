## Description

This is a simple banking application, that handles *customers*, *accounts* and *transactions*.

### You can

- do CRUD methods on entities
- deposit to account
- withdraw from account
- transfer between accounts
- get the balance of accounts

### How to try

You can try it in Postman. If you want to create a new entity, you don't have to give an id, only the other fields.

Transaction examples (JSON):

- **deposit**:

  "fromAccountId": null,
  "toAccountId": 1,
  "amount": 8000

- **withdraw**:

  "fromAccountId": 1,
  "toAccountId": null,
  "amount": 5000

- **transfer**:

  "fromAccountId": 1,
  "toAccountId": 2,
  "amount": 3000

Get account balance:

- with account number: `/account/balance?accountNumber=...`
- with account id: `/account/balance/..`