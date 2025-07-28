package poly.cafe.util;

import java.io.*;
import java.net.*;
import java.util.*;

public class InternetChecker extends Thread{
    private volatile boolean running = true;
    private final Runnable onConnectionLost;

    public InternetChecker(Runnable onConnectionLost) {
        this.onConnectionLost = onConnectionLost;
    }

    public void stopChecking() {
        running = false;
    }


    @Override
    public void run() {
        while (running) {
            if (!isInternetAvailable()) {
                onConnectionLost.run();
                break;
            }
            try {
                Thread.sleep(3000); // kiểm tra mỗi 3 giây
            } catch (InterruptedException e) {
                break;
            }
        }
    }
    /**
     * Kiểm tra xem có Internet thực sự hay không (ping Google).
     */
    public static boolean isInternetAvailable() {
        try {
            URL url = new URL("http://www.google.com");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("HEAD");
            connection.setConnectTimeout(3000);
            connection.connect();
            return (connection.getResponseCode() == 200);
        } catch (IOException e) {
            return false;
        }
    }

    /**
     * Lấy tên mạng Wi-Fi hiện tại đang kết nối (SSID).
     */
    public static String getConnectedSSID() {
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netsh wlan show interfaces");
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;

            while ((line = reader.readLine()) != null) {
                if (line.trim().startsWith("SSID") && !line.contains("BSSID")) {
                    return line.substring(line.indexOf(":") + 1).trim();
                }
            }

        } catch (IOException e) {
            return null;
        }
        return null;
    }

    /**
     * Lấy danh sách tất cả các Wi-Fi khả dụng trên máy.
     */
    public static List<String> getAllWifiStatus() {
        List<String> wifiList = new ArrayList<>();
        String connectedSSID = getConnectedSSID();
        boolean hasInternet = isInternetAvailable();

        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "netsh wlan show networks mode=bssid");
            Process process = builder.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
            String line;
            String currentSSID = null;
            Set<String> listedSSIDs = new HashSet<>();

            while ((line = reader.readLine()) != null) {
                line = line.trim();

                if (line.startsWith("SSID")) {
                    int idx = line.indexOf(" : ");
                    if (idx > 0) {
                        currentSSID = line.substring(idx + 3).trim();
                        if (!currentSSID.isEmpty() && !listedSSIDs.contains(currentSSID)) {
                            listedSSIDs.add(currentSSID);

                            if (currentSSID.equals(connectedSSID)) {
                                String status = hasInternet ? "✅ " : "⚠️ ";
                                wifiList.add(status + currentSSID + " - Đã kết nối " +
                                        (hasInternet ? "(Có Internet)" : "(Không có Internet)"));
                            } else {
                                wifiList.add("❌ " + currentSSID + " - Chưa kết nối");
                            }
                        }
                    }
                }
            }

        } catch (IOException e) {
            wifiList.add("⚠️ Không thể lấy danh sách Wi-Fi: " + e.getMessage());
        }

        return wifiList;
    }

    /**
     * Hiển thị danh sách Wi-Fi ra console.
     */
    public static void showWifiStatus() {
        List<String> wifiList = getAllWifiStatus();
        for (String wifi : wifiList) {
            System.out.println(wifi);
        }
    }

    // Main thử nghiệm
    public static void main(String[] args) {
        showWifiStatus();
    }
}

