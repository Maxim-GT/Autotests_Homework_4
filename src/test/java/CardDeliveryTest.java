import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;


public class CardDeliveryTest {


    @BeforeEach
    void SetUp() {
        open("http://localhost:9999/");
    }

    String generateDate(int days) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    @Test
    void shouldFillTheForm() {
        $("[placeholder= 'Город']").setValue("Абакан");
        $("[class= 'input__control'][placeholder= 'Дата встречи']").sendKeys(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE);
        String date = generateDate(3);
        $("[class= 'input__control'][placeholder= 'Дата встречи']").setValue(date);
        $("[class= 'input__control'][name= 'name']").setValue("Иван Иванов");
        $("[type= 'tel'][name= 'phone']").setValue("+71234567890");
        $("[class= 'checkbox__box']").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + date));

    }

    @Test
    void shouldFillTheFormWithConditions() {
        $("[placeholder= 'Город']").setValue("Аб");
        $("[class= 'menu-item__control']").click();
        $("[class='icon-button icon-button_size_m icon-button_theme_alfa-on-white']").click();
        String date = generateDate(7);
        if (LocalDate.now().getMonth().equals(LocalDate.now().plusDays(7).getMonth())) {
            $$("td").find(exactText("20")).click();
        } else {
            $("[class='calendar__arrow calendar__arrow_direction_right']").click();
            $$("td").find(exactText("")).click();
        }
        $("[class= 'input__control'][name= 'name']").setValue("Иван Иванов");
        $("[type= 'tel'][name= 'phone']").setValue("+71234567890");
        $("[class= 'checkbox__box']").click();
        $("[class='button__content']").click();
        $("[data-test-id='notification']").shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='notification']").shouldHave(text("Встреча успешно забронирована на " + date));
    }
}
