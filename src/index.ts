import { registerPlugin } from '@capacitor/core';

import type { C9GunApiCapacitorPlugin } from './definitions';

const C9GunApiCapacitor = registerPlugin<C9GunApiCapacitorPlugin>('C9GunApiCapacitorPlugin', {
  web: () => import('./web').then(m => new m.C9GunApiCapacitorPluginWeb()),
});

export * from './definitions';
export { C9GunApiCapacitor };
