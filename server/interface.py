from tkinter import *
from tkinter.font import Font
from ipconf import get_ip
import server

# Init
app = Tk()
app.title("Phone Text Display")
app.geometry("600x350")
app_font = Font(family="Arial", size=14, weight="bold")
app.attributes("-topmost", True)
app.attributes("-alpha", 0.9)
app.configure(bg="black")
app.resizable(True, True)
fallback_ip = "..."


# Display Handler
def display_message(message):
    output_box.config(state=NORMAL)
    output_box.insert(END, f"> {message}\n")
    output_box.config(state=DISABLED)
    output_box.see(END)


# Refresh Button
def refresh_ip():
    ip_label.config(text="Device IP: " + fallback_ip)
    app.after(
        500, lambda: ip_label.config(text="Device IP: " + get_ip(), font=app_font)
    )


# Start Server Button
def start_server():
    server.start_server()
    ip_label.config(text="Device IP: " + get_ip(), font=app_font)
    refresh_button.config(state=NORMAL)
    start_button.config(state=DISABLED)
    stop_button.config(state=NORMAL)


# Stop Server Button
def stop_server():
    server.stop_server()
    output_box.config(state=NORMAL)
    output_box.insert(END, "> Server stopped.\n")
    output_box.config(state=DISABLED)
    refresh_button.config(state=DISABLED)
    start_button.config(state=NORMAL)
    stop_button.config(state=DISABLED)
    ip_label.config(text="Device IP: " + fallback_ip, font=app_font)


# Widgets
ip_label = Label(
    app, text="Device IP: " + fallback_ip, font=app_font, fg="white", bg="black"
)
ip_label.pack(pady=10)

output_box = Text(
    app,
    height=8,
    width=80,
    state=DISABLED,
    font=("Arial", 14),
    bg="black",
    fg="white",
)
output_box.pack(pady=5)

refresh_button = Button(
    app, text="Refresh", command=refresh_ip, bg="gray20", fg="white"
)
refresh_button.pack(pady=2)
refresh_button.config(state=DISABLED)

start_button = Button(
    app, text="Start Server", command=start_server, bg="green", fg="white"
)
start_button.pack(pady=2)

stop_button = Button(
    app, text="Stop Server", command=stop_server, state=DISABLED, bg="red", fg="black"
)
stop_button.pack(pady=2)

server.set_message_handler(display_message)

# Main loop
app.mainloop()
