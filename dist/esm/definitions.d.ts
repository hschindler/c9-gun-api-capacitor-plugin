import type { PluginListenerHandle } from '@capacitor/core';
export type ScanButtonPressedListener = (keyCode: {
    value: string;
}) => void;
export interface C9GunApiCapacitorPlugin {
    echo(options: {
        value: string;
    }): Promise<{
        value: string;
    }>;
    /**
     * Gets RFID UHF reader firmware.
     *
     * @since 1.0.0
     */
    getFirmware(): Promise<{
        firmware: string;
    }>;
    /**
     * Starts Barcode inventory.
     * Param: options: { value: 'zebra' }
     * @since 1.1.0
     */
    startBarcodeInventory(options: {
        value: string;
    }): Promise<{
        barcodeData: string;
    }>;
    /**
     * Stops Barcode inventory.
     *
     * @since 1.1.0
     */
    stopBarcodeInventory(): Promise<boolean>;
    /**
     * Set Barcode timeout.
     * Value:1000,2000,3000,4000,5000(default),6000,7000,8000,9000,10000
     * Param: options: { timeout: number }
     *
     * @since 1.1.0
     */
    setBarcodeTimeout(options: {
        timeout: number;
    }): Promise<void>;
    /**
     * Starts RFID UHF inventory.
     * Param: options: { value: string }
     *
     * @since 1.0.0
     */
    startInventory(options: {
        value: string;
    }): Promise<{
        uhfData: string[];
    }>;
    /**
     * Stops RFID UHF inventory.
     *
     * @since 1.0.0
     */
    stopInventory(): Promise<boolean>;
    /**
     * Sets RFID UHF output power.
     *
     * @since 1.0.0
     */
    setOutputPower(options: {
        value: number;
    }): Promise<void>;
    /**
     * Sets RFID UHF output power.
     *
     * @since 1.0.0
     */
    getOutputPower(): Promise<number>;
    /**
     * Write EPC to Tag by selected EPC.
     *
     * @since 1.0.0
     */
    writeEPCToTagByEPC(options: {
        filteredTagEPC: string;
        newEPC: string;
    }): Promise<boolean>;
    /**
     * Listen for scanButtonPressed
     *
     * @since 1.0.0
     */
    addListener(eventName: 'scanButtonPressed' | 'barcodeReceived', listenerFunc: ScanButtonPressedListener): Promise<PluginListenerHandle>;
}
