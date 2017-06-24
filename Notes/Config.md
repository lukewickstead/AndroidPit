Add the platform tools to the bash path.

```bash
PATH=$PATH{}:/home/luke/Android/Sdk/platform-tools
PATH=$PATH{}:/home/luke/Android/Sdk/tools
 ```

Start and kill the adb.

```bash
adb kill-server
adb start-server 
 ```
 
 
 You can get more debug information when running the emulator through the terminal. Often hardware acceleration crashes the emulator.
 
 ```bash
emulator -avd Nexus-5X-API25-64 -netspeed full -netdelay none
```Sunday, 18. June 2017 11:09am 


The following error occurred after upgrading Android Studio.

> [139786785425216]:ERROR:./android/qt/qt_setup.cpp:28:Qt library not found at ../emulator/lib64/qt/lib
Could not launch '../emulator/qemu/linux-x86_64/qemu-system-x86_64': No such file or directory

There are two emulators.

/home/luke/Android/Sdk/emulator/emulator

and 
/home/luke/Android/Sdk/tools/emulator.

Looks like the later should not work
