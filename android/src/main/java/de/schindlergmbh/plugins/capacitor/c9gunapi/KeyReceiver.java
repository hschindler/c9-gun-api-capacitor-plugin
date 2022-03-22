package de.schindlergmbh.plugins.capacitor.c9gunapi;

import android.nfc.Tag;
import android.content.BroadcastReceiver;
import android.view.KeyEvent;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;
// import android.widget.Toast;

import com.getcapacitor.NativePlugin;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

class KeyReceiver extends BroadcastReceiver {

    private final String TAG = KeyReceiver.class.getName();
    // private Toast toast;
    private Context _context;
    private ReceiverCallback _onReceiveCallback;

    public KeyReceiver(Context context, ReceiverCallback onReceiveCallback) {
        this._context = context;
        this._onReceiveCallback = onReceiveCallback;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        int keyCode = intent.getIntExtra("keyCode", 0);

        if (keyCode == 0) {// ����H941
            keyCode = intent.getIntExtra("keycode", 0);
        }

        boolean keyDown = intent.getBooleanExtra("keydown", false);

        if (keyDown) {
            Log.d(TAG, "keyCode = " + keyCode);
            // Log.d(TAG, "KeyEvent.KEYCODE_F4 = " + KeyEvent.KEYCODE_F4);
            // Log.d(TAG, "KeyEvent.KEYCODE_F5 = " + KeyEvent.KEYCODE_F5);
            // this.showToast("KeyReceiver:keyCode = " + keyCode);

            switch (keyCode) {
                case KeyEvent.KEYCODE_F1:

                    break;
                case KeyEvent.KEYCODE_F2:

                    break;
                case KeyEvent.KEYCODE_F3:
                    break;
                
                case KeyEvent.KEYCODE_F4:
                    this._onReceiveCallback.onReceiveCallback("scanButtonPressed", null);
                    // this.showToast("scan clicked");
                    // PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, "scan clicked");
                    // pluginResult.setKeepCallback(true);
                    // _scanKeyCallBackContext.sendPluginResult(pluginResult);
        
                    break;
                case KeyEvent.KEYCODE_F5:

                    break;
            }
        }

    }

    // private void showToast(String text) {
    //     if (toast == null) {
    //         toast = Toast.makeText(this._context, text,
    //                 Toast.LENGTH_SHORT);
    //     } else {
    //         toast.setText(text);
    //     }

    //     toast.show();
    // }
}