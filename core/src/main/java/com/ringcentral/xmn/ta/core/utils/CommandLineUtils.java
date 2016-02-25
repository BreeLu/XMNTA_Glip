package com.ringcentral.xmn.ta.core.utils;

import com.trilead.ssh2.Connection;
import com.trilead.ssh2.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.List;

public class CommandLineUtils {
    private static final Logger LOG = LoggerFactory.getLogger("CORELog");

    public static void SSHCommand(String hostName, String username, String password, String... commandList) {
        Connection connection = new Connection(hostName);
        boolean isAuthenticated;
        try {
            connection.connect();
            isAuthenticated = connection.authenticateWithPassword(username, password);
            if (!isAuthenticated) {
                throw new IOException("Authentication failed.");
            }
            if (commandList != null) {
                if (commandList.length == 0) {
                    throw new IllegalArgumentException("blank commands");
                } else {
                    for (int i = 0; i < commandList.length; i++) {
                        Session session = connection.openSession();
                        session.execCommand(commandList[i]);
                        session.close();
                        Thread.sleep(3000);
                    }
                }
            }
            connection.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static List<String> runtimeCommand(String shStr) throws Exception {
        List<String> strList = new ArrayList<>();

        Process process;
        process = Runtime.getRuntime().exec(new String[]{"/bin/sh", "-c", shStr}, null, null);
        InputStreamReader ir = new InputStreamReader(process
                .getInputStream());
        LineNumberReader input = new LineNumberReader(ir);
        String line;
        process.waitFor();
        while ((line = input.readLine()) != null) {
            if (!line.contains("List of devices attached") && !"".equals(line)) {
                strList.add(line.replace("\tdevice", ""));
            }
        }
        LOG.info("number of attached devices is "+ strList.size());

        return strList;
    }

    public static void main(String[] args) throws Exception {
        runtimeCommand("adb devices");
    }


}
