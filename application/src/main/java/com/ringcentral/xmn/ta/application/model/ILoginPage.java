package com.ringcentral.xmn.ta.application.model;

import io.appium.java_client.MobileElement;

public interface ILoginPage {
    MobileElement getPhoneNumberField();
    MobileElement getPasswordField();
    MobileElement getLoginButton();
}
