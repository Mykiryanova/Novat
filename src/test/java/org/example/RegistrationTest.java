package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.*;
public class RegistrationTest {

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
        driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
    }

    @AfterEach
    void exitDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    @DisplayName("Тестирование регистрации пользователя")
    void registrationTest() {
        driver.get("https://novat.nsk.ru");
        driver.findElement(By.id("cookie-accept")).click();
        // Do: Перейти в личный кабинет, нажав Личный кабинет в боковом меню Assert: Открывается окно авторизации
        driver.findElement(By.xpath("//a[contains(text(),'Личный кабинет')]")).click();
        //    assertEquals("https://novat.nsk.ru/theatre/theatre/", driver.getCurrentUrl());
        //Do: Авторизоваться на сайте под login/password: masha2410@mail.ru/zarplata Assert: Успешная авторизация
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[1]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[1]/input")).sendKeys("masha2410@mail.ru");
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[2]/input")).click();
        driver.findElement(By.xpath("//*[@id=\"auth-form\"]/div[2]/input")).sendKeys("zarplata");
        driver.findElement(By.xpath("//*[@id=\"forgot-form\"]/div/input[2]")).click();
        //Do: Перейти в личный кабинет, нажав Личный кабинет в боковом меню Assert: Открыается страница личного кабинета
        driver.findElement(By.xpath("//a[contains(@href, '/cabinet/profile/?backurl=%2F')]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("/cabinet/profile/?backurl=%2F"));
        driver.findElement(By.xpath("//a[contains(text(),'Персональные данные')]")).click();
        //Выполним проверку соответствия данных пользователя, указанных в поле E-mail, с E-mail, который введен при авторизации
        assertEquals("masha2410@mail.ru",  driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/div[2]/form/div[1]/div/div[1]/div/div[1]/div[2]")).getText());
    }
}



