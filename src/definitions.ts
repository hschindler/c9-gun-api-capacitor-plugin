export interface C9GunApiCapacitorPluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
