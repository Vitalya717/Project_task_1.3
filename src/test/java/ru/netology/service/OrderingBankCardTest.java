package ru.netology.service;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderingBankCardTest {
    private WebDriver driver;

    @BeforeAll
    static void setUpAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }
    @Test
    public void shouldSubmitRequest() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    public void ShouldSendRequestWithDoubleSurnameSeparatedByHyphen() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов-Сидоров Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    public void shouldSubmitRequestWithNameСontainingTheLetterЁ() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Аксёнов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }
    @Test
    public void shouldSubmitRequestWithNameСontainingTheLetterЙ() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Алексей");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='order-success']")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldSendRequestWithoutName() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameInLatinLetters() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Ivanov Ivan");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameInNumbers() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("123456");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameConsistingOnlyOfSpaces() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("    ");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameConsistingOnlyOfHyphens() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("-----");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameConsistingOfSpecialCharactersAndSymbols() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("!<?%#");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    public void ShouldSendRequestWithTheNameConsistingOfApostrophe() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов 'Иван'");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='name'].input_invalid .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }
    @Test
    public void shouldSubmitRequestWithoutPhoneNumber() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberStartingWithBigit() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("89179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingOfCyrillicLetters() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("Номер телефона");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingOfLatinLetters() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("Phone number");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingOfSpecialCharactersAndSymbols() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("!%><?-");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingFromSingleDigit() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+7");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingOfFiveDigits() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestForPhoneNumberConsistingOfTwelveDigits() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+791799977665");
        form.findElement(By.cssSelector("[data-test-id='agreement']")).click();
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='phone'].input_invalid .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }
    @Test
    public void shouldSubmitRequestWithoutAnAgreement() {
        WebElement form = driver.findElement(By.cssSelector(".form"));
        form.findElement(By.cssSelector("[data-test-id='name'] input")).sendKeys("Иванов Иван");
        form.findElement(By.cssSelector("[data-test-id='phone'] input")).sendKeys("+79179997766");
        form.findElement(By.cssSelector("[data-test-id='agreement']"));
        form.findElement(By.cssSelector(".button")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id='agreement'].input_invalid .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", text.trim());
    }


}
