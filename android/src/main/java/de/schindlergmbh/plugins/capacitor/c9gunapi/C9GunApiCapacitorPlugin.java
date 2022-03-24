package de.schindlergmbh.plugins.capacitor.c9gunapi;

import static java.util.Optional.ofNullable;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import android.util.Log;
import android.content.IntentFilter;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import cn.pda.serialport.Tools;

import com.uhf.api.cls.Reader.TAGINFO;
import com.uhf.api.cls.Reader;
import com.handheld.uhfr.UHFRManager;

@CapacitorPlugin(name = "C9GunApiCapacitorPlugin")
public class C9GunApiCapacitorPlugin extends Plugin {

    private static final String TAG = C9GunApiCapacitorPlugin.class.getName();

    private UHFRManager _uhfManager;
    private Boolean _readerInitialized;
    private String _errorLog;
    private boolean startFlag = false;

    private String _readMode = "tid"; // tid / epc

    private int _outputPower = 0; // 0-33

    private Thread _scanThread;

    private KeyReceiver _keyReceiver;

    private Zebra2DScanner _zebraScanner;

    public void load() {

        Log.d(TAG, "C9GunApiCapacitorPlugin - load");

        this.initKeyReceiver();

    }

    @Override
    protected void handleOnDestroy() {
        this.dispose();
    }

