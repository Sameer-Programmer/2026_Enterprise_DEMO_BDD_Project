package utils;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public final class ExtentReportManager {

    private static final Path REPORT_PATH =
            Path.of("reports", "ExtentReport.html");

    private static final ThreadLocal<ExtentTest> TEST =
            new ThreadLocal<>();

    private static final ExtentReports REPORTS =
            createReports();

    private ExtentReportManager() {
    }

    public static void startTest(String testName) {
        TEST.set(REPORTS.createTest(testName));
    }

    public static ExtentTest getTest() {
        ExtentTest test = TEST.get();

        if (test == null) {
            throw new IllegalStateException(
                    "No Extent test has been started for this thread"
            );
        }

        return test;
    }

    public static void removeTest() {
        TEST.remove();
    }

    public static void flush() {
        REPORTS.flush();
    }

    private static ExtentReports createReports() {

        try {
            Files.createDirectories(REPORT_PATH.getParent());

        } catch (IOException exception) {

            throw new IllegalStateException(
                    "Unable to create Extent report directory",
                    exception
            );
        }

        ExtentSparkReporter sparkReporter =
                new ExtentSparkReporter(REPORT_PATH.toString());

        sparkReporter.config().setDocumentTitle(
                "Enterprise BDD Automation Report"
        );

        sparkReporter.config().setReportName(
                "Automation Execution Report"
        );

        ExtentReports reports = new ExtentReports();

        reports.attachReporter(sparkReporter);

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

        return reports;
    }
}