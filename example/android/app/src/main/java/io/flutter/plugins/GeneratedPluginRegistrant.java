package io.flutter.plugins;

import io.flutter.plugin.common.PluginRegistry;
import com.bittrex.bittrex.signalr_io_plugin.SignalrIoPlugin;

/**
 * Generated file. Do not edit.
 */
public final class GeneratedPluginRegistrant {
  public static void registerWith(PluginRegistry registry) {
    if (alreadyRegisteredWith(registry)) {
      return;
    }
    SignalrIoPlugin.registerWith(registry.registrarFor("com.bittrex.bittrex.signalr_io_plugin.SignalrIoPlugin"));
  }

  private static boolean alreadyRegisteredWith(PluginRegistry registry) {
    final String key = GeneratedPluginRegistrant.class.getCanonicalName();
    if (registry.hasPlugin(key)) {
      return true;
    }
    registry.registrarFor(key);
    return false;
  }
}
