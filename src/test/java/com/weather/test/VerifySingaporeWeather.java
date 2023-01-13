package com.weather.test;

import com.weather.base.BaseTest;
import com.weather.pages.DetailsPage;
import com.weather.pages.HomePage;
import org.testng.annotations.Test;

public class VerifySingaporeWeather extends BaseTest {
    final String TEST_WEATHER_DATA = "Singapore, Singapore";

    @Test
    public void retrieveSingaporeWeatherDuring10Days() {
        HomePage homePage = new HomePage(driver);
        DetailsPage detailsPage = new DetailsPage(driver);

        homePage.navigateToHomePage().inputSearchInfo(TEST_WEATHER_DATA);
        detailsPage.selectTimeFilterOptions(DetailsPage.TimeFilterOptions.TEN_DAYS).getWeatherInfo();
    }
}
