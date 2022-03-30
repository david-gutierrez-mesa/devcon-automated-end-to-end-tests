package stepDefinition.privateLayouts;

import base.TestContext;
import io.cucumber.java8.En;
import portalObjects.layouts.PrivateLayout;

import static junit.framework.TestCase.assertTrue;

public class HomePageSteps implements En {

    private final PrivateLayout privateLayoutHomePage;

    public HomePageSteps(TestContext testContext) {
        this.privateLayoutHomePage = null;

        When("^I navigate to \"([^\"]*)\"$", (String page) -> {
            //TODO Step 2 Execution
        });

    }

}
