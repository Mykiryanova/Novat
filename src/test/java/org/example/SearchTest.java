package org.example;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import java.util.concurrent.TimeUnit;
import static org.junit.jupiter.api.Assertions.assertNotEquals;


// Покупка невозможна без регистрации, поэтому в данном тесте также отражены операции по регистрации пользователя

public class SearchTest {
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
    @DisplayName("Поиск спектакля")
    void searchTest() {
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

        // 2. Поиск спектакля
        // Do:Перейти на страницу поиска, нажав на значок лупы Assert: Открывается страница  с поисковой строкой
        WebElement icon =  driver.findElement(By.xpath("//*[@class=\"additions__link search-icon\"]"));
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();",icon);
        icon.click();
        WebElement search = driver.findElement(By.name("q"));
        // Do:Ввестив поисковую строку название спектакля "Дон Кихот" Assert: Открывается страница  с результатами запроса
        search.sendKeys("Дон Кихот");
        driver.findElement(By.className("input-search-icon")).click();
        //Проверка того, что поисковый элемент найден (т.е. результат не равен 0 - взяла так, потому что данные могут меняться, сегодня найдено 145, завтра афишу поменяли стало 146)
        assertNotEquals ("Найдено: 0",  driver.findElement(By.xpath("//*[@class=\"search-result\"]")).getText());
    }
}


