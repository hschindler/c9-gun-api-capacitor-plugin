import { registerPlugin } from '@capacitor/core';
const C9GunApiCapacitor = registerPlugin('C9GunApiCapacitorPlugin', {
    web: () => import('./web').then(m => new m.C9GunApiCapacitorPluginWeb()),
});
export * from './definitions';
export { C9GunApiCapacitor };
//# sourceMappingURL=index.js.map