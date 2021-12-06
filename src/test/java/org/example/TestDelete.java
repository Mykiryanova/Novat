// !!! Запускать только после теста покупки билета !!!
package org.example;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

public class TestDelete {

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
    @DisplayName("Удаление билета из корзины")
    void deleteTest() {
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
        // 2. Do: Нажать на пункт меню Ваша корзина (1) Assert: Открывается страница корзины. В корзине имеется билет, выбранный в тесте BuyTest
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", driver.findElement(By.xpath("/html/body/div[2]/div/div/aside/div[7]/a[4]")));
        driver.findElement(By.xpath("/html/body/div[2]/div/div/aside/div[7]/a[4]")).click();
        new WebDriverWait(driver, 10).until(ExpectedConditions.urlContains("/cart/"));
        // Do: Кликнуть на крестик в билете Assert: Билет удаляется. Корзина пустая
        driver.findElement(By.xpath("/html/body/div[2]/div/div/div/div[2]/form/div[2]/div[2]/div[3]/div/a")).click();

        assertNotEquals("Ваша корзина (1)", driver.findElement(By.xpath("/html/body/div[2]/div/div/aside/div[7]/a[4]")).getText());

    }
}
