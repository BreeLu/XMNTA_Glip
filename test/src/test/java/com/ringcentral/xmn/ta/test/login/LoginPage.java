package com.ringcentral.xmn.ta.test.login;

import com.ringcentral.xmn.ta.core.MobileTest;
import io.appium.java_client.MobileElement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

public class LoginPage extends MobileTest {
    private static final Logger LOG = LoggerFactory.getLogger(LoginPage.class);

    @Test
    public void login() {
        MobileElement phoneNumberField = getMobile().getLoginPage().getPhoneNumberField();
        assert phoneNumberField != null;
        phoneNumberField.tap(1, 1);
        getMobile().getLoginPage().enterPhoneNum("18552700002");
        LOG.info("Step 1 .  enter phone number");
        MobileElement passwordField = getMobile().getLoginPage().getPasswordField();
        assert passwordField != null;
        passwordField.tap(1, 1);
        getMobile().getLoginPage().enterPassword("Test!123");
        LOG.info("Step 2 .  enter correct password");
        MobileElement loginButton = getMobile().getLoginPage().getLoginButton();
        assert loginButton != null;
        loginButton.click();
        LOG.info("Step 3 .  Tap 'login' Button");
        try {
            new Thread().sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
