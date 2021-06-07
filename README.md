# c9-gun-api-capacitor-plugin

C9 Gun Devices API Plugin

## Install

```bash
npm install c9-gun-api-capacitor-plugin
npx cap sync
```
## API dependencies info

*.so files have to copied in android/src/main/jniLibs/arm...
*.jar files have to copied to android/libs

## API

<docgen-index>

* [`echo(...)`](#echo)
* [`getFirmware()`](#getfirmware)
* [`startInventory(...)`](#startinventory)
* [`stopInventory()`](#stopinventory)
* [`setOutputPower(...)`](#setoutputpower)
* [`getOutputPower()`](#getoutputpower)
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


### startInventory(...)

```typescript
startInventory(options: { value: string; }) => any
```

Starts RFID UHF inventory.

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


### addListener(...)

```typescript
addListener(eventName: 'scanButtonPressed', listenerFunc: ScanButtonPressedListener) => Promise<PluginListenerHandle> & PluginListenerHandle
```

Listen for scanButtonPressed

| Param              | Type                             |
| ------------------ | -------------------------------- |
| **`eventName`**    | <code>"scanButtonPressed"</code> |
| **`listenerFunc`** | <code>() =&gt; void</code>       |

**Returns:** <code>any</code>

**Since:** 1.0.0

--------------------


### Interfaces


#### PluginListenerHandle

| Prop         | Type                      |
| ------------ | ------------------------- |
| **`remove`** | <code>() =&gt; any</code> |

</docgen-api>


