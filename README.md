# c9-gun-api-capacitor-plugin

C9 Gun Devices API Plugin

## Install

```bash
npm install https://github.com/hschindler/c9-gun-api-capacitor-plugin.git
npx cap sync
```
## API dependencies info

*.so files have to copied in android/src/main/jniLibs/arm...
*.jar files have to copied to android/libs

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`getFirmware()`](#getfirmware)
* [`startBarcodeInventory(...)`](#startbarcodeinventory)
* [`stopBarcodeInventory()`](#stopbarcodeinventory)
* [`setBarcodeTimeout(...)`](#setbarcodetimeout)
* [`startInventory(...)`](#startinventory)
* [`stopInventory()`](#stopinventory)
* [`setOutputPower(...)`](#setoutputpower)
* [`getOutputPower()`](#getoutputpower)
* [`writeEPCToTagByEPC(...)`](#writeepctotagbyepc)
* [`addListener('scanButtonPressed' | 'barcodeReceived', ...)`](#addlistenerscanbuttonpressed--barcodereceived)
* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => Promise<{ value: string; }>
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ value: string; }&gt;</code>

--------------------


### getFirmware()

```typescript
getFirmware() => Promise<{ firmware: string; }>
```

Gets RFID UHF reader firmware.

**Returns:** <code>Promise&lt;{ firmware: string; }&gt;</code>

**Since:** 1.0.0

--------------------


### startBarcodeInventory(...)

```typescript
startBarcodeInventory(options: { value: string; }) => Promise<{ barcodeData: string; }>
```

Starts Barcode inventory.
Param: options: { value: 'zebra' }

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ barcodeData: string; }&gt;</code>

**Since:** 1.1.0

--------------------


### stopBarcodeInventory()

```typescript
stopBarcodeInventory() => Promise<boolean>
```

Stops Barcode inventory.

**Returns:** <code>Promise&lt;boolean&gt;</code>

**Since:** 1.1.0

--------------------


### setBarcodeTimeout(...)

```typescript
setBarcodeTimeout(options: { timeout: number; }) => Promise<void>
```

Set Barcode timeout.
Value:1000,2000,3000,4000,5000(default),6000,7000,8000,9000,10000
Param: options: { timeout: number }

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ timeout: number; }</code> |

**Since:** 1.1.0

--------------------


### startInventory(...)

```typescript
startInventory(options: { value: string; }) => Promise<{ uhfData: string[]; }>
```

Starts RFID UHF inventory.
Param: options: { value: string }

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>Promise&lt;{ uhfData: string[]; }&gt;</code>

**Since:** 1.0.0

--------------------


### stopInventory()

```typescript
stopInventory() => Promise<boolean>
```

Stops RFID UHF inventory.

**Returns:** <code>Promise&lt;boolean&gt;</code>

**Since:** 1.0.0

--------------------


### setOutputPower(...)

```typescript
setOutputPower(options: { value: number; }) => Promise<void>
```

Sets RFID UHF output power.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: number; }</code> |

**Since:** 1.0.0

--------------------


### getOutputPower()

```typescript
getOutputPower() => Promise<number>
```

Sets RFID UHF output power.

**Returns:** <code>Promise&lt;number&gt;</code>

**Since:** 1.0.0

--------------------


### writeEPCToTagByEPC(...)

```typescript
writeEPCToTagByEPC(options: { filteredTagEPC: string; newEPC: string; }) => Promise<boolean>
```

Write EPC to Tag by selected EPC.

| Param         | Type                                                     |
| ------------- | -------------------------------------------------------- |
| **`options`** | <code>{ filteredTagEPC: string; newEPC: string; }</code> |

**Returns:** <code>Promise&lt;boolean&gt;</code>

**Since:** 1.0.0

--------------------


### addListener('scanButtonPressed' | 'barcodeReceived', ...)

```typescript
addListener(eventName: 'scanButtonPressed' | 'barcodeReceived', listenerFunc: ScanButtonPressedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for scanButtonPressed

| Param              | Type                                                                            |
| ------------------ | ------------------------------------------------------------------------------- |
| **`eventName`**    | <code>'scanButtonPressed' \| 'barcodeReceived'</code>                           |
| **`listenerFunc`** | <code><a href="#scanbuttonpressedlistener">ScanButtonPressedListener</a></code> |

**Returns:** <code>Promise&lt;<a href="#pluginlistenerhandle">PluginListenerHandle</a>&gt; & <a href="#pluginlistenerhandle">PluginListenerHandle</a></code>

**Since:** 1.0.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### ScanButtonPressedListener

<code>(keyCode: { value: string; }): void</code>

</docgen-api>


