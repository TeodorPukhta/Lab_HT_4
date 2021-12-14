package BasketTests;

import model.Product;
import model.Products;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.*;
import pages.HomePage;
import pages.SearchResultPage;
import util.PropertiesReader;
import util.WebDriverSingleton;
import util.XMLReader;

import static io.github.bonigarcia.wdm.WebDriverManager.chromedriver;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class BasketTest {

    @DataProvider(name = "data", parallel = true)
    public static Object[][] getData() {
        XMLReader xmlReader = new XMLReader();
        Products products = new Products();
        products.setProducts(xmlReader.getXmlData());
        Object[][] productData = new Object[products.getProducts().size()][2];
        for (int i = 0; i < products.getProducts().size(); i++) {
            Object[] column = productData[i];
            column[0] = products.getProducts().get(i).getId();
            column[1] = products.getProducts().get(i);
        }
        return productData;
    }

    @BeforeMethod
    public void testSetUp() {
        PropertiesReader propertiesReader = new PropertiesReader();
        WebDriverSingleton.getInstance().manage().window().maximize();
        WebDriverSingleton.getInstance().get(propertiesReader.getURL());
    }

    @BeforeTest
    public void profileSetUp() {
        chromedriver().setup();

    }

    @Test(dataProvider = "data")
    public void checkThatProductIsInBucketAndBlowPrice(String dataId, Product product) {
        HomePage homePage = new HomePage();
        WebDriverWait wait = new WebDriverWait(homePage.getWebDriver(), 10);
        wait.until(ExpectedConditions.elementToBeClickable(homePage.getSearchField()));
        homePage.searchByKeyWord(product.getName());
        SearchResultPage searchResultPage = new SearchResultPage();
        searchResultPage.waitForPageToLoad();
        searchResultPage.scrollToBrandFilterInput();
        searchResultPage.inputBrandNameInSearchFilterField(product.getBrand());
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath(searchResultPage.getXpathOfListOfBrandCheckboxes()), 1));
        searchResultPage.clickOnBrandFilterCheckBox();
        try {
            searchResultPage.clickOnAddProductToBasketButton();
        } catch (Exception e) {
            wait.until(ExpectedConditions.elementToBeClickable(searchResultPage.getAddProductToBasketButton()));
            searchResultPage.clickOnAddProductToBasketButton();
        }
        searchResultPage.clickOnBasketIcon();
        wait.until(ExpectedConditions.visibilityOf(searchResultPage.getSumPrice()));
        assertTrue(Integer.parseInt(product.getPrice()) < Integer.parseInt(searchResultPage.getSumPrice().getText()));
        assertEquals(searchResultPage.getListOfProductsInBasket().size(), 1);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        WebDriverSingleton.close();
    }
}
