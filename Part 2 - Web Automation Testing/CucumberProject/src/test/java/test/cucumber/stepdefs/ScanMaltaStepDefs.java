package test.cucumber.stepdefs;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import pageObjects.ScanMaltaPageObject;

import static org.junit.Assert.assertTrue;

public class ScanMaltaStepDefs {

    WebDriver browser = null;
    public ScanMaltaPageObject sut;

    public void sleep(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (Exception e) {}
    }

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "D:/chromedriver.exe");
        browser = new ChromeDriver();
        sut = new ScanMaltaPageObject(browser);
        browser.manage().window().maximize();
    }

    @After
    public void teardown() {
        browser.quit();
    }

    //1
    @Given("I am a user on the website")
    public void i_am_a_user_on_the_website() {
        sut.getPage();    }

    @When("I log in using valid credentials {string} and {string}")
    public void i_log_in_using_valid_credentials(String username, String password) {
        sut.validLogin(username, password);
    }

    @Then("I should be logged in")
    public void i_should_be_logged_in() {
        sut.validateLogin();
    }

    //2
    @When("I log in using invalid credentials {string} and {string}")
    public void i_log_in_using_invalid_credentials_and(String username, String wrongPass) {
        sut.invalidLogin(username, wrongPass);
    }

    @Then("I should not be logged in")
    public void i_should_not_be_logged_in() {
        sut.validateInvalidLogin();
    }

    //3
    @Given("I am a logged in user on the website {string} and {string}")
    public void i_am_a_logged_in_user_on_the_website(String username, String password) {
        sut.getPage();
        sut.validLogin(username, password);
    }

    @When("I search for a product {string}")
    public void i_search_for_a_product(String product) {
        sut.search(product);
    }

    @When("I select the first product in the list")
    public void i_select_the_first_product_in_the_list() {
        sut.selectFirstProduct();
    }

    @Then("I should see the product details")
    public void i_should_see_the_product_details() {
        sut.productDetailsFound();
    }

    //4
//    @Given("I am a logged in user on the website  {string} and {string}")
//    public void i_am_a_logged_in_user_on_the_website(String username, String password) {
//        sut.getPage();
//        sut.validLogin(username, password);
//    }

    @Given("my shopping cart is empty")
    public void my_shopping_cart_is_empty() {
        sut.emptyCart();
    }

    @When("I view the details of a product {string}")
    public void i_view_the_details_of_a_product(String product) {
        sut.search(product);
        sut.selectFirstProduct();
    }

    @When("I choose to buy the product")
    public void i_choose_to_buy_the_product() {
        sut.addToCart();
    }

    @Then("my shopping cart should contain {int} item")
    public void my_shopping_cart_should_contain_item(int int1) {
        sut.getCartAmount();
        sut.cartHasOneItem(int1);
    }

    @When("I add {int} products to my shopping cart")
    public void i_add_num_products_products_to_my_shopping_cart(int int1) {
        sut.selectMultipleProductsAndAddToCart(int1);
    }

    @Then("my shopping cart should contain {int} items")
    public void my_shopping_cart_should_contain_num_products_items(int int1) {
        sut.getCartAmount();
        sut.cartHasMultipleItems(int1);
    }

    

    @Given("my shopping cart has {int} products")
    public void my_shopping_cart_has_products(Integer int1) {
        sut.emptyCart();
        sut.addTwoProducts();
        sut.getCartAmount();
        sut.cartHasMultipleItems(int1);
    }

    @When("I remove the first product in my cart")
    public void i_remove_the_first_product_in_my_cart() {
        sut.removeFirstProduct();
    }


}
