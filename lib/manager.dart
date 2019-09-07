import 'dart:async';

import 'package:flutter/services.dart';
import 'package:signalr_io_plugin/socket.dart';

class SocketIOManager {
  static const MethodChannel _channel =
      const MethodChannel('signalr_io_plugin');

  Map<int, SocketIO> _sockets = {};

  ///Create a [SocketIO] instance
  ///[uri] - Socket Server URL
  ///[query] - Query params to send to server as a Map
  ///returns [SocketIO]
  Future<SocketIO> createInstance(String uri, String hubName,
      {Map<String, String> query,
      bool enableLogging: false,
      String path,
      bool forceWebsockets}) async {
    int index = await _channel.invokeMethod('newInstance', {
      'uri': uri,
      'hubName': hubName,
      'query': query,
      'enableLogging': enableLogging,
      'path': path,
      'forceWebsockets': forceWebsockets,
    });
    SocketIO socket = SocketIO(index);
    _sockets[index] = socket;
    return socket;
  }

  ///Disconnect a socket instance and remove from stored sockets list
  Future clearInstance(SocketIO socket) async {
    await _channel.invokeMethod('clearInstance', {'id': socket.id});
    _sockets.remove(socket.id);
  }
}
