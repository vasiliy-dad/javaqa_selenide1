package ru.netology.selenide;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

public class AppCardDeliveryTest {

    @BeforeEach
    void setup() {
        Selenide.open("http://localhost:9999");
    }

    private String generateDate(int days, String pattern) {
        return LocalDate.now().plusDays(days).format(DateTimeFormatter.ofPattern(pattern));
    }

    @Test
    void shouldSuccessOfReservationCard() {
        $("[data-test-id='city'] input").setValue("Казань");
        String planningDate = generateDate(4, "dd.MM.yyyy");
        $("[data-test-id='date'] input").press(Keys.chord(Keys.SHIFT, Keys.HOME), Keys.BACK_SPACE)
                .setValue(planningDate);
        $("[data-test-id='name'] input").setValue("Петров Иван Степанович");
        $("[data-test-id='phone'] input").setValue("+79876543210");
        $("[data-test-id='agreement']").click();
        $("button.button").shouldBe(Condition.exactText("Забронировать")).click();
        $(".notification__content")
                .shouldBe(visible, Duration.ofSeconds(15))
                .shouldHave(Condition.text("Встреча успешно забронирована на " + planningDate));
    }
}
