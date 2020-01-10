import com.github.javafaker.Faker;
import lombok.val;
import org.apache.commons.dbutils.QueryRunner;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.DriverManager;
import java.sql.SQLException;

import static com.codeborne.selenide.Selenide.open;

public class TestLogin {

    @BeforeAll
    static void userAdd() throws SQLException {
        val faker = new Faker();
        val runner = new QueryRunner();
        val dataSQL = "INSERT INTO users (id, login, password) VALUES (?, ?, ?);";
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pas");
        ) {
            runner.update(conn, dataSQL, faker.bothify("??##??"), faker.name().username(), faker.bothify("??##??"));
        }
    }

    @AfterAll
    static void deleteTables() throws SQLException {
        val deleteAuthCodes = "DELETE FROM auth_codes";
        val deleteCards = "DELETE FROM cards";
        val deleteUsers = "DELETE FROM users";
        val runner = new QueryRunner();
        try (
                val conn = DriverManager.getConnection("jdbc:mysql://192.168.99.100:3306/app", "app", "pas");
        ) {
            runner.update(conn, deleteAuthCodes);
            runner.update(conn, deleteCards);
            runner.update(conn, deleteUsers);
        }
    }

    @Test
    void loginValidTest() throws SQLException {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        VerificationPage verificationPage = loginPage.validLogin();
        DashboardPage dashboardPage = verificationPage.validVerity();
        dashboardPage.dashboardPageVisible();
    }

    @Test
    void loginNotValidTest() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();
        loginPage.notValidLogin();
    }
}
