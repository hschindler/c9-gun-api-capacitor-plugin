import { WebPlugin } from '@capacitor/core';
export class C9GunApiCapacitorPluginWeb extends WebPlugin {
    async echo(options) {
        console.log('ECHO', options);
        return options;
    }
    async getFirmware() {
        // logic here
        this.throwUnimplementedError();
    }
    async startBarcodeInventory() {
        // logic here
        this.throwUnimplementedError();
    }
    async stopBarcodeInventory() {
        // logic here
        this.throwUnimplementedError();
    }
    async startInventory() {
        // logic here
        this.throwUnimplementedError();
    }
    async stopInventory() {
        // logic here
        this.throwUnimplementedError();
    }
    async setOutputPower() {
        // logic here
        this.throwUnimplementedError();
    }
    async getOutputPower() {
        // logic here
        this.throwUnimplementedError();
    }
    async writeEPCToTagByEPC() {
        // logic here
        this.throwUnimplementedError();
    }
    throwUnimplementedError() {
        throw this.unimplemented('Not implemented on web.');
    }
}
// const C9GunApiCapacitorPlugin = new C9GunApiCapacitorPluginWeb();
// export { C9GunApiCapacitorPlugin };
//# sourceMappingURL=web.js.map