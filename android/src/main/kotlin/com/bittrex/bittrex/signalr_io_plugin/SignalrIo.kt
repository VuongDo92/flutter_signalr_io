package com.bittrex.bittrex.signalr_io_plugin

import android.content.ContentValues.TAG
import android.util.Log
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.PluginRegistry
import microsoft.aspnet.signalr.client.Logger
import microsoft.aspnet.signalr.client.Platform
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent
import microsoft.aspnet.signalr.client.hubs.HubConnection
import microsoft.aspnet.signalr.client.hubs.HubProxy
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport

class SignalrIo : MethodChannel.MethodCallHandler {


    private val channel: MethodChannel
    private val options: Options

    var logger = Logger { message, level -> Log.d("HUB_CONNECTION", message) }

//    fun log() {
//        /**
//         * LOGGER TRACE OF SIGNALR
//         * */
//        logger    
//    }

    private var mHubConnection: HubConnection? = null
    private var mHubProxy: HubProxy? = null

    private constructor(channel: MethodChannel, options: Options) {
        this.channel = channel
        this.options = options
        Platform.loadPlatformComponent(AndroidPlatformComponent())
        this.mHubConnection = HubConnection(options.uri, "", true, logger)
        this.mHubProxy = this.mHubConnection?.createHubProxy(options.hubName)
    }

    companion object {
        @JvmStatic
        fun registerWith(registrar: PluginRegistry.Registrar, options: Options): SignalrIo {
            Platform.loadPlatformComponent(AndroidPlatformComponent())
            val channel = MethodChannel(registrar.messenger(), "signalr_io_plugin:socket:" + options.index.toString())
            val _signalr = SignalrIo(channel, options)
            channel.setMethodCallHandler(_signalr)
            return _signalr
        }
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {

        when (call.method) {
            "connect" -> {
                Log.d("TAG:::", "connect kotlin")
                val clientTransport = ServerSentEventsTransport(mHubConnection?.logger)
                val awaitConnecttion = mHubConnection?.start(clientTransport)

                awaitConnecttion?.done {
                    // Hub connected
                    Log.d(TAG, "run: await_connection")
                }?.onError {
                    // Connection Error
                    Log.d(TAG, "error: await_connection ERROR")
                }?.onCancelled {
                    // Connection Canceled
                    Log.d(TAG, "Canceled: await_connection CANCELED")
                }

            }
            "on" -> {

            }
            "off" -> {

            }
            "emit" -> {

            }
            "isConnected" -> {

            }
            "disconnect" -> {

            }
            else -> {
                result.notImplemented()
            }
        }
    }

    class Options internal constructor(internal var index: Int, internal var uri: String, internal var hubName: String) {
        var enableLogging: Boolean? = false
    }
}