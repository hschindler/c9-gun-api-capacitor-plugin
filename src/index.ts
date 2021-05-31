import { registerPlugin } from '@capacitor/core';

import type { C9GunApiCapacitorPluginPlugin } from './definitions';

const C9GunApiCapacitorPlugin = registerPlugin<C9GunApiCapacitorPluginPlugin>('C9GunApiCapacitorPlugin', {
  web: () => import('./web').then(m => new m.C9GunApiCapacitorPluginWeb()),
});

export * from './definitions';
export { C9GunApiCapacitorPlugin };
