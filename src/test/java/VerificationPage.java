import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.ScalarHandler;
import org.openqa.selenium.By;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.cssSelector;

public class VerificationPage {
    By codeLocator = cssSelector("[data-test-id=code] input");
    By verifyButtonLocator = cssSelector("[data-test-id=action-verify]");

    private SelenideElement codeField = $(codeLocator);
    private SelenideElement verifyButton = $(verifyButtonLocator);

    public VerificationPage() {
        codeField.waitUntil(Condition.visible, 15000);
    }

    public DashboardPage validVerity() throws SQLException {
        val userId = "SELECT id FROM users WHERE login = ?;";
        val authCode = "SELECT code FROM auth_codes WHERE user_id = ?;";
        val runner = new QueryRunner();

        try (
                val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pas");
        ) {
            val userIdAuth = runner.query(conn, userId, "vasya", new ScalarHandler<>());
            val authCodeId = runner.query(conn, authCode, userIdAuth, new ScalarHandler<>());
            System.out.println("Код верификации: " + authCodeId);
            codeField.setValue(String.valueOf(authCodeId));
            verifyButton.click();
        }
        return new DashboardPage();
    }
}
