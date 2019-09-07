import 'package:flutter/material.dart';
import 'package:signalr_io_plugin/signalr_io_plugin.dart';

void main() => runApp(MyApp());

const String URI = "https://beta.bittrex.com/signalr";
const String HUBNAME = "c2";

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  SocketIOManager manager;
  SocketIO socket;
  bool isProbablyConnected = false;

  @override
  void initState() {
    super.initState();
//    initPlatformState();
    manager = SocketIOManager();
    initSocket();
  }

  initSocket() async {
    setState(() => isProbablyConnected = true);
    socket = await manager.createInstance(
        //Socket IO server URI
        URI,
        HUBNAME,
        //Query params - can be used for authentication
        query: {
          "auth": "--SOME AUTH STRING---",
          "info": "new connection from adhara-socketio",
          "timestamp": DateTime.now().toString()
        },
        //Enable or disable platform channel logging
        enableLogging: false);

    socket.connect();

//    socket.onConnect((data) {
//      pprint("connected...");
//      pprint(data);
//      sendMessage();
//    });

//    socket.onConnectError(pprint);
//    socket.onConnectTimeout(pprint);
//    socket.onError(pprint);
//    socket.onDisconnect(pprint);
//    socket.on("news", (data) {
//      pprint("news");
//      pprint(data);
//    });
//    socket.connect();
  }

  pprint(data) {
//    setState(() {
//      if (data is Map) {
//        data = json.encode(data);
//      }
//      print(data);
//      toPrint.add(data);
//    });
  }

  // Platform messages are asynchronous, so we initialize in an async method.
//  Future<void> initPlatformState() async {
//    String platformVersion;
//    // Platform messages may fail, so we use a try/catch PlatformException.
//    try {
//      platformVersion = await SignalrIoPlugin.platformVersion;
//    } on PlatformException {
//      platformVersion = 'Failed to get platform version.';
//    }
//
//    // If the widget was removed from the tree while the asynchronous platform
//    // message was in flight, we want to discard the reply rather than calling
//    // setState to update our non-existent appearance.
//    if (!mounted) return;
//
//    setState(() {
//      _platformVersion = platformVersion;
//    });
//  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
          child: Text('Running on: $_platformVersion\n'),
        ),
      ),
    );
  }
}
