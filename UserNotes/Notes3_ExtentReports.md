# Extent Reports Implementation

## Overview

This project uses **Extent Reports 5.1.2** to generate a detailed HTML execution report for the Selenium + Cucumber + TestNG automation framework.

The Extent Report provides:

- Test scenario name
- PASS / FAIL status
- Failure information
- Failure screenshots
- Execution details
- Browser and framework information
- Timestamped screenshots
- HTML report for easy viewing

The report is generated at:

```text
reports/ExtentReport.html
```

Failed-test screenshots are stored at:

```text
reports/screenshots/
```

---

# 1. Technology Stack

The reporting implementation uses:

```text
Java 21
Selenium 4.35.0
Cucumber 7.23.0
TestNG 7.11.0
Extent Reports 5.1.2
Maven
```

---

# 2. Extent Reports Dependency

Extent Reports is added to `pom.xml` using a version property:

```xml
<extent.version>5.1.2</extent.version>
```

Dependency:

```xml
<dependency>
    <groupId>com.aventstack</groupId>
    <artifactId>extentreports</artifactId>
    <version>${extent.version}</version>
</dependency>
```

This provides the Extent Reports APIs required to create the HTML report.

---

# 3. Project Structure

The reporting-related files are organized as follows:

```text
2026_Enterprise_DEMO_BDD_Project/
│
├── reports/
│   ├── Cucumber.html
│   ├── ExtentReport.html
│   └── screenshots/
│       └── Add_a_new_employee_FAILED_YYYY-MM-DD_HH-mm-ss.png
│
├── src/
│   └── test/
│       └── java/
│           │
│           ├── hooks/
│           │   └── Hooks.java
│           │
│           ├── listeners/
│           │   └── ExtentTestNGListener.java
│           │
│           └── utils/
│               ├── ExtentReportManager.java
│               └── ScreenshotUtil.java
│
├── testng.xml
└── pom.xml
```

---

# 4. ExtentReportManager.java

## Purpose

`ExtentReportManager` is responsible for:

- Creating the Extent Reports instance
- Creating the HTML report
- Configuring the Spark reporter
- Creating individual `ExtentTest` objects
- Maintaining the current test using `ThreadLocal`
- Flushing the report
- Removing the current test from the thread

The report path is configured as:

```java
private static final Path REPORT_PATH =
        Path.of("reports", "ExtentReport.html");
```

Therefore, the generated report is:

```text
reports/ExtentReport.html
```

---

## ThreadLocal ExtentTest

The framework uses:

```java
private static final ThreadLocal<ExtentTest> TEST =
        new ThreadLocal<>();
```

### Why ThreadLocal?

`ThreadLocal` keeps the Extent test associated with the current execution thread.

This is useful when tests are executed in parallel.

For example:

```text
Thread 1 → Login Scenario
Thread 2 → Employee Scenario
Thread 3 → Candidate Scenario
```

Each execution thread can maintain its own `ExtentTest`.

---

## Creating an Extent Test

The framework provides:

```java
public static void startTest(String testName) {
    TEST.set(REPORTS.createTest(testName));
}
```

The Cucumber scenario name is passed to this method.

For example:

```gherkin
Scenario: Add a new employee
```

becomes:

```text
Add a new employee
```

in the Extent Report.

---

# 5. ExtentSparkReporter Configuration

The framework uses:

```java
ExtentSparkReporter sparkReporter =
        new ExtentSparkReporter(REPORT_PATH.toString());
```

The report is configured with:

```java
sparkReporter.config().setDocumentTitle(
        "Enterprise BDD Automation Report"
);

sparkReporter.config().setReportName(
        "Automation Execution Report"
);
```

System information is also added:

```java
reports.setSystemInfo(
        "Project",
        "EclipseEHR-BDD-Automation"
);

reports.setSystemInfo(
        "Framework",
        "Selenium + Cucumber + TestNG"
);

reports.setSystemInfo(
        "Browser",
        "Chrome"
);
```

---

# 6. ExtentTestNGListener.java

The project uses a custom TestNG listener:

```java
public class ExtentTestNGListener implements ITestListener
```

The listener is registered in the Cucumber Test Runner:

```java
@Listeners(ExtentTestNGListener.class)
```

The listener currently uses the `onFinish()` method:

```java
@Override
public void onFinish(ITestContext context) {

    ExtentReportManager.flush();
}
```

