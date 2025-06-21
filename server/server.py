# server.py
import asyncio
import websockets
import threading

clients = set()
loop = None
server = None
on_message = None


def set_message_handler(callback):
    global on_message
    on_message = callback


async def handler(websocket):
    print("Handler Online.")
    clients.add(websocket)
    try:
        async for message in websocket:
            print(f"Received: {message}")
            if on_message:
                loop.call_soon_threadsafe(on_message, message)
    except Exception as e:
        print(f"Error: {e}")
    finally:
        clients.remove(websocket)
        print("Shutting Down.")


def start_server():
    def run():
        global loop, server
        loop = asyncio.new_event_loop()
        asyncio.set_event_loop(loop)

        async def launch_server():
            return await websockets.serve(handler, "0.0.0.0", 8765)

        server = loop.run_until_complete(launch_server())
        print("WebSocket server started on port 8765.")
        if on_message:
            loop.call_soon_threadsafe(
                on_message, "If you can see this, it means the WebSocket is Online"
            )
        loop.run_forever()

    threading.Thread(target=run, daemon=True).start()


def stop_server():
    global loop, server
    print("Shutting down WebSocket server...")
    if loop and server:
        async def shutdown():
            server.close()
            await server.wait_closed()
            loop.stop()

        asyncio.run_coroutine_threadsafe(shutdown(), loop)
