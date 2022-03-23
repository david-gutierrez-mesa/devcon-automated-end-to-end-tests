package utils.APIs;

import com.liferay.poshi.runner.util.JSONCurlUtil;
import portalObjects.users.RegisteredUser;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static utils.APIs.Company.getCompanyId;
import static utils.APIs.Generic.*;
import static utils.APIs.Site.getSiteId;

public class Users {
    private Users() {
    }


    public static void addUserToASiteByEmail(String email, String siteName) throws IOException, TimeoutException {
        String userId = getUserIdByEmailAddress(email);
        String siteId = getSiteId(siteName);

        addUserToASite(userId, siteId);
    }

    public static void addUserToASite(String userId, String siteId) throws IOException, TimeoutException {
        String baseUrl = getPortalURL();

        String curl = String.format("%sapi/jsonws/user/add-group-users/group-id/%s/user-ids/%s -u %s:%s", baseUrl, siteId, userId, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);
        JSONCurlUtil.post(curl);
    }

    public static void agreeToTermsAndAnswerReminderQuery(String userId) throws IOException, TimeoutException {
        String baseUrl = getPortalURL();

        String curl = String.format("%sapi/jsonws/user/update-agreed-to-terms-of-use/user-id/%s/agreed-to-terms-of-use/true -u %s:%s", baseUrl, userId, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);
        JSONCurlUtil.post(curl);

        curl = String.format("%sapi/jsonws/user/update-reminder-query/user-id/%s/question/what-is-your-father%%27s-middle-name/answer/test t-u %s:%s", baseUrl, userId, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);
        JSONCurlUtil.post(curl);
    }

    public static void createUser(RegisteredUser user) throws IOException, TimeoutException {

        String companyId = getCompanyId();
        String requestURL = getPortalURL() + "api/jsonws/user/add-user";

        String curl = requestURL + " " +
                "-u " + ADMIN_USER_EMAIL + ":" + ADMIN_USER_PASSWORD + " " +
                "-d companyId=" + companyId + " " +
                "-d autoPassword=false " +
                "-d password1=" + user.getPassword() + " " +
                "-d password2=" + user.getPassword() + " " +
                "-d autoScreenName=false " +
                "-d screenName=" + user.getScreenName() + " " +
                "-d emailAddress=" + user.getEmail() + " " +
                "-d facebookId=0 " +
                "-d openId= " +
                "-d locale=" + user.geLocale().getCountry() + " " +
                "-d firstName=" + user.getFirstName() + " " +
                "-d middleName=" + user.getMiddleName() + " " +
                "-d lastName=" + user.getLastName() + " " +
                "-d prefixId=0 " +
                "-d suffixId=0 " +
                "-d male=true " +
                "-d birthdayMonth=" + user.getDateOfBirth().getMonthValue() + " " +
                "-d birthdayDay=" + user.getDateOfBirth().getDayOfMonth() + " " +
                "-d birthdayYear=" + user.getDateOfBirth().getYear() + " " +
                "-d jobTitle= " +
                "-d groupIds= " +
                "-d organizationIds= " +
                "-d roleIds= " +
                "-d userGroupIds= " +
                "-d sendEmail=false";

        JSONCurlUtil.post(curl);

        String userId = getUserIdByEmailAddress(user.getEmail());
        agreeToTermsAndAnswerReminderQuery(userId);
    }

    public static void deleteUser(RegisteredUser user) throws IOException, TimeoutException {

        //TODO Step 4 Clean up the environment

    }

    public static String getUserIdByEmailAddress(String emailAddress) throws IOException, TimeoutException {
        String baseUrl = getPortalURL();

        String companyId = getCompanyId();

        String curl = String.format("%sapi/jsonws/user/get-user-by-email-address/company-id/%s/email-address/%s -u %s:%s", baseUrl, companyId, emailAddress, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);

        return JSONCurlUtil.get(curl, "$['userId']");
    }
}
