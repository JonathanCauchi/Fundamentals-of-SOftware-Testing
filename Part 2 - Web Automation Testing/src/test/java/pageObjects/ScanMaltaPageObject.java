package pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScanMaltaPageObject {

    WebDriver browser = null;

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {}
    }

    public ScanMaltaPageObject(WebDriver browser) {
        this.browser = browser;
    }

    public void getPage() {
        browser.get("https://www.scanmalta.com/newstore/customer/account/login/");
    }

    public void validLogin(String username, String password) {
        browser.findElement(By.name("login[username]")).sendKeys(username);
        browser.findElement(By.name("login[password]")).sendKeys(password);
        browser.findElement(By.name("send")).submit();
        sleep(2);
    }

    public void validateLogin() {
        assertTrue(browser.findElement(By.className("hello")).getText().contains("Hello Jonathan!"));
    }

    public void invalidLogin(String username, String wrongPass) {
        browser.findElement(By.name("login[username]")).sendKeys(username);
        browser.findElement(By.name("login[password]")).sendKeys(wrongPass);
        browser.findElement(By.name("send")).submit();
        sleep(3);
    }

    public void validateInvalidLogin() {
        assertTrue(browser.findElement(By.className("error-msg")).getText().contains("Invalid login or password."));
    }

    public void search(String product) {
        browser.findElement(By.id("search")).sendKeys(product);
        browser.findElement(By.className("icon-search")).submit();
        sleep(2);
    }

    public void selectFirstProduct() {
        List<WebElement> productsList = browser.findElements(By.className("item-images"));
        WebElement firstProduct = productsList.get(0);
        firstProduct.click();
        sleep(2);
    }

    public void productDetailsFound() {
        assertTrue(browser.findElement(By.className("product-description")).getText().contains("Product Description"));
    }

    public void goToCart() {
        browser.get("https://www.scanmalta.com/newstore/checkout/cart/");
        sleep(3);
    }

    public void emptyCart() {
        if(getCartAmount() != 0) {
            browser.findElement(By.id("empty_cart_button")).click();
        }
        sleep(2);
    }

    public void addToCart() {
        browser.findElement(By.id("product-addtocart-button")).submit();
        sleep(7); //due to popup that follows
    }

    public int getCartAmount() {
        String amount = browser.findElement(By.xpath("//span[contains(text(), 'items')]")).getText();
        return Integer.parseInt(amount.split(" ")[0]);
    }

    public void cartHasOneItem(int int1) {
        int items = getCartAmount();
        assertEquals(items, int1);
    }

    public void selectMultipleProductsAndAddToCart(int int1) {

        int i = 0;

        do {
            search("ssd");
            List<WebElement> productsList = browser.findElements(By.className("item-images"));
            System.out.println("Loop No. " + i);
            WebElement product = productsList.get(i);
            sleep(2);
            product.click();
            sleep(2);
            addToCart();
            i++;
        }while(i != int1);
        
    }

    public void cartHasMultipleItems(int int1) {
        int items = getCartAmount();
        System.out.println("Number in cart " + items +" should contain " + int1); //test to see amount in cart and what it should have
        assertEquals(items, int1);
    }

}
