package com.ringcentral.xmn.ta.application.model;

import io.appium.java_client.MobileElement;

public interface ILoginPage {
    MobileElement getPhoneNumberField();

    void enterPhoneNum(String phoneNum);

    MobileElement getPasswordField();

    void enterPassword(String password);

    MobileElement getLoginButton();
}
