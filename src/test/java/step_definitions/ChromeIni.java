package step_definitions;

import org.openqa.selenium.WebDriver;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

public class ChromeIni {

    public static WebDriver driver;

    @Before
    public void openChrome() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setBrowserName(org.openqa.selenium.remote.BrowserType.CHROME);

        System.setProperty("webdriver.chrome.driver", "D://chromedriver//chromedriver.exe");

        driver = new ChromeDriver(capabilities);
    }
   /* @After
    public static void closeChrome() {
        driver.close();
    }*/
}
