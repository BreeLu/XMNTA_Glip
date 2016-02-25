package com.ringcentral.xmn.ta.core.driverLauncher;

import com.ringcentral.xmn.ta.application.model.ILoginPage;
import com.ringcentral.xmn.ta.core.data.DeviceInfo;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ADriver extends MobileDriver {
    private static AndroidDriver driver;
    public static final String DEFAULT_WD_HUB_URL = "http://127.0.0.1:4723/wd/hub";

    public ADriver() {

    }

    public void setupDriver(DeviceInfo deviceInfo) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceInfo.getPlatformVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfo.getDeviceName());
        desiredCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, deviceInfo.getAppiumVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceInfo.getUDID());
        desiredCapabilities.setCapability(MobileCapabilityType.APP, deviceInfo.getApp());
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "999999");
        desiredCapabilities.setCapability("appPackage", deviceInfo.getPackageName());
        try {
            driver = new AndroidDriver(new URL(DEFAULT_WD_HUB_URL), desiredCapabilities);
            driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void quitDriver() {
        driver.quit();
    }

    public String getDriverName() {
        return "AndroidDriver";
    }

    @Override
    MobileDriver getByPlatform() {
        return null;
    }

    @Override
    public MobileElement getElementById(String elementId) {
        WebElement element = null;
        try {
            element = driver.findElement(By.id(elementId));
        } catch (Exception e) {
            System.out.println("[WARN] cannot find element with id = " + elementId);
        }
        return (MobileElement) element;
    }

    @Override
    public MobileElement getElementByName(String elementName) {
        WebElement element = null;
        try {
            element = driver.findElement(By.name(elementName));
        } catch (Exception e) {
            System.out.println("[WARN] cannot find element with elementName = " + elementName);
        }
        return (MobileElement) element;
    }

    @Override
    public MobileElement getElementByXPath(String xpath) {
        WebElement element = null;
        try {
            element = driver.findElement(By.xpath(xpath));
        } catch (Exception e) {
            System.out.println("[WARN] cannot find element with xpath = " + xpath);

        }
        return (MobileElement) element;
    }

    @Override
    public List<MobileElement> getElementsByClassName(String className) {
        return driver.findElementsByClassName(className);
    }


    @Override
    public void back() {
        driver.navigate().back();
    }

    @Override
    public void scrollToElement(String elementName) {

    }

    @Override
    public void tapByLocation(int posX, int posY, Integer duration) {

    }

    @Override
    public void swipe(int fromX, int fromY, int toX, int toY, Integer duration) {

    }

    @Override
    public boolean installApp() {
        return false;
    }


    @Override
    public boolean uninstallApp() {
        return false;
    }

    @Override
    public void takeScreenShot(String path) {

    }

    @Override
    public void rotateDevice() {

    }

    @Override
    public void sendKeysInKeyboard(String keyCode) {
        driver.getKeyboard().sendKeys(keyCode);
    }

    @Override
    public ILoginPage getLoginPage() {
        return null;
    }

}
