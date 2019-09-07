#import "SignalrIoPlugin.h"
#import <signalr_io_plugin/signalr_io_plugin-Swift.h>

@implementation SignalrIoPlugin
+ (void)registerWithRegistrar:(NSObject<FlutterPluginRegistrar>*)registrar {
  [SwiftSignalrIoPlugin registerWithRegistrar:registrar];
}
@end
