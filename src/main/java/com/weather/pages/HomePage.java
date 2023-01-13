package com.weather.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(id = "LocationSearch_input")
    WebElement txbSearch;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }

    public HomePage navigateToHomePage() {
        driver.get("https://weather.com/");

        return this;
    }

    public HomePage inputSearchInfo(String info) {
        wait.until(ExpectedConditions.elementToBeClickable(txbSearch));
        txbSearch.sendKeys(info);

        String optSearchResult = String.format("//button[.='" + info + "']", "%s");
        wait.until(ExpectedConditions
                .visibilityOfElementLocated(By.xpath(optSearchResult))).click();

        return this;
    }
}
