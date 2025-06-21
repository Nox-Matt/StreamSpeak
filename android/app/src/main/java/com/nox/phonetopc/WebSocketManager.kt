package com.nox.phonetopc

import okhttp3.*

class WebSocketManager(private val url: String, private val listener: Listener) {
    interface Listener {
        fun onConnect()
        fun onDisconnect()
        fun onMessage(message: String)
        fun onError(error: String)
    }

    private val client = OkHttpClient()
    private var webSocket: WebSocket? = null

    fun connect() {
        val request = Request.Builder().url(url).build()
        webSocket = client.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                listener.onConnect()
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                listener.onMessage(text)
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                webSocket.close(1000, null)
                listener.onDisconnect()
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                listener.onError(t.message ?: "Unknown error")
            }
        })
    }

    fun disconnect() {
        webSocket?.close(1000, "Closed by user")
        listener.onDisconnect()
    }

    fun sendMessage(msg: String) {
        webSocket?.send(msg)
    }
}