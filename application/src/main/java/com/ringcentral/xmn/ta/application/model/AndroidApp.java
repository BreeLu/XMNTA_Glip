package com.ringcentral.xmn.ta.application.model;


import com.ringcentral.xmn.ta.application.screen.android.login.LoginPage;
import com.ringcentral.xmn.ta.core.driverLauncher.MobileDriver;

public class AndroidApp extends MobileApp{

    private final ILoginPage loginPage = new LoginPage() ;

    public String getDriverName() {
        return "AndroidDriver";
    }

    @Override
    public void initApp(MobileDriver driverName) {

    }

    @Override
    public ILoginPage getLoginPage() {
        return loginPage;
    }
}
