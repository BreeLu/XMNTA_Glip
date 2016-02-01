package com.ringcentral.xmn.ta.application.screen.ios.login;

import com.ringcentral.xmn.ta.application.model.ILoginPage;
import com.ringcentral.xmn.ta.core.driverLauncher.IDriver;
import io.appium.java_client.MobileElement;

public class LoginPage implements ILoginPage {
    private static IDriver driver;

    public LoginPage() {
        driver = new IDriver();
    }

    @Override
    public MobileElement getPhoneNumberField() {
        return driver.getElementById("Phone Number");
    }

    @Override
    public MobileElement getPasswordField() {
        return driver.getElementById("Password");
    }

    @Override
    public MobileElement getLoginButton() {
        return driver.getElementById("Log In");
    }
}