## Why is the listener only flushing the report?

This project uses:

```text
Cucumber + TestNG
```

The actual test is a **Cucumber Scenario**, not a normal TestNG test method.

Therefore, scenario-level information such as:

- Scenario name
- Scenario status
- Scenario failure
- Screenshot

is handled by the Cucumber `Hooks.java`.

The TestNG listener is responsible for the final report lifecycle and calls:

```java
ExtentReportManager.flush();
```

when TestNG execution finishes.

---

# 7. TestRunner.java

The TestNG listener is registered using:

```java
@Listeners(ExtentTestNGListener.class)
```

Example:

```java
package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import listeners.ExtentTestNGListener;
import org.testng.annotations.Listeners;

@Listeners(ExtentTestNGListener.class)
@CucumberOptions(
        features = "src/test/resources/features",
        glue = {"stepdefinations", "hooks"},
        tags = "@smoke",
        plugin = {
                "pretty",
                "html:reports/Cucumber.html"
        },
        monochrome = true
)
public class TestRunner
        extends AbstractTestNGCucumberTests {
}
```

This allows TestNG to use the custom Extent listener.

---

# 8. Hooks.java Integration

The Cucumber Hooks are responsible for scenario-level Extent reporting.

## Before Scenario

The `@Before` hook initializes the browser:

```java
DriverFactory.initializeDriver();
```

Then the Cucumber scenario name is used to create the Extent test:

```java
ExtentReportManager.startTest(
        scenario.getName()
);
```

For example:

```gherkin
Scenario: Add a new employee
```

creates:

```text
Extent Test:
Add a new employee
```

---

# 9. Handling Passed Scenarios

After the scenario finishes, the framework checks:

```java
if (scenario.isFailed())
```

If this returns `false`, the scenario passed.

The report is updated using:

```java
ExtentReportManager.getTest()
        .pass("Scenario Passed");
```

The Extent Report therefore shows:

```text
Add a new employee
        |
        └── PASS
```

---

# 10. Handling Failed Scenarios

If:

```java
scenario.isFailed()
```

returns `true`, the framework records:

```java
ExtentReportManager.getTest()
        .fail("Scenario Failed");
```

The failed scenario is therefore shown in the Extent Report as:

```text
Add a new employee
        |
        └── FAIL
```

---

# 11. Screenshot Capture

Screenshots are handled separately by:

```text
ScreenshotUtil.java
```

The utility uses Selenium's:

```java
TakesScreenshot
```

interface.

Example:

```java
TakesScreenshot takesScreenshot =
        (TakesScreenshot) DriverFactory.getDriver();

byte[] screenshotBytes =
        takesScreenshot.getScreenshotAs(OutputType.BYTES);
```

The screenshot is saved under:

```text
reports/screenshots/
```

---

# 12. Timestamped Screenshots

The screenshot filename contains the current date and time.

The format is:

```text
yyyy-MM-dd_HH-mm-ss
```

Example:

```text
Add_a_new_employee_FAILED_2026-10-08_11-30-15.png
```

This prevents screenshots from being overwritten when the same test fails multiple times.

Example:

```text
reports/
└── screenshots/
    ├── Add_a_new_employee_FAILED_2026-10-08_11-30-15.png
    ├── Add_a_new_employee_FAILED_2026-10-08_11-45-21.png
    └── Add_a_new_employee_FAILED_2026-10-08_12-02-08.png
```

---

# 13. Attaching Screenshot to Extent Report

After capturing the screenshot, the path is converted into a relative path.

The report is located at:

```text
reports/ExtentReport.html
```

and screenshots are located at:

```text
reports/screenshots/
```

Therefore, the correct path from the HTML report is:

```text
screenshots/<filename>.png
```

The framework attaches the screenshot using:

```java
ExtentReportManager.getTest()
        .addScreenCaptureFromPath(
                relativeScreenshotPath
        );
```

This allows the screenshot to be displayed correctly inside the Extent HTML report.

---

# 14. Why a Relative Path Is Used

Initially, the screenshot was successfully created in:

```text
reports/screenshots/
```

but the image was not displayed correctly inside Extent Report.

The issue was the path being passed to Extent.

The report is already inside:

```text
reports/
```

Therefore:

### Incorrect

```text
reports/screenshots/example.png
```

### Correct

```text
screenshots/example.png
```

The relative path allows the generated HTML report to correctly locate the screenshot.

