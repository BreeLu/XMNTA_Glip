package com.ringcentral.xmn.ta.application.screen.android.login;


import com.ringcentral.xmn.ta.application.model.ILoginPage;
import com.ringcentral.xmn.ta.core.driverLauncher.ADriver;
import io.appium.java_client.MobileElement;

public class LoginPage  implements ILoginPage {
    private static ADriver driver;

    public LoginPage() {
        driver = new ADriver();
    }

    @Override
    public MobileElement getPhoneNumberField() {
        return driver.getElementById("phone");
    }

    @Override
    public MobileElement getPasswordField() {
        return driver.getElementById("password");
    }

    @Override
    public MobileElement getLoginButton() {
        return driver.getElementById("btnSignIn");
    }
}
