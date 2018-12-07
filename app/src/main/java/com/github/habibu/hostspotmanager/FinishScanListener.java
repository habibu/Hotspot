package com.github.habibu.hostspotmanager;

import java.util.ArrayList;

public interface FinishScanListener {

    void onFinishScan(ArrayList<ClientScanResult> clients);
}
