import { WebPlugin } from '@capacitor/core';

import type { C9GunApiCapacitorPlugin } from './definitions';

export class C9GunApiCapacitorPluginWeb extends WebPlugin implements C9GunApiCapacitorPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }

  async getFirmware(): Promise<{ firmware: string }> {
    // logic here
    this.throwUnimplementedError();
  }

  async startBarcodeInventory(): Promise<{ barcodeData: string }> {
    // logic here
    this.throwUnimplementedError();
  }

  async stopBarcodeInventory(): Promise<boolean> {
    // logic here
    this.throwUnimplementedError();
  }

  async startInventory(): Promise<{ uhfData: string[] }> {
    // logic here
    this.throwUnimplementedError();
  }

  async stopInventory(): Promise<boolean> {
    // logic here
    this.throwUnimplementedError();
  }

  async setOutputPower(): Promise<void> {
    // logic here
    this.throwUnimplementedError();
  }

  async getOutputPower(): Promise<number> {
    // logic here
    this.throwUnimplementedError();
  }

  async writeEPCToTagByEPC(): Promise<boolean> {
    // logic here
    this.throwUnimplementedError();
  }


  private throwUnimplementedError(): never {
    throw this.unimplemented('Not implemented on web.');
  }
}

// const C9GunApiCapacitorPlugin = new C9GunApiCapacitorPluginWeb();

// export { C9GunApiCapacitorPlugin };