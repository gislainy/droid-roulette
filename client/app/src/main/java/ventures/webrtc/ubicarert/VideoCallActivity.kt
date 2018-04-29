package ventures.webrtc.ubicarert
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.webrtc.DataChannel
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import ventures.webrtc.ubicarert.webrtc.*
import java.nio.ByteBuffer

class VideoCallActivity :  AppCompatActivity() {

    private var dataChannelSession: DataChannelSession? = null;
    private var localTextView: TextView? = null
    private var remoteTextView: TextView? = null
    private var statusConnection: TextView? = null
    private var channel: DataChannel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_message)
        localTextView = findViewById(R.id.localTextView)
        remoteTextView = findViewById(R.id.remoteTextView)
        statusConnection = findViewById(R.id.statusConnection)

        val send : Button = findViewById(R.id.sendButton)
        send.setOnClickListener {
            sendMessage()
        }

        val hangup : Button = findViewById(R.id.finish)
        hangup.setOnClickListener {
            finish()
        }

        startVideoSession()
    }

    override fun onDestroy() {
        super.onDestroy()

        dataChannelSession?.terminate()


    }

    private fun onStatusChanged(newStatus: CallStatus) {
        Log.d(TAG,"New call status: $newStatus")
        runOnUiThread {
            when(newStatus) {
                CallStatus.FINISHED -> finish()
                else -> {
                    statusConnection?.text = resources.getString(newStatus.label)
                    statusConnection?.setTextColor(ContextCompat.getColor(this, newStatus.color))
                }
            }
        }
    }

    private fun onMesasge(string: String) {
        runOnUiThread {
            val textRemote = remoteTextView?.text
            remoteTextView?.text = string
        }
    }
    private fun sendMessage() {
        val textLocal = localTextView?.text;
        if (channel?.state() == DataChannel.State.OPEN) {
            val buffer = ByteBuffer.wrap(textLocal.toString().toByteArray())
            channel?.send(DataChannel.Buffer(buffer, false))
        }
    }
    private fun onSendCb(chan: DataChannel?) {
        if(chan != null) channel = chan;
    }
    private fun startVideoSession() {
        dataChannelSession = DataChannelSession.connect(this, BACKEND_URL, this::onMesasge, this::onSendCb, this::onStatusChanged)

    }



    companion object {
        private val CAMERA_AUDIO_PERMISSION_REQUEST = 1
        private val TAG = "VideoCallActivity"
        private val BACKEND_URL = "ws://192.168.15.8:4433/" // Change HOST to your server's IP if you want to test
    }
}
