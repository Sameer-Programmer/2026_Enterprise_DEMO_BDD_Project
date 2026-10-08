package listeners;

import org.testng.ITestContext;
import org.testng.ITestListener;
import utils.ExtentReportManager;

public class ExtentTestNGListener implements ITestListener {

    @Override
    public void onFinish(ITestContext context) {

        System.out.println("===== Flushing Extent Report =====");

        ExtentReportManager.flush();

        System.out.println("===== Extent Report Generated =====");
    }
}