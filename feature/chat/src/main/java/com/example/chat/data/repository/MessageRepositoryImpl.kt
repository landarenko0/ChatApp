package com.example.chat.data.repository

import android.util.Log
import com.example.auth.entities.User
import com.example.chat.data.entities.MessageApi
import com.example.chat.data.entities.mappers.toDomain
import com.example.chat.domain.entities.Message
import com.example.chat.domain.repository.MessageRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import java.util.concurrent.TimeUnit
import javax.inject.Inject

internal class MessageRepositoryImpl @Inject constructor() : MessageRepository {

    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _incomingMessages = MutableSharedFlow<Message>(extraBufferCapacity = 64)
    override val messages: Flow<Message> = _incomingMessages.asSharedFlow()

    private val sendQueue = Channel<String>(capacity = Channel.UNLIMITED)

    private lateinit var webSocket: WebSocket
    private var isConnected = false

    private val client = OkHttpClient.Builder()
        .readTimeout(0, TimeUnit.MILLISECONDS)
        .build()

    private val request = Request.Builder()
        .url(HOST)
        .build()

    init {
        startWebSocket()
        startSendLoop()
    }

    private fun startWebSocket() {
        client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(ws: WebSocket, response: Response) {
                super.onOpen(ws, response)

                webSocket = ws
                isConnected = true
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                super.onMessage(webSocket, text)

                scope.launch {
                    val message = Json.decodeFromString<MessageApi>(text).toDomain()
                    _incomingMessages.emit(message)
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                super.onFailure(webSocket, t, response)

                isConnected = false
                reconnectWithDelay()
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                super.onClosed(webSocket, code, reason)

                isConnected = false
                reconnectWithDelay()
            }
        })
    }

    private fun reconnectWithDelay() {
        scope.launch {
            delay(3000)
            startWebSocket()
        }
    }

    private fun startSendLoop() {
        scope.launch {
            for (message in sendQueue) {
                if (isConnected) {
                    webSocket.send(message)
                }
            }
        }
    }

    override suspend fun sendMessage(message: String, user: User): Unit = withContext(Dispatchers.IO) {
        Log.d("MessageRepositoryImpl", "Send message: $message")
        val messageJson = Json.encodeToString(MessageApi(user.id, user.name, message))
        //sendQueue.send(messageJson)
        webSocket.send(messageJson)
    }

    override fun closeConnection() {
        runCatching {
            isConnected = false
            sendQueue.close()
            webSocket.close(1000, "Closed by client")
            client.dispatcher.executorService.shutdown()
        }
    }

    companion object {
        private const val HOST = "ws://192.168.1.107:3826/chat"
    }
}