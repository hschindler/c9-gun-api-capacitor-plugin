import { WebPlugin } from '@capacitor/core';

import type { C9GunApiCapacitorPluginPlugin } from './definitions';

export class C9GunApiCapacitorPluginWeb extends WebPlugin implements C9GunApiCapacitorPluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
