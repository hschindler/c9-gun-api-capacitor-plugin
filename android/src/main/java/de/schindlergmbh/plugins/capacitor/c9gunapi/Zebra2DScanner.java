package de.schindlergmbh.plugins.capacitor.c9gunapi;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Context;

import com.getcapacitor.Bridge;
import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;



public class Zebra2DScanner {

    /**
     * Open scan service
     */
    private final String ACTION_SCAN_INIT = "com.rfid.SCAN_INIT";
    /**
     * Scanning
     */
    private final String ACTION_SCAN = "com.rfid.SCAN_CMD";
    /**
     * Stop Scanning
     */
    private static final String ACTION_STOP_SCAN = "com.rfid.STOP_SCAN";
    /**
     * Close scan service
     */
    private final String ACTION_CLOSE_SCAN = "com.rfid.CLOSE_SCAN";
    /**
     * Scan result output mode, 0 -- BroadcastReceiver mode; 1 -- Focus input mode (default)
     */
    private final String ACTION_SET_SCAN_MODE = "com.rfid.SET_SCAN_MODE";
    /**
     * Scan timeout (Value:1000,2000,3000,4000,5000,6000,7000,8000,9000,10000)
     */
    private final String ACTION_SCAN_TIME = "com.rfid.SCAN_TIME";

    private Context _context;

    private HashSet<String> setBarcode = new HashSet<String>();
    private HashMap<String, Integer> mapBarcode = new HashMap<>();
    private List<Barcode> listBarcode;

    private Bridge _bridge;
    private String _callBackId;

    public Zebra2DScanner(Context context, Bridge bridge) {
        this._context = context;
        this._bridge = bridge;

        this.enableScanService();

        //Register receiver to receive the result of scan
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.rfid.SCAN");

        this._context.registerReceiver(this.receiver, filter);

    }



    /**
     * open scan service
     * @param context Context
     */
    private void enableScanService() {
        Intent intent = new Intent();
        intent.setAction(ACTION_SCAN_INIT);
        this._context.sendBroadcast(intent);

        this.setScanMode(0);

    }

    /**
     * Start Scanning
     */
    public void scan(String callBackId) {
        this._callBackId = callBackId;

        Intent intent = new Intent();
        intent.setAction(ACTION_SCAN);
        this._context.sendBroadcast(intent);
    }

    /**
     * Stop Scanning
     */
    public void stopScan() {
        Intent intent = new Intent();
        intent.setAction(ACTION_STOP_SCAN);
        this._context.sendBroadcast(intent);
    }

    /**
     * Set the scan result output mode
     * @param mode 0 -- BroadcastReceiver mode; 1 -- Focus input mode (default)
     */
    public void setScanMode(int mode) {
        Intent intent = new Intent();
        intent.setAction(ACTION_SET_SCAN_MODE);
        intent.putExtra("mode", mode);
        this._context.sendBroadcast(intent);
    }

    /**
     * Close scan service
     */
    public void close() {
        Intent toKillService = new Intent();
//        toKillService.putExtra("iscamera", true);
        toKillService.setAction(ACTION_CLOSE_SCAN);
        this._context.sendBroadcast(toKillService);
    }

    /**
     * Set scan timeout
     * @param timeout Value:1000,2000,3000,4000,5000(default),6000,7000,8000,9000,10000
     */
    public void setTimeout(String timeout){
        Intent intent = new Intent();
        intent.setAction(ACTION_SCAN_TIME);
        intent.putExtra("time", timeout);
        this._context.sendBroadcast(intent);
    }


    // BroadcastReceiver to receiver scan data
    private BroadcastReceiver receiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            byte[] data = intent.getByteArrayExtra("data");
            if (data != null) {
                //String barcode = Tools.Bytes2HexString(data, data.length);
                String barcode = new String(data);

                PluginCall call =_bridge.getSavedCall(_callBackId);

                if (call != null) {

                    JSObject ret = new JSObject();
                    ret.put("barcodeData", barcode);

                    call.resolve(ret);
                    _bridge.releaseCall(call);
                }

                /*
                //first add
                if (setBarcode.isEmpty()) {
                    setBarcode.add(barcode);
                    listBarcode = new ArrayList<>();
                    Barcode b = new Barcode();
                    b.sn = 1;
                    b.barcode = barcode;
                    b.count = 1;
                    listBarcode.add(b);
                    //list index
                    mapBarcode.put(barcode, 0);
                    //adapter = new MAdapter();
                    //lv.setAdapter(adapter);
                } else {
                    if (setBarcode.contains(barcode)) {
                        Barcode b = listBarcode.get(mapBarcode.get(barcode));
                        b.count += 1;
                        listBarcode.set(mapBarcode.get(barcode), b);

                    } else {
                        Barcode b = new Barcode();
                        b.sn = listBarcode.size();
                        b.barcode = barcode;
                        b.count = 1;
                        listBarcode.add(b);
                        setBarcode.add(barcode);
                        //list index
                        mapBarcode.put(barcode, listBarcode.size() - 1);
                    }
                }*/

                // _receiveCallback.onReceiveCallback("barcodeReceived", barcode);

            }

        }
    };
}
