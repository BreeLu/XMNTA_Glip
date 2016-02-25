package com.ringcentral.xmn.ta.core;

import com.google.gson.Gson;
import com.ringcentral.xmn.ta.application.model.MobileApp;
import com.ringcentral.xmn.ta.core.data.DeviceInfo;
import com.ringcentral.xmn.ta.core.driverLauncher.MobileDriver;
import com.ringcentral.xmn.ta.core.utils.CommandLineUtils;
import com.ringcentral.xmn.ta.core.utils.FileUtils;
import com.ringcentral.xmn.ta.core.utils.ProcessCSVFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MobileTest {
    private static final Logger LOG = LoggerFactory.getLogger("CORELog");

    private static MobileApp mobile;

    private static MobileDriver driver;

    public static MobileApp getMobile() {
        return mobile;
    }

    /**
     * init
     * prepare app
     * prepare account
     * *
     */
    @BeforeMethod
    public void beforeMethod() {
        LOG.info("MobileTest beforeMethod , setup a session");
        initConfig();
        prepareApp();
    }

    /**
     * do the logout
     * *
     */
    @AfterMethod(alwaysRun = true)
    public void afterMethod() {
        //todo : wrap driver and screen
        LOG.info("afterMethod , quit ther driver");
        driver.quitDriver();

    }

    /**
     * *
     * config the device info according to the deviceId
     * set app path/
     * ***
     */
    private void initConfig() {
        List<String> deviceIdList = getDeviceIdInUse();
        List<DeviceInfo> initDevices = new ArrayList<>();
        String configFile;
        if (deviceIdList.get(0).length() == 40) {
            configFile = System.getProperty("user.dir") + "/core/src/main/resources/iosDeviceList.csv";
        } else {
            configFile = System.getProperty("user.dir") + "/core/src/main/resources/androidDeviceList.csv";
        }
        List<DeviceInfo> deviceInfos = null;
        try {
            deviceInfos = new ProcessCSVFile(new File(configFile)).processDevicePoolFile();
        } catch (IOException e) {
            LOG.info("Process device Pool exception , exception detail : " + e.getMessage());
        }

        for (String deviceId : deviceIdList) {
            for (DeviceInfo deviceInfo : deviceInfos) {
                if (deviceId.equals(deviceInfo.getUDID())) {
                    String platform;
                    if (deviceInfo.getUDID().length() == 40) {
                        platform = "ios";
                    } else {
                        platform = "android";
                    }
                    deviceInfo.setApp(getAppAbsolutePath(platform));
                    if (deviceId.length() < 40) {
                        deviceInfo.setPackageName(getPackageName());
                    }
                    initDevices.add(deviceInfo);
                }
            }
        }

        for (DeviceInfo deviceInfo : initDevices) {
            launchDriver(deviceInfo);
        }
    }

    /**
     * *****
     * 1. uninstall/install app
     * 2. remove tips if first Login
     * *******
     */
    private void prepareApp() {

    }

    private static List<String> getDeviceIdInUse() {
        //todo : maybe can change another strategy
        List<String> deviceIdList = null;
        String commandLine;
//        String platform = System.getProperty("platform");
        String platform = "ios";
        LOG.info("Current test platform is " + platform);
        if ("ios".equalsIgnoreCase(platform)) {
            commandLine = "/usr/local/Cellar/libimobiledevice/1.2.0/bin/idevice_id -l";
        } else {
            commandLine = "adb devices";
        }
        try {
            deviceIdList = CommandLineUtils.runtimeCommand(commandLine);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceIdList;
    }

    private void launchDriver(DeviceInfo deviceInfo) {
        LOG.info("DeviceInfo = " + new Gson().toJson(deviceInfo));
        String driverName;
        //todo
//        String driverName = "ios".equalsIgnoreCase(System.getProperty("platform")) ? "IOSDriver" : "AndroidDriver";
        if (deviceInfo.getUDID().length() == 40) {
            driverName = "IOSDriver";
        } else {
            driverName = "AndroidDriver";
        }

        driver = MobileDriver.getDriverByName(driverName);
        if (driver != null) {
            driver.setupDriver(deviceInfo);
        }
        mobile = MobileApp.getMobileApp(driverName);
        if (mobile != null) {
            System.out.println("mobile = " + new Gson().toJson(mobile));
            mobile.initApp(driver);
        }
    }

    public static String getBrand() {
        String currentBrand = "RCMobile";
        if (System.getProperty("brand") != null) {
            currentBrand = System.getProperty("brand");
            return currentBrand;
        }
        return currentBrand;
    }

    private String getAppAbsolutePath(String platform) {
        String postFix = "ios".equalsIgnoreCase(platform) ? ".ipa" : ".apk";
        String appName = FileUtils.getAppName(System.getProperty("user.dir"), postFix);
        String userDir = System.getProperty("user.dir");
        File classpathRoot = new File(userDir);
        File app = new File(classpathRoot, appName);
        return app.getAbsolutePath();
    }

    private String getPackageName() {
        String packageName;
        switch (getBrand()) {
            case "RCMobile":
                packageName = "com.ringcentral.android";
                break;
            case "BT":
                packageName = "com.bt.cloudphone";
                break;
            case "ATT":
                packageName = "com.attofficeathand.android";
                break;
            case "Telus":
                packageName = "com.telusvoip.android";
                break;
            default:
                packageName = "com.ringcentral.android";
                break;
        }
        return packageName;
    }

}
