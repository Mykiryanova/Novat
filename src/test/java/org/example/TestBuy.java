package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;


public class TestBuy {   private WebDriver driver;

    @BeforeAll
    static void enableDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDriver() {
        driver = new ChromeDriver();
        ChromeOptions chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("incognito");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
    }


    @AfterEach
    void exitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Test
    @DisplayName("Покупка билета")
    void buyTest() {
        // 1. Авторизация на сайте (описания шагов в тесте RegistrationTest. Так как тест по авторизации выполняется отдельно, здесь мы быстро по нему пробежимся поз объяснений)
        driver.get("https://novat.nsk.ru");
        driver.findElement(By.id("cookie-accept")).click();
        driver.findElement(By.xpath("//a[contains(text(),'Личный кабинет')]")).click();
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[1]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[1]/input")).sendKeys("masha2410@mail.ru");
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[2]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[2]/input")).sendKeys("zarplata");
        driver.findElement(By.xpath("//*[@id=\"forgot-form\"]/div/input[2]")).click();
        driver.findElement(By.xpath("//a[contains(@href, '/cabinet/profile/?backurl=%2F')]")).click();
        // 2. Do: Нажать на вкладку Афиша Assert: Открывается страница с предстоящими спектаклями
        driver.findElement(By.xpath("//a[contains(@href, '/afisha/')]")).click();
        // 3. Do: Найти спектакль и нажать на кнопку Купить билет Assert: Открывается схема зала
        WebElement perfomance =  driver.findElement(By.xpath("//a[contains(@href, '/buy_now/tickets/2451738/')]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",perfomance);
        perfomance.click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("/buy_now/tickets/2451738/"));
        // 3. Do: Выбрать место и нажать на кнопку Купить Assert: Открывается страница Корзина. В корзине отражен выбранный билет
        driver.findElement(By.name("Ряд 11 / Место 2")).click();
        driver.findElement(By.xpath("/html/body/div[2]/div[1]/div/div/div[2]/div[2]/div/form[2]/div/input[4]")).click();


        // Проверка наличия в корзине билета
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("/cart/"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("/html/body/div[2]/div/div/aside/div[7]/a[4]")));
        assertEquals("Ряд 11 / Место 2", driver.findElement(By.xpath("//div[contains(text(),'Ряд 11 / Место 2')]")).getText());

    }
}
