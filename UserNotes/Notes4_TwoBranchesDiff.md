## Branch Difference

### Branch 1 – Cucumber Reports + Screenshots

This branch contains the basic reporting implementation.

```text
Cucumber + Selenium + TestNG
          │
          ├── Cucumber HTML Report
          │
          └── Screenshot on Failure
```

### Main Components

```text
Hooks.java
    │
    ├── @Before
    │      └── Start WebDriver
    │
    └── @After
           ├── Check Scenario Status
           ├── If Failed → Capture Screenshot
           └── Quit WebDriver

ScreenshotUtil.java
    │
    └── Saves screenshots into:
        reports/screenshots/
```

### Generated Reports

```text
reports/
├── Cucumber.html
└── screenshots/
    ├── Add_a_new_employee_FAILED_2026-10-07_10-30-20.png
    └── Login_with_valid_credentials_FAILED_2026-10-07_10-35-10.png
```

---

# Branch 2 – Cucumber Reports + Screenshots + Extent Reports

This branch contains everything from Branch 1 plus **Extent Reports**.

```text
Cucumber + Selenium + TestNG
          │
          ├── Cucumber HTML Report
          │
          ├── Screenshot on Failure
          │
          └── Extent HTML Report
                    │
                    └── Screenshot attached
```

### Additional Components

```text
ExtentReportManager.java
    │
    ├── Creates Extent Report
    ├── Creates Test Entry
    ├── Stores Current Test
    └── Flushes Report

ExtentTestNGListener.java
    │
    └── Flushes Extent Report when execution finishes

Hooks.java
    │
    ├── @Before
    │      └── Creates Extent Test
    │
    └── @After
           ├── Check Scenario Status
           ├── PASS → Mark Extent Test as PASS
           │
           └── FAIL
                  ├── Mark Extent Test as FAIL
                  ├── Capture Screenshot
                  └── Attach Screenshot to Extent Report
```

### Generated Reports

```text
reports/
├── Cucumber.html
├── ExtentReport.html
└── screenshots/
    ├── Add_a_new_employee_FAILED_2026-10-07_10-30-20.png
    └── Login_with_valid_credentials_FAILED_2026-10-07_10-35-10.png
```

---

# Actual Difference Between the Branches

| Feature | Cucumber Report Branch | Extent Report Branch |
|---|---|---|
| Selenium | ✅ | ✅ |
| Cucumber | ✅ | ✅ |
| TestNG | ✅ | ✅ |
| Cucumber HTML Report | ✅ | ✅ |
| Screenshot on Failure | ✅ | ✅ |
| Screenshot Folder | ✅ | ✅ |
| ExtentReportManager | ❌ | ✅ |
| ExtentTestNGListener | ❌ | ✅ |
| Extent HTML Report | ❌ | ✅ |
| Screenshot inside Extent Report | ❌ | ✅ |
| ThreadLocal ExtentTest | ❌ | ✅ |

### Development Progress

```text
                    START
                      │
                      ▼
        Selenium + Cucumber Framework
                      │
                      ▼
       ┌─────────────────────────────┐
       │ Branch 1                    │
       │ Cucumber Report             │
       │ + Screenshots               │
       └─────────────────────────────┘
                      │
                      ▼
       ┌─────────────────────────────┐
       │ Branch 2                    │
       │ Cucumber Report             │
       │ + Screenshots               │
       │ + Extent Report             │
       └─────────────────────────────┘
                      │
                      ▼
                  NEXT STEP
                      │
                      ▼
              Jenkins CI/CD
                      │
                      ▼
       Automated Execution + Reports
```

### In Simple Words

**Branch 1** gives us:

> "The test ran, and if it failed, give me a screenshot."

**Branch 2** gives us:

> "The test ran, give me a professional HTML report, show PASS/FAIL status, and if it failed, attach the screenshot."

Therefore, **Branch 2 is the more complete version and should be the base for the Jenkins branch.**