---

# 15. Removing the ThreadLocal Test

After the scenario finishes:

```java
ExtentReportManager.removeTest();
```

is called.

This clears the `ExtentTest` associated with the current thread.

This is especially useful when the framework is later configured for parallel execution.

---

# 16. Complete Execution Flow

The complete reporting flow is:

```text
Maven
  |
  v
TestNG
  |
  v
TestRunner
  |
  +--> ExtentTestNGListener
  |
  v
Cucumber
  |
  v
@Before Hook
  |
  +--> Initialize WebDriver
  |
  +--> Create ExtentTest
  |
  v
Execute Scenario
  |
  +-----------------------+
  |                       |
  v                       v
PASS                    FAIL
  |                       |
  |                       +--> Capture Screenshot
  |                       |
  |                       +--> Save Screenshot
  |                       |
  |                       +--> Attach Screenshot
  |                       |
  +-----------+-----------+
              |
              v
          @After Hook
              |
              v
        Quit WebDriver
              |
              v
       TestNG onFinish()
              |
              v
     ExtentReports.flush()
              |
              v
   reports/ExtentReport.html
```

---

# 17. Generated Files

After execution, the reports directory looks like:

```text
reports/
│
├── Cucumber.html
│
├── ExtentReport.html
│
└── screenshots/
    ├── Add_a_new_employee_FAILED_2026-10-08_11-30-15.png
    └── Login_FAILED_2026-10-08_11-35-20.png
```

---

# 18. How to Run

Run the complete test suite using:

```bash
mvn clean test
```

The framework will:

1. Compile the project.
2. Start TestNG.
3. Start Cucumber.
4. Execute scenarios matching the configured tag.
5. Create Extent tests.
6. Mark scenarios PASS or FAIL.
7. Capture screenshots for failed scenarios.
8. Attach screenshots to Extent.
9. Flush the report.
10. Generate `ExtentReport.html`.

---

# 19. Current Framework Reporting

The project currently generates two reports:

### Cucumber Report

```text
reports/Cucumber.html
```

This is the standard Cucumber HTML report.

### Extent Report

```text
reports/ExtentReport.html
```

This provides a richer execution report with:

```text
Scenario
Status
Failure
Screenshot
System Information
```

---

# 20.  Explanation

If asked:

**"How we implementd Extent Reports in your framework?"**

explaination:

> I integrated Extent Reports 5.1.2 into my Selenium BDD framework using Cucumber with TestNG. I created a dedicated `ExtentReportManager` to initialize and configure the Spark HTML reporter and maintain `ExtentTest` instances using `ThreadLocal`. I implemented a custom TestNG `ITestListener` mainly to flush the report when the TestNG execution finishes. Since the actual tests are Cucumber scenarios, I use Cucumber Hooks to create the Extent test using `Scenario.getName()`, determine PASS or FAIL using `scenario.isFailed()`, and capture screenshots for failed scenarios. The screenshots are stored with timestamps and attached to the Extent report using a relative path. Finally, the report is generated under the `reports` directory.

---

# 21. Key Classes and Responsibilities

| Class | Responsibility |
|---|---|
| `ExtentReportManager` | Creates and manages Extent Reports |
| `ExtentTestNGListener` | Flushes Extent report when TestNG finishes |
| `Hooks` | Handles Cucumber scenario-level reporting |
| `ScreenshotUtil` | Captures and stores screenshots |
| `TestRunner` | Starts Cucumber/TestNG and registers the listener |
| `testng.xml` | Defines the TestNG suite and TestRunner |

---

# 22. Final Architecture

```text
                 Selenium
                    |
                 Cucumber
                    |
                 TestNG
                    |
              TestRunner.java
                    |
        @Listeners(ExtentTestNGListener)
                    |
                    v
        ExtentTestNGListener.java
                    |
              onFinish()
                    |
                    v
        ExtentReportManager.java
                    |
                    v
           ExtentReport.html
                    ^
                    |
               Hooks.java
                    |
          +---------+---------+
          |                   |
        PASS                 FAIL
          |                   |
          |             ScreenshotUtil
          |                   |
          |                   v
          |          reports/screenshots/
          |                   |
          |                   v
          +--------> Extent Report
```

## Result

The framework now provides **BDD execution reporting + Extent reporting + automatic failure screenshots** while keeping the reporting responsibilities separated into reusable classes.