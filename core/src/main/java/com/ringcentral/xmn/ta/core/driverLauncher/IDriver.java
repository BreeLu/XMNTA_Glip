package com.ringcentral.xmn.ta.core.driverLauncher;

import com.ringcentral.xmn.ta.application.model.ILoginPage;
import static com.ringcentral.xmn.ta.core.MobileTest.getBrand;
import com.ringcentral.xmn.ta.core.data.DeviceInfo;
import static com.ringcentral.xmn.ta.core.utils.CommandLineUtils.runtimeCommand;
import io.appium.java_client.MobileElement;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import org.openqa.selenium.ScreenOrientation;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class IDriver extends MobileDriver {
    private static IOSDriver iosDriver;
    private static WebDriverWait driverWait;
    public static final String DEFAULT_WD_HUB_URL = "http://127.0.0.1:4723/wd/hub";
    public static final int timeoutInSeconds = 60;

    public IDriver() {

    }


    public void setupDriver(DeviceInfo deviceInfo) {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability(MobileCapabilityType.PLATFORM_VERSION, deviceInfo.getPlatformVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.DEVICE_NAME, deviceInfo.getDeviceName());
        desiredCapabilities.setCapability(MobileCapabilityType.APPIUM_VERSION, deviceInfo.getAppiumVersion());
        desiredCapabilities.setCapability(MobileCapabilityType.UDID, deviceInfo.getUDID());
        desiredCapabilities.setCapability(MobileCapabilityType.APP, deviceInfo.getApp());
        desiredCapabilities.setCapability(MobileCapabilityType.NEW_COMMAND_TIMEOUT, "999999");

        try {
            iosDriver = new IOSDriver(new URL(DEFAULT_WD_HUB_URL), desiredCapabilities);
            driverWait = new WebDriverWait(iosDriver, timeoutInSeconds);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public String getDriverName() {
        return "IOSDriver";
    }

    @Override
    MobileDriver getByPlatform() {
        return null;
    }

    @Override
    public MobileElement getElementById(String elementId) {
        MobileElement element = null;
        try {
            element = (MobileElement) iosDriver.findElementByAccessibilityId(elementId);
        } catch (WebDriverException e) {
            System.out.println("[WARN] cannot find element with accessibilityId = " + elementId);
        }
        return element;

    }

    @Override
    public MobileElement getElementByName(String elementName) {
        MobileElement element = null;
        try {
            element = (MobileElement) iosDriver.findElementByAccessibilityId(elementName);
        } catch (WebDriverException e) {
            System.out.println("[WARN] cannot find element with name = " + elementName);
        }
        return element;
    }

    @Override
    public MobileElement getElementByXPath(String xpath) {
        MobileElement element = null;
        try {
            element = (MobileElement) iosDriver.findElementByAccessibilityId(xpath);
        } catch (WebDriverException e) {
            System.out.println("[WARN] cannot find element with xpath = " + xpath);
        }
        return element;
    }

    @Override
    public List<MobileElement> getElementsByClassName(String className) {
        List<MobileElement> mobileElementList = new ArrayList<>();
        try {
            mobileElementList = iosDriver.findElementsByClassName(className);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mobileElementList;
    }


    @Override
    public void back() {
        if (getElementByName("Back") != null) {
            getElementByName("Back").click();
        } else {
            iosDriver.navigate().back();
        }
    }

    @Override
    public void scrollToElement(String elementName) {
        try {
            iosDriver.scrollTo(elementName);
        } catch (Exception e) {
            System.out.println("[WARN] cannot locate the element with text + " + elementName);
        }

    }

    @Override
    public void tapByLocation(int posX, int posY, Integer duration) {
        iosDriver.tap(1, posX, posY, duration);
    }

    @Override
    public void swipe(int fromX, int fromY, int toX, int toY, Integer duration) {
        iosDriver.swipe(fromX, fromY, toX, toY, duration);
    }

    @Override
    public boolean installApp() {
        String IDEVICEINSTALLER_PATH = "/usr/local/Cellar/ideviceinstaller/1.1.0_1/bin/ideviceinstaller";
        List<String> rtnList;
        try {
            rtnList = runtimeCommand(IDEVICEINSTALLER_PATH + String.format(" -i %s", ""));
            for (String rtn : rtnList) {
                if (rtn.contains("Complete")) {
                    List<String> rtnList2 = runtimeCommand(IDEVICEINSTALLER_PATH + " -l");
                    for (String rtn2 : rtnList2) {
                        if (rtn2.contains(getAppBoundId(getBrand())))
                            return true;
                    }
                    System.out.println("*ERROR*: ideviceinstaller -U " + getAppBoundId(getBrand()) + " failed");
                    return false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean uninstallApp() {
        String IDEVICEINSTALLER_PATH = "/usr/local/Cellar/ideviceinstaller/1.1.0_1/bin/ideviceinstaller";
        List<String> rtnList;
        try {
            rtnList = runtimeCommand(IDEVICEINSTALLER_PATH + String.format(" -U %s", ""));
            for (String rtn : rtnList) {
                if (rtn.contains("Complete"))
                    return true;
            }
        } catch (Exception e) {
            System.out.println("[Error] fail to uninstall app , error msg is " + e.getMessage());
        }
        return false;
    }

    @Override
    public void takeScreenShot(final String path) {
        String commandLine = "/usr/local/Cellar/libimobiledevice/1.2.0/bin/idevicescreenshot " + path;
        try {
            runtimeCommand(commandLine);
            new Thread() {
                @Override
                public void run() {
                    compressScreenShot(path);
                }
            }.start();
        } catch (Exception e) {
            System.out.println("[Error] fail to take screen shot , error msg is " + e.getMessage());
        }


    }

    @Override
    public void rotateDevice() {
        ScreenOrientation orientation = iosDriver.getOrientation();
        if (orientation.equals(ScreenOrientation.PORTRAIT)) {
            iosDriver.rotate(ScreenOrientation.LANDSCAPE);
        } else {
            iosDriver.rotate(ScreenOrientation.PORTRAIT);
        }
    }

    @Override
    public void sendKeysInKeyboard(String keyCode) {
        iosDriver.getKeyboard().sendKeys(keyCode);
    }

    @Override
    public ILoginPage getLoginPage() {
        return null;
    }

    private static void compressScreenShot(String path) {
        String commandLine = "/opt/ImageMagick/bin/mogrify -format png " + path;
        try {
            runtimeCommand(commandLine);
            File file = new File(path);
            if (file.isFile() && file.exists()) {
                file.delete();
            }
        } catch (Exception e) {
            System.out.println("[Error] fail to compress picture , error msg is " + e.getMessage());
        }

    }

    private static String getAppBoundId(String brand) {
        String appId;
        switch (brand) {
            case "RC":
                appId = "com.ringcentral.RingCentralMobile";
                break;
            case "RCMobile":
                appId = "com.ringcentral.RingCentralMobile";
                break;
            case "ATT":
                appId = "com.ringcentral.attviipfr";
                break;
            case "TELUS":
                appId = "com.ringcentral.RingCentralMobileTelus";
                break;
            case "BT":
                appId = "com.ringcentral.BTMobile";
                break;
            default:
                appId = "com.ringcentral.RingCentralMobile";
                break;
        }
        return appId;
    }



    //


}
