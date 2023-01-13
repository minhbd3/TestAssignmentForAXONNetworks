package com.weather.pages;

import com.weather.ults.Common;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DetailsPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(xpath = "//div[contains(@class,'DaypartDetails')]//div[@data-testid='DailyContent'][1]/h3/span")
    List<WebElement> listWeatherDate;

    @FindBy(xpath = "//div[contains(@class,'DaypartDetails')]//div[@data-testid='DailyContent'][1]//span[@data-testid='TemperatureValue']")
    List<WebElement> lisDayTemperature;

    @FindBy(xpath = "//div[contains(@class,'DaypartDetails')]//div[@data-testid='DailyContent'][2]//span[@data-testid='TemperatureValue']")
    List<WebElement> listNightTemperature;

    @FindBy(xpath = "//div[contains(@class,'DaypartDetails--DetailsTable')][1]/ul[@data-testid='DetailsTable']/li[@data-testid='HumiditySection']//span[@data-testid='PercentageValue']")
    List<WebElement> listDayHumidity;

    @FindBy(xpath = "//div[contains(@class,'DaypartDetails--DetailsTable')][2]/ul[@data-testid='DetailsTable']/li[@data-testid='HumiditySection']//span[@data-testid='PercentageValue']")
    List<WebElement> listNightHumidity;

    @FindBy(xpath = "//section[@data-testid='DailyForecast']//div[contains(@class,'DetailSummaryContent')]/*[@data-testid='Icon']")
    List<WebElement> listWeatherExpandBtn;

    public enum TimeFilterOptions {
        TODAY("Today"),
        HOURLY("Hourly"),
        TEN_DAYS("10 Day"),
        WEEKEND("Weekend"),
        MONTHLY("Monthly"),
        RADAR("Radar");

        private final String filterOptions;

        private TimeFilterOptions(String option) {
            filterOptions = option;
        }

        public String toString() {
            return this.filterOptions;
        }
    }

    public DetailsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, 30);
        PageFactory.initElements(driver, this);
    }

    public DetailsPage selectTimeFilterOptions(TimeFilterOptions filterOptions) {
        String filterModule = String.format("//nav[contains(@class,'Localsuite')]//following-sibling::a[.='%s']/span", filterOptions.toString());
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath(filterModule))).click();

        return this;
    }

    public DetailsPage getWeatherInfo() {
        Common common = new Common();

        List<String> date = new ArrayList<>();
        List<String> dayTemp = new ArrayList<>();
        List<String> nightTemp = new ArrayList<>();
        List<String> dayHumidity = new ArrayList<>();
        List<String> nightHumidity = new ArrayList<>();

        /** Retrieve 10 days weather **/
        int RETRIEVE_CARDS = 10;

        for (int i = 0; i < RETRIEVE_CARDS; i++) {
            wait.until(ExpectedConditions.elementToBeClickable(listWeatherExpandBtn.get(i)));
            /* If the card wasn't expanded => expand => get data
               else the card was expanded => get data
            * */

            if (listWeatherExpandBtn.get(i).getAttribute("name").contains("down")) {

                ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", listWeatherExpandBtn.get(i));
                common.sleepInSeconds(1);
                //Sometimes the element is removed from the DOM structure
                try {
                    listWeatherExpandBtn.get(i).click();
                } catch (Exception e) {
                    listWeatherExpandBtn.get(i).click();
                }
                common.sleepInSeconds(1);
            }

            date.add(listWeatherDate.get(i).getAttribute("textContent"));
            dayTemp.add(lisDayTemperature.get(i).getAttribute("textContent"));
            nightTemp.add(listNightTemperature.get(i).getAttribute("textContent"));
            dayHumidity.add(listDayHumidity.get(i).getAttribute("textContent"));
            nightHumidity.add(listNightHumidity.get(i).getAttribute("textContent"));
        }

        for (int i = 0; i < RETRIEVE_CARDS; i++) {
            System.out.println("-- Date: " + date.get(i));
            System.out.println("Day temperature: " + dayTemp.get(i));
            System.out.println("Night temperature: " + nightTemp.get(i));
            System.out.println("Day humidity: " + dayHumidity.get(i));
            System.out.println("Night humidity: " + nightHumidity.get(i));
        }

        return this;
    }
}
