package com.github.habibu.hostspotmanager;

import android.app.Activity;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;



public  class MainActivity extends Activity {
    TextView testVScan;
    WifiApManager wifiApManager;
    WifiManager wifiManager;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        testVScan = findViewById(R.id.textScan);

        wifiApManager = new WifiApManager(this);

        // force to show the settings page for demonstration purpose of this method
        wifiApManager.showWritePermissionSettings(true);

        scan();
    }

    @Override
    protected void onResume() {
        super.onResume();


        wifiApManager.showWritePermissionSettings(false);
        //testVScan.setText(" ");
    }
    /*
    @Override
    protected void onRestart(){
        super.onRestart();

        testVScan.setText(null);
    }
    */

    private void scan() {
        wifiApManager.getClientList(false, new FinishScanListener() {

            @Override
            public void onFinishScan(final ArrayList<ClientScanResult> clients) {

                // Adding Wifi Signal Properties
                wifiManager = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();

                //testVScan.setText("Wifi AP State: " + wifiApManager.getWifiApState() + "\n\n");

                //testVScan.append("Clients: \n");

                if(clients.size() <= 5) {
                    for (ClientScanResult clientScanResult : clients) {

                        /* Out of requirements: to be use in the signal analysis

                        testVScan.append("isReachable: " + clientScanResult.isReachable() + "\n");
                        testVScan.append("IpAddr: " + clientScanResult.getIpAddr() + "\n");

                        */
                        testVScan.append("Connected Device "+"\n");
                        testVScan.append("Device Name: " + clientScanResult.getDevice() + "\n");
                        testVScan.append("Device Address: " + clientScanResult.getHWAddr() + "\n");

                        testVScan.append("\nDevice Wifi Data" +"\n");
                        testVScan.append("Link Speed: " + wifiInfo.getLinkSpeed() + "\n");
                        testVScan.append("RSSI: " + wifiInfo.getRssi() + "\n");
                    }
                }else{
                    Toast.makeText(getApplicationContext(),"Error Maximum number of devices is 5", Toast.LENGTH_LONG).show();
                }

            }
        });
        //onRestart();
    }

    /*
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(0, 0, 0, "Get Clients");
        menu.add(0, 1, 0, "Open AP");
        menu.add(0, 2, 0, "Close AP");
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                scan();
                break;
            case 1:
                wifiApManager.setWifiApEnabled(null, true);
                break;
            case 2:
                wifiApManager.setWifiApEnabled(null, false);
                break;
        }

        return super.onMenuItemSelected(featureId, item);
    }
    */
}
