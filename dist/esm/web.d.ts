import { PluginListenerHandle, WebPlugin } from '@capacitor/core';
import type { C9GunApiCapacitorPlugin, ScanButtonPressedListener } from './definitions';
export declare class C9GunApiCapacitorPluginWeb extends WebPlugin implements C9GunApiCapacitorPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    getFirmware(): Promise<{
        firmware: string;
    }>;
    startBarcodeInventory(): Promise<{
        barcodeData: string;
    }>;
    stopBarcodeInventory(): Promise<boolean>;
    setBarcodeTimeout(): Promise<void>;
    startInventory(): Promise<{
        uhfData: string[];
    }>;
    stopInventory(): Promise<boolean>;
    setOutputPower(): Promise<void>;
    getOutputPower(): Promise<number>;
    writeEPCToTagByEPC(): Promise<boolean>;
    addListener(eventName: 'scanButtonPressed' | 'barcodeReceived', listenerFunc: ScanButtonPressedListener): Promise<PluginListenerHandle>;
    private throwUnimplementedError;
}
