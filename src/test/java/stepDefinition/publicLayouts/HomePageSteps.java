package stepDefinition.publicLayouts;

import base.TestContext;
import io.cucumber.java.Before;
import io.cucumber.java8.En;
import portalObjects.layouts.PrivateLayout;
import portalObjects.layouts.PublicLayout;
import portalObjects.users.RegisteredUser;
import portalObjects.users.UserFactory;
import portalObjects.users.UserTypes;
import utils.APIs.Company;
import utils.APIs.Users;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static junit.framework.TestCase.assertTrue;
import static utils.APIs.Users.deleteUser;

public class HomePageSteps implements En {

    private static boolean firstExecution = false;
    private final TestContext testContext;
    private final PublicLayout publicLayoutHomePage;

    public HomePageSteps(TestContext testContext) {
        this.testContext = testContext;
        this.publicLayoutHomePage = this.testContext.getLayoutObjectManager().getPublicLayoutHomePage();

        Given("^I am logged in Liferay as \"([^\"]*)\"$", (UserTypes userType) -> {
            RegisteredUser user = UserFactory.getUser(userType);
            this.testContext.setUser(user);

            this.publicLayoutHomePage.navigateToCurrentPage();

            PrivateLayout privateHomePage = this.publicLayoutHomePage.getLoginComponent().doLogin(this.testContext.getUser());

            assertTrue(privateHomePage.stickerOverlayIsDisplayed());
        });

    }

    @Before
    public void beforeAll() throws IOException, TimeoutException {
        if (!firstExecution) {
            RegisteredUser user = UserFactory.getUser(UserTypes.STANDARD_USER);
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                try {
                    deleteUser(user);
                } catch (IOException | TimeoutException e) {
                    e.printStackTrace();
                }
            }));
            Company.disablePasswordChangeRequired();

            Users.createUser(user);
            Users.addUserToASiteByEmail(user.getEmail(), "Guest");
            firstExecution = true;

        }

    }

}
