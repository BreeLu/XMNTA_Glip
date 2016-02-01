package com.ringcentral.xmn.ta.test.login;

import com.ringcentral.xmn.ta.core.MobileTest;
import io.appium.java_client.MobileElement;
import org.testng.annotations.Test;

public class LoginPage extends MobileTest {
    @Test
    public void login() {
        MobileElement phoneNumberField = getMobile().getLoginPage().getPhoneNumberField();
        assert phoneNumberField != null;
        phoneNumberField.tap(1,1);
        phoneNumberField.sendKeys("18552700002");
        MobileElement passwordField = getMobile().getLoginPage().getPasswordField();
        assert passwordField != null;
        passwordField.tap(1,1);
        passwordField.sendKeys("Test!123");
        MobileElement loginButton = getMobile().getLoginPage().getLoginButton();
        assert loginButton != null;
        loginButton.click();
    }
}
