package util;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class WebDriverSingleton {
    private static ThreadLocal<WebDriver> webDriverThreadLocal = new ThreadLocal<>();

    public static WebDriver getInstance(){
        if(webDriverThreadLocal.get() != null)
            return webDriverThreadLocal.get();
        WebDriver instance;
        instance = new ChromeDriver(){
            {
                manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
            }
        };
        webDriverThreadLocal.set(instance);
        return webDriverThreadLocal.get();
    }

    public static void close(){
        try{
            if(webDriverThreadLocal != null){
                webDriverThreadLocal.get().quit();
            }
        } catch (Exception e){
            System.out.println("Error: can not close WebDriver");
        } finally {
            webDriverThreadLocal.remove();
        }
    }
}
