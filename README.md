# 📢 StreamSpeak

**StreamSpeak** lets you type messages from your phone to your PC over LAN, making it perfect for streamers who want to "speak" through text when they can’t use their mic or if you have any physical disabilites that does not allowed you to speak.

## ✨ Features

- Send text from your Android phone to your PC over local Wi-Fi
- Messages appear on the PC so you can display them in OBS or screen-share
- Lightweight and easy to use – no server hosting needed

## 🚀 How to Run

1. **PC Side**
   - Download the `.exe` from [Releases](https://github.com/Nox-Matt/StreamSpeak/releases) or run the Python script directly if you have Python 3 installed:
     ```bash
     python interface.py
     ```

2. **Phone Side**
   - Download the `StreamSpeak.apk` from [Releases](https://github.com/Nox-Matt/StreamSpeak/releases)
   - Install it on your Android device

3. **Connect**
   - On the PC app, click **Start Server** – it will display your LAN IP.
   - On your phone, input that IP and tap **Connect**.
   - You can now send messages from your phone to your PC in real-time!

## ⚠️ Notes

- This tool works **only on local networks (LAN)**.
- No HTTPS or WSS – it’s intentionally kept simple and unencrypted.
- Built in just one day, not intended for production environments.
- For OBS integration, you can capture the PC window showing the messages.

## 📁 Files to Include

- `interface.py` (or packaged `.exe`)
- `server.py` if applicable
- `StreamSpeak.apk`
- `README.md`

## 👤 Author

Created by **Nox-Matt**

## 📝 License

This project is licensed under the [MIT License](LICENSE).
