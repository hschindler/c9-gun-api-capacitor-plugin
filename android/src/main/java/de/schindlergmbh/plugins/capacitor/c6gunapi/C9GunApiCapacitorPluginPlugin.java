package de.schindlergmbh.plugins.capacitor.c6gunapi;

import com.getcapacitor.JSObject;
import com.getcapacitor.Plugin;
import com.getcapacitor.PluginCall;
import com.getcapacitor.PluginMethod;
import com.getcapacitor.annotation.CapacitorPlugin;

@CapacitorPlugin(name = "C9GunApiCapacitorPlugin")
public class C9GunApiCapacitorPluginPlugin extends Plugin {

    private C9GunApiCapacitorPlugin implementation = new C9GunApiCapacitorPlugin();

    @PluginMethod
    public void echo(PluginCall call) {
        String value = call.getString("value");

        JSObject ret = new JSObject();
        ret.put("value", implementation.echo(value));
        call.resolve(ret);
    }
}
