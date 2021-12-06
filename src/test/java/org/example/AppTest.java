package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

public class AppTest 
{
    private WebDriver driver;

    @BeforeAll
    static void enableDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("incognito");
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterEach
    void exitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Проверка открытия необходимой страницы")
    void openPageTest() {
        driver.get("https://novat.nsk.ru");
        driver.findElement(By.id("cookie-accept")).click();
        assertEquals("https://novat.nsk.ru/",  driver.getCurrentUrl());
    }

    @Test
    @DisplayName("Тестирование серфинга по элементам в меню")
    void surfingTest() {
        driver.get("https://novat.nsk.ru");
        driver.findElement(By.id("cookie-accept")).click();
        // Do: Перейти на страницу Театр выбрав соответсвующий пункт в боковом меню Assert:Открылась информация о театре
        driver.findElement(By.xpath("//a[contains(@href, '/theatre/')]")).click();
        assertEquals("https://novat.nsk.ru/theatre/theatre/",  driver.getCurrentUrl());
        // Do: Перейти на страницу Афиша выбрав соответсвующий пункт в боковом меню Assert:Открылась информация о ближайших спектаклях
        driver.findElement(By.xpath("//a[contains(@href, '/afisha/')]")).click();
        assertEquals("https://novat.nsk.ru/afisha/performances/",  driver.getCurrentUrl());
        // Do: Перейти на страницу Новости выбрав соответсвующий пункт в боковом меню Assert:Открылась страница с новостями
        driver.findElement(By.xpath("//a[contains(@href, '/news/')]")).click();
        assertEquals("https://novat.nsk.ru/news/",  driver.getCurrentUrl());
    }
}