    @PluginMethod
    public void exitApp(PluginCall call) {

    }

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", value);
        call.resolve(ret);
    }

    @PluginMethod()
    public void getFirmware(PluginCall call) {

        Log.d(TAG, "getFirmware");

        this.initializeUHFManager();

        if (_uhfManager == null) {
            call.reject("UHF API not installed");
            return;
        }

        String firmwareVersion = this._uhfManager.getHardware();

        Log.d(TAG, "firmwareVersion");

        String returnVersion = "";

        if (firmwareVersion == null) {
            returnVersion = "get version is null";
        } else {
            Log.d(TAG, "firmwareVersion " + new String(firmwareVersion));
            returnVersion = firmwareVersion;
        }

        this.disposeUHFManager();

        JSObject ret = new JSObject();
        ret.put("firmware", returnVersion);
        call.resolve(ret);
    }

    @PluginMethod()
    public void startBarcodeInventory(PluginCall call) {

        Log.d(TAG, "startBarcodeInventory");

        String value = call.getString("value", "zebra");

        Log.d(TAG, "startBarcodeInventory value=" + value);

        if (value.equals("zebra")) {
            // register Intent
            if (this._zebraScanner == null) {
                Log.d(TAG, "createReceiver");
                this._zebraScanner = new Zebra2DScanner(getContext(), bridge);
            }

            bridge.saveCall(call);

            this._zebraScanner.scan(call.getCallbackId());

        }
    }

    @PluginMethod()
    public void stopBarcodeInventory(PluginCall call) {

        Boolean result = true;

        if (this._zebraScanner != null) {
            this._zebraScanner.stopScan();
        }

        call.resolve();

    }

    /**
     * Set scan timeout
     * 
     * @param timeout Value:1000,2000,3000,4000,5000(default),6000,7000,8000,9000,10000
     */
    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setBarcodeTimeout(PluginCall call) {

        Log.d(TAG, "setBarcodeTimeout");

        Integer value = call.getInt("timeout", 5000);

        Log.d(TAG, "setBarcodeTimeout - " + value);

        if (this._zebraScanner == null) {
            Log.d(TAG, "createReceiver");
            this._zebraScanner = new Zebra2DScanner(getContext(), bridge);
        }

        if (this._zebraScanner != null & value != null) {
            if (value >= 1000 & value <= 10000) {
                this._zebraScanner.setTimeout(value.toString());
            }
        }

        call.resolve();
    }

    @PluginMethod()
    public void startInventory(PluginCall call) {

        Log.d(TAG, "startInventory");

        String value = call.getString("value", "tid");

        Log.d(TAG, "startInventory value=" + value);

        if (value.equals("tid") || value.equals("epc")) {
            this._readMode = value;
        }

        bridge.saveCall(call);

        this.StartInventoryThread(call.getCallbackId());

    }

    @PluginMethod()
    public void stopInventory(PluginCall call) {

        Boolean result = true;

        this.StopInventoryThread();

        JSObject ret = new JSObject();
        ret.put("value", result);
        call.resolve(ret);

    }

    @PluginMethod(returnType = PluginMethod.RETURN_NONE)
    public void setOutputPower(PluginCall call) {
        // 0-30
        Integer value = call.getInt("value");

        this._outputPower = value; // Integer.parseInt(value);

        call.resolve();
    }

    @PluginMethod()
    public void getOutputPower(PluginCall call) {

        JSObject ret = new JSObject();
        ret.put("value", this._outputPower);
        call.resolve(ret);
    }

    @PluginMethod()
    public void writeEPCToTagByEPC(PluginCall call) {
        Log.d(TAG, "writeEPCToTagByEPC");

        String filteredTagEPC = call.getString("filteredTagEPC", "");
        String newEPC = call.getString("newEPC", "");

        Log.d(TAG, "writeEPCToTagByEPC filteredTagEPC=" + filteredTagEPC.trim());
        Log.d(TAG, "writeEPCToTagByEPC newEPC=" + newEPC.trim());

        this.initializeUHFManager();

        byte[] epcBytes = Tools.HexString2Bytes(filteredTagEPC.trim());
        byte[] accessBytes = Tools.HexString2Bytes("00000000");

        byte[] newEPCBytes = null;

        try {
            newEPCBytes = Tools.HexString2Bytes(newEPC.trim());
            if (newEPCBytes.length % 2 != 0) {
                call.reject("Write EPC to Tag failed! New EPC has wrong format!");
                return;
            }
        } catch (Exception e) {
            call.reject("Write EPC to Tag failed! New EPC has wrong format!");
            return;
        }

        Reader.READER_ERR err = this._uhfManager.setPower(20, 20);

        if (err != Reader.READER_ERR.MT_OK_ERR) {
            call.reject("Write EPC to Tag failed! Set write power failed!");
            return;
        }

        // err = this._uhfManager.writeTagEPC(newEPCBytes, accessBytes, (short) 1000);
        err = this._uhfManager.writeTagEPCByFilter(newEPCBytes, accessBytes, (short) 1000, epcBytes, 1, 2, true);

        if (err == Reader.READER_ERR.MT_OK_ERR) {
            JSObject ret = new JSObject();
            ret.put("value", true);
            call.resolve(ret);
        } else {
            Log.e(TAG, "Write EPC to Tag failed!" + err);
            call.reject("Write EPC to Tag failed!");
        }

    }

    private void dispose() {
        this.StopInventoryThread();
        this.disposeUHFManager();
        this.disposeZebraBarcodeScanner();

        // if (getContext().)
        // getContext().unregisterReceiver(this._keyReceiver);

        this._keyReceiver = null;

    }

    private void initializeUHFManager() {
        Log.d(TAG, "initializeUHFManager C9GunApiCordovaPlugin");
        if (this._uhfManager == null) {

            try {
                // UhfManager.Port = 13;

                this._uhfManager = UHFRManager.getInstance();

                if (this._uhfManager == null) {
                    Log.d(TAG, "initializeUHFManager getInstance failed");
                    return;
                }

                Reader.READER_ERR err = this._uhfManager.setPower(30, 30);
                Log.d(TAG, "initializeUHFManager setPower = " + err);

                // Reader.Region_Conf.RG_EU2 , Reader.Region_Conf.RG_EU3
                err = this._uhfManager.setRegion(Reader.Region_Conf.RG_EU3);
                Log.d(TAG, "initializeUHFManager setWorkArea = " + err);

                if (err == Reader.READER_ERR.MT_OK_ERR) {
                    boolean powerResult = false;

                    if (this._outputPower > 0) {
                        err = this._uhfManager.setPower(this._outputPower, 20);
                        Log.d(TAG, "initializeUHFManager setOutputPower = " + err);
                    } else {
                        err = this._uhfManager.setPower(26, 20); // 0-33
                        Log.d(TAG, "initializeUHFManager setOutputPower = " + err);
                    }
                } else {
                    Log.d(TAG, "initializeUHFManager failed");
                    this._uhfManager = null;
                }

            } catch (Exception e) {
                _errorLog = e.getMessage();
                e.printStackTrace();
                // Log.d(TAG, "Error: " + e.getMessage());
            }

        }

    }

    private void disposeUHFManager() {

        if (this._uhfManager != null) {
            Log.d(TAG, "disposeUHFManager");

            try {
                this._uhfManager.close();
            } catch (Exception e) {
                _errorLog = e.getMessage();
            }

            this._uhfManager = null;
        }
    }

    private void disposeZebraBarcodeScanner() {

        if (this._zebraScanner != null) {
            Log.d(TAG, "disposeZebraBarcodeScanner");

            try {
                this._zebraScanner.stopScan();
                this._zebraScanner.close();
            } catch (Exception e) {
                _errorLog = e.getMessage();
            }

            this._zebraScanner = null;
        }
    }

    class CallBackImpl implements ReceiverCallback { // class that implements the method to callback defined in the
                                                     // interface
        public void onReceiveCallback(String eventName, String value) {
            System.out.println("I've been called back");

            Optional<String> ln = ofNullable(value);

            JSObject ret = new JSObject();
            ret.put("value", value);
            notifyListeners(eventName, ret);
        }
    }

    private void initKeyReceiver() {

        Log.d(TAG, "initKeyReceiver");

        if (this._keyReceiver == null) {
            Log.d(TAG, "createReceiver");
            this._keyReceiver = new KeyReceiver(getContext(), new CallBackImpl());
        } else {
            // Log.d(TAG, "search receiver");
            // var existingReceiver= getContext().registerReceiver(null, filter);

            Log.d(TAG, "unregisterReceiver");

            getContext().unregisterReceiver(this._keyReceiver);
        }

        IntentFilter filter = new IntentFilter();
        filter.addAction("android.rfid.FUN_KEY");

        getContext().registerReceiver(this._keyReceiver, filter);

    }

    private void unregisterReceiver() {
        try {
            getContext().unregisterReceiver(this._keyReceiver);
        } catch (Exception e) {

        }

    }

    private void StartInventoryThread(String callBackId) {

        Log.d(TAG, "StartInventoryThread");

        // start inventory thread
        startFlag = true;

        if (this._scanThread == null || this._scanThread.getState() == Thread.State.TERMINATED) {
            Log.d(TAG, "StartInventoryThread - create new thread");
            this._scanThread = new InventoryThread(callBackId);
        }

        Log.d(TAG, "StartInventoryThread - start thread");

        if (this._scanThread.getState() == Thread.State.NEW) {
            this._scanThread.start();
        }

    }

    private void StopInventoryThread() {
        // runFlag = false;
        startFlag = false;
    }

    private void PauseInventoryThread() {
        startFlag = false;
    }

    private JSONArray ConvertArrayList(ArrayList<String> list) {
        org.json.JSONArray jsonArray = new org.json.JSONArray();
        for (String value : list) {
            jsonArray.put(value);
        }

        return jsonArray;
    }

    // add TIDs to view
    private void returnCurrentTIDs(final ArrayList<String> tidList, PluginCall call) {
        if (call != null) {
            if (tidList != null || tidList.isEmpty() == false) {
                JSObject ret = new JSObject();
                ret.put("uhfData", ConvertArrayList(tidList));
                call.resolve(ret);
            }

        }
    }

    /**
     * Inventory Thread
     */
    class InventoryThread extends Thread {

        // private List<byte[]> epcList;
        // private ArrayList<String> tidList;
        private List<TAGINFO> epcList;
        private ArrayList<String> dataList;
        private String _callBackId;

        public InventoryThread(String callBackId) {
            this._callBackId = callBackId;
        }

        @Override
        public void run() {
            super.run();

            Log.d(TAG, "InventoryThread starting...");

            PluginCall savedCall = bridge.getSavedCall(this._callBackId);

            if (savedCall == null) {
                Log.d("Test", "No stored plugin call for startInventory request result");
                return;
            }

            initializeUHFManager();

            if (_uhfManager == null) {
                Log.d(TAG, "InventoryThread failed creating UhfManager");
                savedCall.reject("InventoryThread failed creating UhfManager");
                return;
            }

            Log.d(TAG, "InventoryThread startflag = " + String.valueOf(startFlag));

            while (startFlag) {

                Log.d(TAG, "Waiting for timeout..");

                if (_uhfManager != null) {

                    Log.d(TAG, "starting inventoryRealTime...");

                    // epcList = _uhfManager.tagInventoryRealTime();

                    if ("tid".equals(_readMode)) {

                        Log.d(TAG, "ReadMode is TID...");

                        epcList = _uhfManager.tagEpcTidInventoryByTimer((short) 50);

                        try {

                            if (epcList != null && !epcList.isEmpty()) {

                                Log.d(TAG, "Found Tags...");

                                dataList = new ArrayList<String>();

                                for (TAGINFO tagInfo : epcList) {
                                    byte[] epcdata = tagInfo.EpcId;

                                    String tidStr = Tools.Bytes2HexString(tagInfo.EmbededData, tagInfo.EmbededDatalen);

                                    dataList.add(tidStr);

                                }

                            } else {
                                if (epcList == null) {
                                    Log.e(TAG, "epcList is null");
                                } else {
                                    Log.e(TAG, "epcList size + " + epcList.size());
                                }

                            }

                        } catch (Exception ex) {
                            Log.e(TAG, "GetTID Exception: " + ex.getMessage());
                            savedCall.reject("Fehler-GetTID: " + ex.getMessage());
                        }

                    } else if ("epc".equals(_readMode)) {

                        try {

                            dataList = new ArrayList<String>();

                            epcList = _uhfManager.tagInventoryByTimer((short) 50);

                            if (epcList != null && !epcList.isEmpty()) {

                                for (TAGINFO tagInfo : epcList) {
                                    byte[] epcdata = tagInfo.EpcId;
                                    String epcStr = Tools.Bytes2HexString(epcdata,
                                            epcdata.length);

                                    dataList.add(epcStr);
                                }
                            }

                        } catch (Exception ex) {
                            Log.e(TAG, "GetEPC Exception: " + ex.getMessage());
                            savedCall.reject("Fehler-GetEPC: " + ex.getMessage());
                        }

                    }

                } else {
                    // returnCurrentTIDs(null);
                    savedCall.reject("UhfManager is not initialized!");
                }

                startFlag = false;

                epcList = null;

                try {
                    Thread.sleep(40);
                } catch (InterruptedException e) {
                    // Thread.currentThread().interrupt();
                    e.printStackTrace();
                    // return;
                }

                // }
            } // while

            Log.d(TAG, "InventoryThread is closing...");

            disposeUHFManager();

            if ((dataList != null) && (!dataList.isEmpty())) {
                if (dataList.size() > 0) {
                    returnCurrentTIDs(dataList, savedCall);
                }

                dataList = null;
            }

        } // run

        // // first select tag by epc
        // private byte[] GetTID(PluginCall call) {
        // // Parameters: int memBank store RESEVER zone 0, EPC District 1, TID District
        // 2,
        // // USER District 3;
        // // int startAddr starting address (not too large, depending on the size of
        // the
        // // data area);
        // // int length read data length, in units of word (1word = 2bytes); byte []
        // // accessPassword password 4 bytes
        // int tidLength = 6; // in word 1 word = 2 byte
        // // byte[] tid; // = new byte[tidLength*2];

        // if (_uhfManager == null) {
        // return null;
        // }

        // Log.d(TAG, "GetTID");

        // try {
        // byte[] pw = new byte[4];
        // byte[] tid = _uhfManager.readFrom6C(2, 0, tidLength, pw);

        // if (tid != null && tid.length > 1) {

        // Log.d(TAG, "GetTID - " + tid);
        // return tid;

        // } else {
        // if (tid != null) {
        // // tid has error code

        // // try again with small tid (8 byte)
        // tidLength = 4;
        // tid = _uhfManager.readFrom6C(2, 0, tidLength, pw);

        // if (tid != null && tid.length > 1) {
        // return tid;
        // } else {
        // // tid has error code
        // if (tid != null) {
        // Log.d(TAG, "Fehler-GetTID tid error code: " + Tools.Bytes2HexString(tid,
        // tid.length));
        // call.reject("Fehler-GetTID tid error code: " + Tools.Bytes2HexString(tid,
        // tid.length));
        // } else {
        // Log.d(TAG, "Fehler-GetTID tid no error code");
        // call.reject("Fehler-GetTID tid no error code");
        // }

        // return null;
        // }
        // }

        // return null;
        // }

        // } catch (Exception ex) {

        // call.reject("Fehler-GetTID: " + ex.getMessage());

        // }

        // return null;
        // }

    } // end class InventoryThread
}
