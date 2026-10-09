# Complete Notes: Jenkins + GitHub + Maven + Selenium + Cucumber BDD

## 1. Objective

Integrate a Selenium automation framework with Jenkins and GitHub so that Jenkins can retrieve the source code, execute automated tests, and display the build status and test reports.

**Overall workflow:**

GitHub Repository → Jenkins Pipeline → Git Checkout → Maven Build → Cucumber/TestNG Execution → Reports → Build Status

## 2. Project Details

- **Automation Framework:** Selenium WebDriver with Java
- **BDD Framework:** Cucumber
- **Test Runner:** TestNG
- **Build Tool:** Maven
- **Version Control:** Git and GitHub
- **CI/CD Tool:** Jenkins
- **Reporting:** Cucumber HTML report and ExtentReports, depending on the framework configuration
- **Maven command:** `mvn clean test`
- **Jenkinsfile location:** Project root directory

Previously discussed GitHub repository:
`https://github.com/Sameer-Programmer/2026_BDD_Cucumberproject_OrangeHRM`

Use the actual branch selected for your current Jenkins job. Branch names are case-sensitive.

## 3. Initial Jenkins Setup

### Step 1: Start Jenkins
1. Open Jenkins in your browser.
2. Log in.
3. Click **New Item**.
4. Enter the job name.
5. Select **Pipeline**.
6. Click **OK**.

### Step 2: Connect Jenkins to GitHub

Under the job's Pipeline configuration, select:

- **Definition:** Pipeline script from SCM
- **SCM:** Git
- **Repository URL:** Your GitHub repository URL
- **Credentials:** Select credentials if the repository requires authentication.
- **Branches to build:** Enter the branch specification, for example `*/main` or `*/your-branch-name`.
- **Script Path:** `Jenkinsfile` or the exact filename used in your repository.

Click **Save** after completing the configuration.

### Step 3: Prepare the GitHub repository

Ensure the following are committed and pushed to the selected branch:

- `pom.xml`
- `Jenkinsfile`
- `src/test/java`
- `src/test/resources`
- Feature files, step definitions, hooks and runner classes
- Required configuration files and report configuration

The exact files depend on your framework structure.

**Important:** If the Jenkinsfile is stored in the repository root, use `Jenkinsfile` as the Script Path when that is its actual filename. The path and capitalization must match.

### Step 4: Configure the Jenkinsfile

The Jenkinsfile defines the pipeline stages and the commands Jenkins executes.

Typical stages:

1. **Checkout:** Retrieve the selected GitHub branch.
2. **Build:** Compile the Java project using Maven.
3. **Test:** Execute Selenium, Cucumber and TestNG tests.
4. **Report:** Publish or archive generated test reports.
5. **Post-build:** Record the final build status and perform any configured cleanup.

For a Windows Jenkins agent, Maven commands can be executed using `bat`, for example:

`bat 'mvn clean compile'`

`bat 'mvn test'`

Alternatively, use `bat 'mvn clean test'` to clean, compile and run the tests in one command.

The Jenkins agent must have compatible Java and Maven configurations. If Jenkins uses Maven Wrapper, the pipeline can use that instead.

## 4. What Happens After Clicking Build Now?

Once the Jenkins configuration is saved, open the Jenkins job and click **Build Now**.

### Execution sequence

1. Jenkins creates a new build number, such as `#1` or `#2`.
2. Jenkins checks out the configured GitHub branch.
3. Jenkins reads and executes the Jenkinsfile.
4. Maven cleans and compiles the project.
5. Maven executes the configured tests.
6. Cucumber and TestNG run the selected scenarios.
7. The framework generates reports if configured.
8. Jenkins records the final build result.

To investigate the execution, open the build number and select **Console Output**.

### Understanding build statuses

- **SUCCESS:** All required pipeline stages completed successfully.
- **FAILURE:** A required stage failed, such as compilation or test execution.
- **UNSTABLE:** The build completed with a configured unstable condition, often because of test failures.
- **ABORTED:** The build was stopped before completion.

The precise status depends on the Jenkinsfile and its post-build configuration.

## 5. What Happens When You Click Build Now Again?

Normally, clicking **Build Now** again starts a new build.

Jenkins checks out the configured branch at the time of that build. Therefore, if new code has been pushed to that branch, the next build can use the updated code.

