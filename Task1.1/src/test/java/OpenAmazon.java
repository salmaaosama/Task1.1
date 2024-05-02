import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class OpenAmazon {
    WebDriver driver;

    @BeforeTest
    public void openBrowser() throws InterruptedException {
        driver = new ChromeDriver();
        //Set Browser resolution
        driver.manage().window().setSize(new Dimension(1024, 768));
        //Open amazon site
        driver.navigate().to("https://www.amazon.com/");
        Thread.sleep(3000);
    }

    @Test
    public void ValidData() throws InterruptedException {
        //Open amazon site
        driver.navigate().to("https://www.amazon.com/");
        //type "car accessories";
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("car accessories");
        //press search button
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        //select first item
        driver.findElement(By.cssSelector(".a-size-base-plus.a-color-base.a-text-normal")).click();
        //Add item to the cart
        driver.findElement(By.id("add-to-cart-button")).click();
        Thread.sleep(2000);
        //Go to the cart and check that item is added successfully
        driver.findElement(By.id("NATC_SMART_WAGON_CONF_MSG_SUCCESS")).getText();
        String expectedText = "Added to Cart";
        String actualText = driver.findElement(By.cssSelector("h1[class=\"a-size-medium-plus a-color-base sw-atc-text a-text-bold\"]")).getText();
        Assert.assertEquals(actualText,expectedText);
    }
    @Test
    public void invalid_addToCartWithoutItem() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        // Go to the cart directly without adding any item
        driver.findElement(By.id("nav-cart-count")).click();
        Thread.sleep(2000);
        // Check if the cart is empty
        String expectedText = "Your Amazon Cart is empty";
        String actualText = driver.findElement(By.cssSelector("div[class='a-row sc-your-amazon-cart-is-empty']")).getText();
        Assert.assertEquals(actualText, expectedText);
    }

    @Test
    public void selectFirstItemThenGoToCartWithoutAdding() throws InterruptedException {
        // Open amazon site
        driver.navigate().to("https://www.amazon.com/");
        // Type "car accessories"
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("car accessories");
        // Press search button
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        // Select first item
        driver.findElement(By.cssSelector(".a-size-base-plus.a-color-base.a-text-normal")).click();
        // Go back to search results
        driver.navigate().back();
        // Go to the cart
        driver.findElement(By.id("nav-cart")).click();
        // Verify that the cart is empty
        Assert.assertTrue(driver.findElement(By.cssSelector(".a-row.sc-your-amazon-cart-is-empty")).isDisplayed());
    }


    @Test
    public void invalid_enterWrongData() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("accessories");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.cssSelector(".a-size-base-plus.a-color-base.a-text-normal")).click();
        driver.findElement(By.id("add-to-cart-button")).click();
        Thread.sleep(2000);
        //Go to the cart and check that item is added successfully
        driver.findElement(By.id("NATC_SMART_WAGON_CONF_MSG_SUCCESS")).getText();
        String expectedText = "Added to Cart";
        String actualText = driver.findElement(By.cssSelector("h1[class=\"a-size-medium-plus a-color-base sw-atc-text a-text-bold\"]")).getText();
        Assert.assertEquals(actualText,expectedText);
    }
    @Test
    public void invalid_searchNoResults() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("CA");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.cssSelector("span[class='a-size-base a-color-base']")).getText().contains("No results for"));
    }
    @Test
    public void Invalid_selectSecondItem() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("car accessories");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        driver.findElements(By.cssSelector(".a-size-base-plus.a-color-base.a-text-normal")).get(1).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.findElement(By.id("add-to-cart-button")).isDisplayed());
    }
    @Test
    public void verifySearchResultsPageTitle() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("car accessories");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        Assert.assertTrue(driver.getTitle().contains("car accessories"));
    }
    @Test
    public void VerifySearchResultsDisplayed() {
        // Verify that search results for "car accessories" are displayed
        Assert.assertTrue(driver.findElement(By.cssSelector("div[data-component-type='s-search-result']")).isDisplayed());
    }
    @Test
    public void invalid_addToCartWithoutSelection() throws InterruptedException {
        driver.navigate().to("https://www.amazon.com/");
        driver.findElement(By.id("twotabsearchtextbox")).sendKeys("car accessories");
        driver.findElement(By.id("nav-search-submit-button")).click();
        Thread.sleep(2000);
        driver.findElement(By.id("add-to-cart-button")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("div[class='a-alert-content']")).getText().contains("Select"));
    }
    @Test
    public void VerifyCartTotalPriceAfterIncrement() {
        // Increase the quantity of the item in the cart
        driver.findElement(By.cssSelector("span[class='a-button a-button-dropdown a-button-small']")).click();
        driver.findElement(By.cssSelector("a[class='a-dropdown-link']")).click();
        // Check the total price in the cart
        String totalPrice = driver.findElement(By.cssSelector("span[class='a-size-medium a-color-base sc-price sc-white-space-nowrap sc-product-price sc-price-sign a-text-bold']")).getText();
        Assert.assertEquals(totalPrice, "New total price after quantity increment");
    }
}