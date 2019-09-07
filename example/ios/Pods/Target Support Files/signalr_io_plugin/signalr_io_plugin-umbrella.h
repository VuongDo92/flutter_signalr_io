#ifdef __OBJC__
#import <UIKit/UIKit.h>
#else
#ifndef FOUNDATION_EXPORT
#if defined(__cplusplus)
#define FOUNDATION_EXPORT extern "C"
#else
#define FOUNDATION_EXPORT extern
#endif
#endif
#endif

#import "SignalrIoPlugin.h"

FOUNDATION_EXPORT double signalr_io_pluginVersionNumber;
FOUNDATION_EXPORT const unsigned char signalr_io_pluginVersionString[];

