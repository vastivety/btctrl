# btctrl

This app lets your Android phone act as a Bluetooth(R) mouse and keyboard.
Note that the necessary _Bluetooth HID device profile_ is not implemented in every phone,
even though it was officially introduced in Android P.


## Development progress

This app is under active development.

| Feature                          | State       |
| -------------------------------- | ----------- |
| List known Bluetooth connections | Done        |
| Pairing new devices              | Not started |
| Establishing a connection        | In progress |
| Bluetooth permission request     | Not started |
| Sending mouse frames             | Not started |
| Sending keyboard frames          | Not started |
| Trackpad input                   | Not started |
| System keyboard input            | Not started |


## Handling connection issues

If you are experiencing issues while trying to connect to a device,
try removing your phone from that device's list of known Bluetooth connections.
In case it still does not work, delete the connections on both your phone
and the other device, and newly pair them using this app.


## Acknowledgements

There are a few iterations of this concept already available.
The goal of this one is to follow the principles of open source,
to be well implemented and to stand out with a modern, easy to use UI.

The project builds mainly on two open sourced apps:
[ginkage/wearmouse](https://github.com/ginkage/wearmouse), which is a robust
implementation also contributing a few lines of code to this project and
[raghavk92/Kontroller](https://github.com/raghavk92/Kontroller), which served
as an initial reference for how the Android Bluetooth API can be used.
