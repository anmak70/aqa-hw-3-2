import com.codeborne.selenide.Condition;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static org.openqa.selenium.By.cssSelector;

public class DashboardPage {
    By dashboardLocator = cssSelector("[data-test-id=dashboard]");
    public void dashboardPageVisible () {
        $(dashboardLocator).waitUntil(Condition.visible, 15000);
    }
}
