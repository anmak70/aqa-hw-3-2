
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;

import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.cssSelector;

public class LoginPage {

    By loginLocator = cssSelector("[data-test-id=login] input");
    By passwordLocator = cssSelector("[data-test-id=password] input");
    By logButton = cssSelector("[data-test-id=action-login]");
    By passErrorLocator = cssSelector("[data-test-id=error-notification]");

    private SelenideElement loginField = $(loginLocator);
    private SelenideElement passwordField = $(passwordLocator);
    private SelenideElement loginButton = $(logButton);
    private SelenideElement passError = $(passErrorLocator);

    public VerificationPage validLogin() throws SQLException {
         loginField.setValue("vasya");
         passwordField.setValue("qwerty123");
         loginButton.click();
         return new VerificationPage();
    }

    public void notValidLogin() {
        loginField.setValue("vasya");
        passwordField.setValue("qwerty");
        loginButton.click();
        passError.waitUntil(Condition.visible, 15000);
    }

}
