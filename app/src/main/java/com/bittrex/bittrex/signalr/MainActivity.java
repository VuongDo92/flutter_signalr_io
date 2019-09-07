package com.bittrex.bittrex.signalr;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import microsoft.aspnet.signalr.client.Action;
import microsoft.aspnet.signalr.client.ErrorCallback;
import microsoft.aspnet.signalr.client.LogLevel;
import microsoft.aspnet.signalr.client.Logger;
import microsoft.aspnet.signalr.client.Platform;
import microsoft.aspnet.signalr.client.SignalRFuture;
import microsoft.aspnet.signalr.client.http.android.AndroidPlatformComponent;
import microsoft.aspnet.signalr.client.hubs.HubConnection;
import microsoft.aspnet.signalr.client.hubs.HubProxy;
import microsoft.aspnet.signalr.client.hubs.SubscriptionHandler1;
import microsoft.aspnet.signalr.client.transport.ClientTransport;
import microsoft.aspnet.signalr.client.transport.ServerSentEventsTransport;

public class MainActivity extends AppCompatActivity {

    private static final String HOST = "https://beta.bittrex.com/signalr";
    private static final String HUB_NAME = "c2";
    private static final String ON_uE_METHOD_NAME = "uE";
    private static final String ON_uS_METHOD_NAME = "uS";

    private static final String TAG = "_main_activity_";

    private HubConnection mHubConnection;
    private HubProxy mHubProxy;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * LOGGER TRACE OF SIGNALR
         * */
        Logger logger = new Logger() {
            @Override
            public void log(String message, LogLevel level) {
                Log.d("HUB_CONNECTION", message);
            }
        };

        /**
         * START CONNECTION
         * */
        Platform.loadPlatformComponent(new AndroidPlatformComponent());
        mHubConnection = new HubConnection(HOST, "", true, logger);

        mHubProxy = mHubConnection.createHubProxy(HUB_NAME);

        ClientTransport clientTransport = new ServerSentEventsTransport(mHubConnection.getLogger());
        SignalRFuture<Void> awaitConnecttion = mHubConnection.start(clientTransport);

        awaitConnecttion.done(new Action<Void>() {
            @Override
            public void run(Void obj) throws Exception {
                // Hub connected
                Log.d(TAG, "run: await_connection");
            }
        }).onError(new ErrorCallback() {
            @Override
            public void onError(Throwable error) {
                // Connection Error
                Log.d(TAG, "error: await_connection ERROR");
            }
        }).onCancelled(new Runnable() {
            @Override
            public void run() {
                // Connection Canceled
                Log.d(TAG, "Canceled: await_connection CANCELED");
            }
        });

        /**
         *  SUBCRIBE FOR CLIENT ANDROID
         * */
        mHubProxy.on(ON_uE_METHOD_NAME, new SubscriptionHandler1<String>() {
            @Override
            public void run(String p1) {
                Log.d(TAG, "run: ON_uE_METHOD_NAME with uE = " + p1);
            }
        }, String.class);

//        mHubProxy.on(ON_uS_METHOD_NAME, new SubscriptionHandler3<String, String, String>() {
//            @Override
//            public void run(String p1, String p2, String p3) {
//
//            }
//        }, String.class, String.class, String.class);

        /**
         * PUSH DATA TO SERVER
         * */
//        hub.server.invoke('SubscribeToSummaryDeltas')
        mHubProxy.invoke("SubscribeToSummaryDeltas");

        setContentView(R.layout.activity_main);
    }
}
