package pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import util.WebDriverSingleton;

public class BasePage {
    WebDriver webDriver;

    public BasePage() {
        webDriver = WebDriverSingleton.getInstance();
    }

    public WebDriver getWebDriver() {
        return webDriver;
    }
    public void waitForPageToLoad(){
        WebDriverWait wait = new WebDriverWait(webDriver,10);
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }
}
