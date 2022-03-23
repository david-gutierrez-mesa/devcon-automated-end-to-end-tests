package utils.APIs;

import com.liferay.poshi.runner.util.JSONCurlUtil;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import static utils.APIs.Generic.*;

public class Company {
    private Company() {
    }

    public static void disablePasswordChangeRequired() throws IOException, TimeoutException {
        String baseUrl = getPortalURL();
        String companyId = getCompanyId();

        //For 7.2 and older
        //String curl = String.format("%sapi/jsonws/passwordpolicy/search/company-id/%s/name/default/start/0/end/1/-obc -u %s:%s", baseUrl, companyId, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);

        //For 7.3 and newer
        String curl = String.format("%sapi/jsonws/passwordpolicy/search " +
                        "-u %s:%s " +
                        "-d companyId=%s " +
                        "-d \"name=Default%%20Password%%20Policy\" " +
                        "-d \"start=-1\" " +
                        "-d \"end=-1\" " +
                        "-d -orderByComparator=",
                baseUrl, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD, companyId);

        String passwordPolicyId = JSONCurlUtil.post(curl, "$.[?(@['name'] == 'Default Password Policy')]['passwordPolicyId']");

        passwordPolicyId = passwordPolicyId.replace("[\"", "").replace("\"]", "");

        curl = baseUrl + "api/jsonws/passwordpolicy/update-password-policy " +
                "-u " + ADMIN_USER_EMAIL + ":" + ADMIN_USER_PASSWORD + " " +
                "-d passwordPolicyId=" + passwordPolicyId + " " +
                "-d name='Default Password Policy' " +
                "-d description='Default Password Policy' " +
                "-d changeable=false " +
                "-d changeRequired=false " +
                "-d minAge=0 " +
                "-d checkSyntax=false " +
                "-d allowDictionaryWords=true " +
                "-d minAlphanumeric=0 " +
                "-d minLength=6 " +
                "-d minLowerCase=0 " +
                "-d minNumbers=1 " +
                "-d minSymbols=0 " +
                "-d minUpperCase=1 " +
                "-d regex=\"(?=.{4})(?:[a-zA-Z0-9]*)\" " +
                "-d history=false " +
                "-d historyCount=6 " +
                "-d expireable=false " +
                "-d maxAge=8640000 " +
                "-d warningTime=86400 " +
                "-d graceLimit=0 " +
                "-d lockout=false " +
                "-d maxFailure=3 " +
                "-d lockoutDuration=0 " +
                "-d resetFailureCount=600 " +
                "-d resetTicketMaxAge=86400";

        JSONCurlUtil.post(curl);
    }

    public static String getCompanyId() throws IOException, TimeoutException {
        String portalInstanceName = getPortalInstanceName();

        String portalURL = getPortalURL();

        String curl = String.format("%sapi/jsonws/company/get-company-by-virtual-host/virtual-host/%s -u %s:%s", portalURL, portalInstanceName, ADMIN_USER_EMAIL, ADMIN_USER_PASSWORD);

        return JSONCurlUtil.get(curl, "$['companyId']");
    }

}