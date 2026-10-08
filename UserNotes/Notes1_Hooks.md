### Cucumber Hooks

In BDD Cucumber, **Hooks** are special methods that execute automatically before or after scenarios or individual steps. They are mainly used for **setup and cleanup activities**.

Think of Hooks as the **setup and teardown mechanism of Cucumber tests**.

```java
@Before
public void setup() {
    // Launch browser
}

@After
public void tearDown() {
    // Close browser
}

@BeforeStep
public void beforeStep() {
    // Executes before each step
}

@AfterStep
public void afterStep() {
    // Executes after each step
}
```

### Key Points

- **`@Before`** → Executes before each scenario; commonly used for browser setup.
- **`@After`** → Executes after each scenario; commonly used for browser cleanup.
- **`@BeforeStep`** → Executes before every Gherkin step.
- **`@AfterStep`** → Executes after every Gherkin step; commonly used for screenshots/logging.
- **Hooks** → Used for technical setup, cleanup, logging, screenshots, and test initialization.