package Task2;

import nz.ac.waikato.modeljunit.GreedyTester;
import nz.ac.waikato.modeljunit.StopOnFailureListener;
import nz.ac.waikato.modeljunit.coverage.ActionCoverage;
import nz.ac.waikato.modeljunit.coverage.StateCoverage;
import nz.ac.waikato.modeljunit.coverage.TransitionPairCoverage;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.ScanMaltaPageObject;

import java.util.Random;

public class ScanModelTest {

    WebDriver browser;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        browser = new ChromeDriver();
        browser.manage().window().maximize();
    }

    @After
    public void teardown() { browser.quit(); }

    @Test
    public void ScanMaltaTestRunner() {
        GreedyTester tester = new GreedyTester(new ScanModel(browser));
        tester.setRandom(new Random());
        tester.buildGraph();
        tester.addListener(new StopOnFailureListener());
        tester.addListener("verbose");
        tester.addCoverageMetric(new TransitionPairCoverage());
        tester.addCoverageMetric(new StateCoverage());
        tester.addCoverageMetric(new ActionCoverage());
        tester.generate(500);
        tester.printCoverage();
    }
}
