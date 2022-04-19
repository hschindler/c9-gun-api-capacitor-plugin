import Foundation
import Capacitor

/**
 * Please read the Capacitor iOS Plugin Development Guide
 * here: https://capacitorjs.com/docs/plugins/ios
 */
@objc(C9GunApiCapacitorPluginPlugin)
public class C9GunApiCapacitorPluginPlugin: CAPPlugin {
    private let implementation = C9GunApiCapacitorPlugin()

    @objc func echo(_ call: CAPPluginCall) {
        let value = call.getString("value") ?? ""
        call.resolve([
            "value": implementation.echo(value)
        ])
    }
}
