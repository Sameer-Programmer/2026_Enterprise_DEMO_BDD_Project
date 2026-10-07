package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.DriverFactory;

public class Hooks {

    @Before
    public void setUp() {
        System.out.println("===== Test Started =====");
        DriverFactory.initializeDriver();
    }

    @After
    public void tearDown() {
        System.out.println("===== Test Finished =====");
        DriverFactory.quitDriver();
    }
}