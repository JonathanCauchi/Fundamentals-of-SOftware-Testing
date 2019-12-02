package Task2;

import enums.ScanMaltaStates;
import nz.ac.waikato.modeljunit.Action;
import nz.ac.waikato.modeljunit.FsmModel;
import org.openqa.selenium.WebDriver;

import static org.junit.Assert.assertEquals;

public class ScanModel implements FsmModel {

    WebDriver browser;

    private ScanMaltaSystem sut = new ScanMaltaSystem(browser);

    private ScanMaltaStates modelState = ScanMaltaStates.LOGGED_OUT;

    private boolean loggedIn, loggedOut, searching, addingToCart,
            removingFromCart, checkingOut;

    public ScanModel(WebDriver browser) {
        this.browser = browser;
        browser.get("https://www.scanmalta.com/newstore/customer/account/login/");
    }

    public ScanMaltaStates getState() {
        return modelState;
    }

    public void reset(final boolean b) {
        modelState = ScanMaltaStates.LOGGED_OUT;
        loggedOut = true;
        loggedIn = false;
        searching = false;
        addingToCart = false;
        removingFromCart = false;
        checkingOut = false;

        if(b) {
            sut = new ScanMaltaSystem(browser);
        }
    }

    public boolean loggingInGuard(){
        return getState().equals(ScanMaltaStates.LOGGED_OUT);
    }

    public @Action
    void loggingIn(){
        sut.isLoggedIn();

        loggedIn = true;
        modelState = ScanMaltaStates.LOGGED_IN;

        assertEquals("", loggedIn, sut.isLoggedIn());
    }

    public boolean loggingOutGuard(){
        return getState().equals(ScanMaltaStates.LOGGED_IN);
    }

    public @Action void loggingOut(){
        sut.isLoggedOut();

        loggedIn = false;
        modelState = ScanMaltaStates.LOGGED_OUT;

        assertEquals("", !loggedIn ,!sut.isLoggedIn());
    }

    public boolean searchingGuard(){
        return getState().equals(ScanMaltaStates.LOGGED_IN);
    }
    public @Action void searchingProduct(){
        sut.isSearching();

        searching = true;
        modelState = ScanMaltaStates.SEARCHING;

        assertEquals("", searching && loggedIn ,sut.isSearching());
    }

    public boolean addingItemGuard(){
        return getState().equals(ScanMaltaStates.SEARCHING);
    }

    public @Action void addingItemToCart(){
        sut.addToCart();
        addingToCart = true;

        modelState = ScanMaltaStates.ADDING_TO_CART;

        assertEquals("", searching ,sut.isSearching());
        assertEquals("", loggedIn, sut.isLoggedIn());
        assertEquals("", addingToCart ,sut.isAddingToCart());
        assertEquals("", checkingOut ,sut.isCheckingOut());
    }




}

