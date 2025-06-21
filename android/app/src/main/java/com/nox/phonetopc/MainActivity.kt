package com.nox.phonetopc

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.nox.phonetopc.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), WebSocketManager.Listener {
    private lateinit var binding: ActivityMainBinding
    private var wsManager: WebSocketManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.connectButton.setOnClickListener {
            val url = "ws://" + binding.ipInput.text.toString() + ":8765"
            if (url.isNotEmpty()) {
                wsManager = WebSocketManager(url, this)
                wsManager?.connect()
            }
            binding.disconnectButton.isEnabled = true
        }
        binding.sendButton.setOnClickListener {
            val msg = binding.messageInput.text.toString()
            if (msg.isNotEmpty()) {
                wsManager?.sendMessage(msg)
                binding.messageLog.append("< $msg\n")
                scrollToBottom()
                binding.messageInput.text.clear()
            }
        }
        binding.disconnectButton.setOnClickListener{
            wsManager?.disconnect()
            binding.disconnectButton.isEnabled = false
        }
    }
    private fun scrollToBottom() {
        binding.messageLog.post {
            binding.messageLog.layout?.let {
                val scrollAmount = binding.messageLog.layout.getLineTop(binding.messageLog.lineCount) - binding.messageLog.height
                if (scrollAmount > 0)
                    binding.messageLog.scrollTo(0, scrollAmount)
                else
                    binding.messageLog.scrollTo(0, 0)
            }
        }
    }


    override fun onConnect() {
        runOnUiThread {
            binding.statusValue.text = "Connected"
            binding.statusValue.setTextColor(ContextCompat.getColor(this, R.color.green))
            binding.connectButton.isEnabled = false
            binding.sendButton.isEnabled = true
            binding.messageLog.append("[Connected to server]\n")
            scrollToBottom()
        }
    }

    override fun onDisconnect() {
        runOnUiThread {
            binding.statusValue.text = "Disconnected"
            binding.connectButton.isEnabled = true
            binding.sendButton.isEnabled = false
            binding.messageLog.append("[Disconnected from server]\n")
            scrollToBottom()
        }
    }

    override fun onMessage(message: String) {
        runOnUiThread {
            binding.messageLog.append("> $message\n")
            scrollToBottom()
        }
    }

    override fun onError(error: String) {
        runOnUiThread {
            binding.statusValue.text = "Error: $error"
            binding.statusValue.setTextColor(ContextCompat.getColor(this, R.color.red))
            binding.messageLog.append("[Error: $error]\n")
            scrollToBottom()
        }
    }
}