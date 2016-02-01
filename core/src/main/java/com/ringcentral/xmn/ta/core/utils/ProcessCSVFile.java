package com.ringcentral.xmn.ta.core.utils;

import com.google.common.collect.Lists;
import static com.google.common.io.Files.readLines;
import com.ringcentral.xmn.ta.core.data.DeviceInfo;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;

public class ProcessCSVFile {
    private static final Charset UTF8 = Charset.forName("UTF-8");
    private final File devicePoolFile;

    public ProcessCSVFile(File devicePoolFile) {
        this.devicePoolFile = devicePoolFile;
    }

    public List<DeviceInfo> processDevicePoolFile() throws IOException {
        List<String> linesOfDeviceInfo = readLines(devicePoolFile, UTF8);
        List<DeviceInfo> deviceInfoList = Lists.newArrayList();

        for (String deviceInfo : linesOfDeviceInfo) {
            String[] deviceDetails = deviceInfo.split(",");
            DeviceInfo info = new DeviceInfo();
            if (deviceDetails.length == 4) {
                info.setUDID(deviceDetails[0]);
                info.setPlatformVersion(deviceDetails[1]);
                info.setDeviceName(deviceDetails[2]);
                info.setDeviceIP(deviceDetails[3]);
                deviceInfoList.add(info);
            }
        }
        return deviceInfoList;
    }

}

