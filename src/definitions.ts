import type { PluginListenerHandle } from '@capacitor/core';

export type ScanButtonPressedListener = () => void;

export interface C9GunApiCapacitorPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;

  /**
  * Gets RFID UHF reader firmware.
  *
  * @since 1.0.0
  */
  getFirmware(): Promise<{ firmware: string }>;

  /**
  * Starts Barcode inventory.
  *
  * @since 1.1.0
  */
  startBarcodeInventory(options: { value: string }): Promise<{ barcodeData: string }>;

  /**
  * Stops Barcode inventory.
  *
  * @since 1.1.0
  */
  stopBarcodeInventory(): Promise<boolean>;

  /**
  * Starts RFID UHF inventory.
  *
  * @since 1.0.0
  */
  startInventory(options: { value: string }): Promise<{ uhfData: string[] }>;

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
  setOutputPower(options: { value: number }): Promise<void>;

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
  writeEPCToTagByEPC(options: { filteredTagEPC: string, newEPC: string }): Promise<boolean>;


  /**
   * Listen for scanButtonPressed
   *
   * @since 1.0.0
   */
  addListener(
    eventName: 'scanButtonPressed' | 'barcodeReceived',
    listenerFunc: ScanButtonPressedListener,
  ): Promise<PluginListenerHandle> & PluginListenerHandle;
}
