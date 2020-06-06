package com.wiki.qa.manager;

import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

public class AppManager {
    AppiumDriver driver;
    DesiredCapabilities capabilities;


    public void init() throws MalformedURLException {

        capabilities = new DesiredCapabilities();
        capabilities.setCapability("platformName", "Android");
        capabilities.setCapability("deviceName", "qa23");
        capabilities.setCapability("platformVersion", "8.0");
        capabilities.setCapability("automationName", "Appium");
        capabilities.setCapability("appPackage", "org.wikipedia");
        capabilities.setCapability("appActivity", ".main.MainActivity");

        capabilities.setCapability("app", "/home/adkogan/git/wiki/apk/org.wikipedia.apk");
        driver = new AndroidDriver(new URL("http://127.0.0.1:4723/wd/hub"), capabilities); driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

    }

    public void stop() {
        driver.quit();
    }

    public void findArticle(String text) {
        click(By.xpath("//*[@resource-id='org.wikipedia:id/search_container']"));
        typeTextForSearch(text);
        selectArticle();

    }

    public void selectArticle() {
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"),20);
    }

    private void typeTextForSearch(String text) {
        waitForElementAndType(By.xpath("//*[@resource-id='org.wikipedia:id/search_src_text']"), 3, text);

    }

    private void waitForElementAndType(By locator, int timeout, String text) {
        waitForElementAndClick(locator, timeout);
        driver.findElement(locator).clear();
        driver.findElement(locator).sendKeys(text);
    }

    public void click(By locator) {
        driver.findElement(locator).click();
    }

    public void waitForElementAndClick(By locator, int timeout) {
        new WebDriverWait(driver, timeout)
                .until(ExpectedConditions.presenceOfElementLocated(locator))
                .click();
    }


    public void addToFavorits() {
        initAddtoFavorites();
        if(isElementPresent(By.xpath("//*[resource-id='org.wikipedia:id/onboarding_button']"))) {
            clickGotItButton();
        }
        createList("MyList");
        clickOK();


    }

    public void clickOK() {
        click(By.xpath("//*[@resource-id='android:id/button1']"));
    }

    public void createList(String listName) {
        waitForElementAndType(By.xpath("//*[@resource-id='org.wikipedia:id/text_input']"), 20, listName);
    }

    public boolean isElementPresent(By locator) {
        return driver.findElements(locator).size()>0;
    }

    public void clickGotItButton() {

        click(By.xpath("//*[resource-id='org.wikipedia:id/onboarding_button']"));
    }

    public void initAddtoFavorites() {
        waitForElementAndClick(By.xpath("//*[@content-desc='Add this article to a reading list']"),20);
    }

    public void closeArticle() {
        click(By.xpath("//*[@content-desc='Navigate up']"));
    }

    public void openMyLists() {
        click(By.xpath("//*[@content-desc='My lists']"));
        waitForElementAndClick(By.xpath("//*[@resource-id='org.wikipedia:id/item_container']"),10);
       // waitForElementAndClick(By.xpath("//*[contains(text), 'MyList']/../../../.."), 10);
    }

    public boolean checkArticlePresent() {
        return isElementPresent(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"));
    }

    public String getArticleName() {
        return driver
                .findElement(By.xpath("//*[@resource-id='org.wikipedia:id/page_list_item_container']"))
                .getText();
    }
}