If you only changed code locally and did not push it to GitHub, Jenkins will not automatically receive those local changes.

**Manual trigger:** Click Build Now.

**Automatic trigger:** Configure a GitHub webhook or another supported build trigger to start the pipeline when a qualifying change occurs.

## 6. Maven and Test Execution

Useful Maven commands:

| Command | Purpose |
|---|---|
| `mvn clean` | Removes previous Maven build output. |
| `mvn compile` | Compiles main Java source code. |
| `mvn test` | Executes configured tests through the Maven test lifecycle. |
| `mvn clean test` | Cleans, compiles and runs tests. |

For Cucumber with TestNG, ensure the Maven Surefire configuration, runner class and dependencies are correct so that the test runner is discovered and executed.

If the runner uses `tags = "@smoke or @sanity"`, the pipeline will execute scenarios matching either tag when that runner is selected.

## 7. Reports and Screenshots

After execution, check the report output configured in the framework.

Possible outputs include:

- Cucumber HTML report, such as `reports/Cucumber.html`
- ExtentReports HTML report, if configured
- TestNG reports under `target/surefire-reports`
- Failure screenshots, if the framework captures them

Jenkins does not automatically publish every HTML report just because the file was generated. Configure the appropriate Jenkins reporting or archiving steps.

For example, a pipeline can archive report files matching:

`reports/**/*`

The pattern must match the actual report directory and filenames. If reports are generated under `target`, configure the appropriate path there as well.

## 8. Common Problems and Solutions

### Problem 1: Remote branch not found
- Verify the branch exists on GitHub.
- Confirm the spelling and capitalization.
- Push the branch if it exists only locally.

Example:

`git push -u origin your-branch-name`

### Problem 2: Jenkinsfile not found
- Verify the filename.
- Confirm the file is committed and pushed.
- Check the Script Path in Jenkins.
- Confirm the file exists on the selected branch.

### Problem 3: Maven command not recognized
- Verify Maven is installed or configured in Jenkins.
- Check the Jenkins agent's PATH and tool configuration.
- Consider using Maven Wrapper if the project includes it.

### Problem 4: Java version mismatch
- Verify the Java version used by Jenkins.
- Check `pom.xml` compiler settings.
- Ensure the configured JDK supports the project's source and target versions.

### Problem 5: Tests are not executed
- Check the Maven Surefire configuration.
- Verify that the Cucumber TestNG runner is discoverable.
- Review the Console Output for skipped tests or test-discovery errors.

### Problem 6: Browser fails in Jenkins
- Confirm Chrome or the selected browser is available on the agent.
- Configure headless execution when appropriate.
- Check browser-driver compatibility and operating-system permissions.
- Review screenshots and browser logs if the framework captures them.

### Problem 7: Reports are missing
- Check whether the report was generated locally in the expected directory.
- Verify the Jenkinsfile report path.
- Confirm the pipeline archives or publishes the report.
- Ensure the reporting stage runs even when tests fail, if that is the intended behavior.

### Problem 8: Credentials or environment variables are missing
- Configure secrets using Jenkins credentials or appropriate environment-variable mechanisms.
- Do not commit passwords, tokens or private `.env` files to GitHub.
- Ensure the Jenkins agent can access the required configuration securely.

## 9. Reuse This Setup for Another Project

For a new automation project, repeat the same procedure:

1. Push the automation project to GitHub.
2. Ensure the correct branch exists remotely.
3. Add the Jenkinsfile to the repository.
4. Create a Jenkins Pipeline job.
5. Configure the GitHub repository URL and branch.
6. Set the correct Jenkinsfile Script Path.
7. Verify Java, Maven and required credentials.
8. Save the job and click **Build Now**.
9. Review Console Output.
10. Verify test results, reports and screenshots.

Only the project-specific settings should change, such as repository URL, branch name, Jenkinsfile path, Maven configuration and report paths.


## Final checklist

- [ ] GitHub repository and branch are correct.
- [ ] Jenkinsfile is committed to the selected branch.
- [ ] Jenkins Pipeline job is configured with Pipeline script from SCM.
- [ ] Java and Maven are configured.
- [ ] Build Now executes the pipeline.
- [ ] Console Output shows the execution steps.
- [ ] Cucumber/TestNG tests are actually discovered and executed.
- [ ] Reports and screenshots are generated and archived as configured.
- [ ] Credentials and secrets are stored securely.
