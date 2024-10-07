'use strict';

var core = require('@capacitor/core');

const C9GunApiCapacitor = core.registerPlugin('C9GunApiCapacitorPlugin', {
    web: () => Promise.resolve().then(function () { return web; }).then(m => new m.C9GunApiCapacitorPluginWeb()),
});

class C9GunApiCapacitorPluginWeb extends core.WebPlugin {
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
    async setBarcodeTimeout() {
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
    async addListener(eventName, listenerFunc) {
        console.log('eventName:', eventName);
        console.log('listenerFunc:', listenerFunc);
        this.throwUnimplementedError();
    }
    throwUnimplementedError() {
        throw this.unimplemented('Not implemented on web.');
    }
}
// const C9GunApiCapacitorPlugin = new C9GunApiCapacitorPluginWeb();
// export { C9GunApiCapacitorPlugin };

var web = /*#__PURE__*/Object.freeze({
    __proto__: null,
    C9GunApiCapacitorPluginWeb: C9GunApiCapacitorPluginWeb
});

exports.C9GunApiCapacitor = C9GunApiCapacitor;
//# sourceMappingURL=plugin.cjs.js.map
