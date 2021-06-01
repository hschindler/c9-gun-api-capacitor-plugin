package de.schindlergmbh.plugins.capacitor.c9gunapi;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;
import android.os.Handler;
import android.content.Intent;
import android.content.IntentFilter;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

import cn.pda.serialport.SerialPort;
import cn.pda.serialport.Tools;

import com.handheld.UHF.UhfManager;

@CapacitorPlugin(name = "C9GunApiCapacitorPlugin")
public class C9GunApiCapacitorPlugin extends Plugin {

    private static final String TAG = C9GunApiCapacitorPlugin.class.getName();
    
    private UhfManager _uhfManager;
    private Boolean _readerInitialized;
    private String _errorLog;
    private boolean startFlag = false;
    

    private String _readMode = "tid"; // tid / epc

    private int _outputPower = 0; // 26, 24, 20, 18, 17, 16
    /*
     private static int WorkArea_China2 = 1;
     private static int WorkArea_USA = 2;
     private static int WorkArea_Europe = 3;
     private static int WorkArea_China1 = 4;
     private static int WorkArea_Korea = 6;
    */
    
    private Thread _scanThread;


    private KeyReceiver _keyReceiver;

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

        byte[] firmwareVersion = this._uhfManager.getFirmware();

        String returnVersion = "";

        if (firmwareVersion == null) {
            returnVersion = "get version is null";
        } else {
            Log.d(TAG, "firmwareVersion " + new String(firmwareVersion));
            returnVersion = new String(firmwareVersion);
        }     
        
        this.disposeUHFManager();

        JSObject ret = new JSObject();
        ret.put("firmware", returnVersion);
        call.resolve(ret);
    }

    @PluginMethod()
    public void startInventory(PluginCall call) {
       
        Log.d(TAG, "startInventory");
        
        String value = call.getString("value", "tid");

        Log.d(TAG, "startInventory value=" + value);
        
        if (value.equals("tid") || value.equals("epc")) {
            this._readMode = value;
        }
        
        saveCall(call);

        this.StartInventoryThread();
        
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
        String value = call.getString("value");

        this._outputPower = Integer.parseInt(value);

        JSObject ret = new JSObject();
        ret.put("value", this._outputPower);
        call.resolve(ret);
    }

    @PluginMethod()
    public void getOutputPower(PluginCall call) {   

        JSObject ret = new JSObject();
        ret.put("value", this._outputPower);
        call.resolve(ret);
    }


    private void dispose() {
        this.StopInventoryThread();
        this.disposeUHFManager();

        // if (getContext().)
        // getContext().unregisterReceiver(this._keyReceiver);

        this._keyReceiver = null;

    }
   


    private void initializeUHFManager() {
        Log.d(TAG, "initializeUHFManager C9GunApiCordovaPlugin");
        if (this._uhfManager == null) {

            try {
                this._uhfManager = UhfManager.getInstance();
                
                this._uhfManager.setWorkArea(UhfManager.WorkArea_Europe);
                
                if (this._outputPower > 0) {
                    this._uhfManager.setOutputPower(this._outputPower);
                } else {
                    this._uhfManager.setOutputPower(26); // 16-26
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


    class CallBackImpl implements KeyReceiverCallback {          //class that implements the method to callback defined in the interface
        public void onReceiveCallback() {
            System.out.println("I've been called back");
            JSObject ret = new JSObject();
            // ret.put("value", "some value");
            notifyListeners("scanButtonPressed", ret);
        }
    }


    private void initKeyReceiver() {

        Log.d(TAG, "initKeyReceiver");

        if (this._keyReceiver == null) {
            Log.d(TAG, "createReceiver");    
            this._keyReceiver = new KeyReceiver(getContext(),  new CallBackImpl());
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


    private void StartInventoryThread() {

        Log.d(TAG, "StartInventoryThread");

        // start inventory thread
        startFlag = true;

        if (this._scanThread == null || this._scanThread.getState() == Thread.State.TERMINATED) {
            Log.d(TAG, "StartInventoryThread - create new thread");
            this._scanThread = new InventoryThread();
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

        private ArrayList<String> dataList;

        @Override
        public void run() {
            super.run();

            Log.d(TAG, "InventoryThread starting...");

            PluginCall savedCall = getSavedCall();

            if (savedCall == null) {
                Log.d("Test", "No stored plugin call for startInventory request result");
                return;
            }

            initializeUHFManager();

            if (_uhfManager == null) {
                Log.d(TAG, "InventoryThread failed creating uhfManager");
                savedCall.reject("InventoryThread failed creating uhfManager");
                return;
            }

            Log.d(TAG, "InventoryThread startflag = " + String.valueOf(startFlag));

            while (startFlag) {

                Log.d(TAG, "Waiting for timeout..");

                if (_uhfManager != null) {

                    if ("tid".equals(_readMode)) {

                        Log.d(TAG, "ReadMode is TID...");

                        try {
                            dataList = new ArrayList<String>();

                            byte[] data = _uhfManager.readFrom6C(UhfManager.TID, 0, 6,
                                    new byte[4]);

                            if (data != null && data.length > 1) { 

                                String dataStr = Tools.Bytes2HexString(data, data.length);
                                Log.d(TAG, "TID found:" + dataStr);

                                dataList.add(dataStr);
                                
                            } else {
                                
                                if (data != null) {
                                    Log.d(TAG, "GetTID Read data fail,Error code: " + (data[0] & 0xff));
                                    return;
                                }

                                Log.d(TAG, "GetTID Read data fail");
                            }
                        } catch (Exception ex) {
                            Log.e(TAG, "GetTID Exception: " + ex.getMessage());
                            savedCall.reject("Fehler-GetTID: " + ex.getMessage());
                        }

                    } else if ("epc".equals(_readMode)) {
                        List<byte[]> epcList;

                        try {

                            dataList = new ArrayList<String>();

                            epcList = _uhfManager.inventoryRealTime(); 

                            if (epcList != null && !epcList.isEmpty()) {

                                for (byte[] epc : epcList) {

                                    String epcStr = Tools.Bytes2HexString(epc,
                                            epc.length);

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

                if ((dataList != null) && (!dataList.isEmpty())) {
                    if (dataList.size() > 0) {
                        returnCurrentTIDs(dataList, savedCall);
                        startFlag = false;
                    }
                }

                // epcList = null;
                dataList = null;

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


        } // run

    } // end class InventoryThread
}
