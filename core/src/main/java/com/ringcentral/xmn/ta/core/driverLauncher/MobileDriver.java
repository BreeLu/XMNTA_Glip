package com.ringcentral.xmn.ta.core.driverLauncher;

import com.ringcentral.xmn.ta.application.model.ILoginPage;
import com.ringcentral.xmn.ta.core.data.DeviceInfo;
import io.appium.java_client.MobileElement;

import java.util.ArrayList;
import java.util.List;

public abstract class MobileDriver {
    public static List<MobileDriver> mobileDrivers;
    public String driverName;

    static {
        mobileDrivers = new ArrayList<>();
        mobileDrivers.add(new ADriver());
        mobileDrivers.add(new IDriver());
    }

    public abstract void setupDriver(DeviceInfo deviceInfo);

    abstract MobileDriver getByPlatform();

    public String getDriverName() {
        return driverName;
    }

    public static MobileDriver getDriverByName(String driverName) {
        for (MobileDriver driver : mobileDrivers) {
            if (driverName.equals(driver.getDriverName())) {
                return driver;
            }
        }
        return null;
    }

    public abstract MobileElement getElementById(String elementId);

    public abstract MobileElement getElementByName(String elementName);

    public abstract MobileElement getElementByXPath(String xpath);

    public abstract List<MobileElement> getElementsByClassName(String className);

    public abstract void back();

    public abstract void scrollToElement(String elementName);

    public abstract void tapByLocation(int posX, int posY, Integer duration);

    public abstract void swipe(int fromX, int fromY, int toX, int toY, Integer duration);

    public abstract boolean installApp();

    public abstract boolean uninstallApp();

    public abstract void takeScreenShot(String path);

    public abstract void rotateDevice();

    public abstract void sendKeysInKeyboard(String keyCode);

    public abstract ILoginPage getLoginPage();


}
