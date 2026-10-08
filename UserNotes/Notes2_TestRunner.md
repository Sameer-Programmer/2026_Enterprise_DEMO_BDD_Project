### Cucumber Test Runner

In BDD Cucumber, the **Runner class** tells Cucumber:
- What feature files to execute
- Where the Step Definitions and Hooks are located
- Which tags to execute
- Which plugins/reports to generate

Think of it as the **entry point for Cucumber test execution**.

```java
@CucumberOptions(
    features = "src/test/resources/features",
    glue = "stepdefinitions",
    tags = "@smoke",
    plugin = {"pretty", "html:target/cucumber-report.html"},
    monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
```

### Key Points

- **Test Runner Class** → Entry point for executing Cucumber tests and integrating Cucumber with TestNG.
- **`@CucumberOptions`** → Configures Cucumber test execution.
- **`features`** → Specifies the location of Feature files.
- **`glue`** → Specifies the location of Step Definitions and Hooks.
- **`tags`** → Selects the scenarios to execute.
- **`plugin`** → Generates reports and console output.
- **`monochrome`** → Makes console output more readable.
- **`AbstractTestNGCucumberTests`** → Integrates Cucumber with TestNG.