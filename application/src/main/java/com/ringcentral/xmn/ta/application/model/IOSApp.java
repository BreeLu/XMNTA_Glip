package com.ringcentral.xmn.ta.application.model;


import com.ringcentral.xmn.ta.application.screen.ios.login.LoginPage;
import com.ringcentral.xmn.ta.core.driverLauncher.MobileDriver;

public class IOSApp extends MobileApp{
    private MobileDriver driver;
    public ILoginPage getLoginPage() {
        return loginPage;
    }

    public IOSApp() {
    }

    public IOSApp(MobileDriver driver) {
        this.driver = driver;
    }

    private final ILoginPage loginPage = new LoginPage() ;

    public String getDriverName() {
        return "IOSDriver";
    }

    @Override
    public void initApp(MobileDriver driverName) {

    }

}
