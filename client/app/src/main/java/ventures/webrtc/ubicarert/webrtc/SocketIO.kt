//package ventures.webrtc.ubicarert.webrtc
//import android.app.PendingIntent.getActivity
//import android.util.Log
//import com.github.nkzawa.emitter.Emitter
//import com.github.nkzawa.socketio.client.IO;
//import com.github.nkzawa.socketio.client.Socket;
//import okhttp3.OkHttpClient
//import okhttp3.Request
//import org.json.JSONObject
//import java.net.URISyntaxException
//
//enum class MessageType(val value: String) {
//    SDPMessage("sdp"),
//    ICEMessage("ice"),
//    MatchMessage("match"),
//    PeerLeft("peer-left"),
//    MonitorMessage("monitor"),
//    LocatorMessage("locator");
//
//    override fun toString() = value
//}
//open class ClientMessage(val type: MessageType)
//
//data class SDPMessage(val sdp: String) : ClientMessage(MessageType.SDPMessage)
//data class ICEMessage(val label: Int, val id: String, val candidate: String) : ClientMessage(MessageType.ICEMessage)
//data class LocatorMessage(val permission: String) : ClientMessage(MessageType.LocatorMessage)
//data class MonitorMessage(val permission: String) : ClientMessage(MessageType.MonitorMessage)
//data class MatchMessage(val match: String, val offer: Boolean) : ClientMessage(MessageType.MatchMessage)
//class PeerLeft : ClientMessage(MessageType.PeerLeft)
//
//class SignalingSocketIO {
//    private var socket: Socket? = null;
//    private fun createSocket(url: String) {
//        try {
//            socket = IO.socket(url);
//
//        } catch (e: URISyntaxException) {
//        }
//
//    }
//    companion object {
//        fun connect(url: String) {
//            val client = OkHttpClient()
//            val request = Request.Builder().url(url).build()
////            Log.i(TAG, "Connecting to $url")
////            client.newWebSocket(request, websocketHandler)
////            client.dispatcher().executorService().shutdown()
//        }
//
//        private val TAG = "DataChannelSession"
//
//    }
//}