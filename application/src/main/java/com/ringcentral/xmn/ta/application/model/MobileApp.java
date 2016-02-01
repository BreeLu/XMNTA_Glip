package com.ringcentral.xmn.ta.application.model;


import com.ringcentral.xmn.ta.core.driverLauncher.MobileDriver;

import java.util.ArrayList;
import java.util.List;

public abstract class MobileApp {
    public static List<MobileApp> mobileApps;
    public String driverName;

    static {
        mobileApps = new ArrayList<>();
        mobileApps.add(new com.ringcentral.xmn.ta.application.model.IOSApp());
        mobileApps.add(new com.ringcentral.xmn.ta.application.model.AndroidApp());
    }

    public String getDriverName() {
        return driverName;
    }

    public static MobileApp getMobileApp(String driverName) {
        for (MobileApp app : mobileApps) {
            if (driverName.equals(app.getDriverName())) {
                return app;
            }
        }
        return null;
    }

    public abstract void initApp(MobileDriver driverName);
    public abstract com.ringcentral.xmn.ta.application.model.ILoginPage getLoginPage();


}
