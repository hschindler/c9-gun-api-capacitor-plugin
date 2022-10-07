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
* [`addListener(...)`](#addlistener)
* [Interfaces](#interfaces)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

### echo(...)

```typescript
echo(options: { value: string; }) => any
```

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

--------------------


### getFirmware()

```typescript
getFirmware() => any
```

Gets RFID UHF reader firmware.

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### startBarcodeInventory(...)

```typescript
startBarcodeInventory(options: { value: string; }) => any
```

Starts Barcode inventory.
Param: options: { value: 'zebra' }

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

**Since:** 1.1.0

--------------------


### stopBarcodeInventory()

```typescript
stopBarcodeInventory() => any
```

Stops Barcode inventory.

**Returns:** <code>any</code>

**Since:** 1.1.0

--------------------


### setBarcodeTimeout(...)

```typescript
setBarcodeTimeout(options: { timeout: number; }) => any
```

Set Barcode timeout.
Value:1000,2000,3000,4000,5000(default),6000,7000,8000,9000,10000
Param: options: { timeout: number }

| Param         | Type                              |
| ------------- | --------------------------------- |
| **`options`** | <code>{ timeout: number; }</code> |

**Returns:** <code>any</code>

**Since:** 1.1.0

--------------------


### startInventory(...)

```typescript
startInventory(options: { value: string; }) => any
```

Starts RFID UHF inventory.
Param: options: { value: string }

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: string; }</code> |

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### stopInventory()

```typescript
stopInventory() => any
```

Stops RFID UHF inventory.

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### setOutputPower(...)

```typescript
setOutputPower(options: { value: number; }) => any
```

Sets RFID UHF output power.

| Param         | Type                            |
| ------------- | ------------------------------- |
| **`options`** | <code>{ value: number; }</code> |

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### getOutputPower()

```typescript
getOutputPower() => any
```

Sets RFID UHF output power.

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### writeEPCToTagByEPC(...)

```typescript
writeEPCToTagByEPC(options: { filteredTagEPC: string; newEPC: string; }) => any
```

Write EPC to Tag by selected EPC.

| Param         | Type                                                     |
| ------------- | -------------------------------------------------------- |
| **`options`** | <code>{ filteredTagEPC: string; newEPC: string; }</code> |

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### addListener(...)

```typescript
addListener(eventName: 'scanButtonPressed' | 'barcodeReceived', listenerFunc: ScanButtonPressedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for scanButtonPressed

| Param              | Type                                                  |
| ------------------ | ----------------------------------------------------- |
| **`eventName`**    | <code>"scanButtonPressed" \| "barcodeReceived"</code> |
| **`listenerFunc`** | <code>(keyCode: { value: string; }) =&gt; void</code> |

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                      |
| ------------ | ------------------------- |
| **`remove`** | <code>() =&gt; any</code> |

</docgen-api>


