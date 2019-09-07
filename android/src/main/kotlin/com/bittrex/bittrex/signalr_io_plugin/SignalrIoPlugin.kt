package com.bittrex.bittrex.signalr_io_plugin

import android.util.Log
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
import io.flutter.plugin.common.PluginRegistry.Registrar
import java.net.URISyntaxException

class SignalrIoPlugin : MethodCallHandler {

    private var instances: MutableList<SignalrIo>
    private val registrar: Registrar

    private constructor(registrar: Registrar) {
        this.instances = mutableListOf<SignalrIo>()
        this.registrar = registrar
    }


    companion object {
        @JvmStatic
        fun registerWith(registrar: Registrar) {
            val channel = MethodChannel(registrar.messenger(), "signalr_io_plugin")
            channel.setMethodCallHandler(SignalrIoPlugin(registrar))
        }
    }

    override fun onMethodCall(call: MethodCall, result: Result) {

        var signalrIo: SignalrIo? = null
        if (call.hasArgument("id")) {
            val socketIndex = call.argument<Int>("id")
            if (socketIndex != null) {
                if (instances.size > socketIndex) {
                    signalrIo = instances[socketIndex]
                }
            }
        }
        when (call.method) {
            "newInstance" -> {
                try {
                    val newIndex = instances.size
                    val uri: String = (call.argument<Any>("uri") as String?).toString()
                    val hubName: String  = (call.argument<Any>("hubName") as String?).toString()
                    Log.d("TAG:::", "uri: " + uri)
                    Log.d("TAG:::", "hubName: " + hubName)
                    val options = SignalrIo.Options(newIndex, uri, hubName)
                    
//                    if (call.hasArgument("query")) {
//                        val _query = call.argument<Map<String, String>>("query")
//                        if (_query != null) {
//                            val sb = StringBuilder()
//                            for ((key, value) in _query) {
//                                sb.append(key)
//                                sb.append("=")
//                                sb.append(value)
//                                sb.append("&")
//                            }
//                            options.query = sb.toString()
//                        }
//                    }
//                    if (call.hasArgument("path") && call.argument<Any>("path") != null) {
//                        options.path = call.argument<Any>("path") as String
//                    }
                    if (call.hasArgument("enableLogging")) {
                        options.enableLogging = call.argument<Any>("enableLogging") as Boolean?
                    }
                    // if(call.hasArgument("forceWebsockets")){
                    //     options.forceWebsockets = call.argument("forceWebsockets");
                    // }
                    this.instances.add(SignalrIo.registerWith(registrar, options))
                    result.success(newIndex)
                } catch (use: URISyntaxException) {
                    result.error(use.toString(), null, null)
                }

            }
            "clearInstance" -> {
//              if (signalrIo == null) {
//                  result.error("Invalid instance identifier provided", null, null)
//                  return
//              }
//              this.instances.remove(signalrIo)
//              adharaSocket.socket.disconnect()
//              result.success(null)
            }
            else -> {
                result.notImplemented()
            }
        }

//    if (call.method == "getPlatformVersion") {
//      result.success("Android ${android.os.Build.VERSION.RELEASE}")
//    } else {
//      result.notImplemented()
//    }
    }
}
