package ventures.webrtc.ubicarert
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import org.webrtc.DataChannel
import org.webrtc.PeerConnection
import org.webrtc.RendererCommon
import org.webrtc.SurfaceViewRenderer
import ventures.webrtc.ubicarert.webrtc.*
import java.nio.ByteBuffer
import java.time.LocalDateTime
import java.util.*
import java.util.*
import kotlin.concurrent.schedule

class VideoCallActivity :  AppCompatActivity() {

    private var dataChannelSession: DataChannelSession? = null;
    private var localTextView: TextView? = null
    private var remoteTextView: TextView? = null
    private var statusConnection: TextView? = null
    private var channel: DataChannel? = null
    private var listaChannel: MutableList<DataChannel?> = mutableListOf();
    private var jaEnviouDados: Boolean = false;
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
//        Timer().scheduleAtFixedRate(object : TimerTask() {
//            override fun run() {
//                Log.i("tag", "A Kiss every 1 seconds")
//                enviarDados();
//            }
//        }, 0, 10000)]
        Handler().postDelayed({
            enviarDados()
        }, 1000)

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
            remoteTextView?.text = string
        }
    }
    private fun enviarDados() {
        runOnUiThread {
            val text = Date();
            listaChannel.forEach {
                if (it?.state() == DataChannel.State.OPEN) {
                    val buffer = ByteBuffer.wrap(text.toString().toByteArray())
                    it.send(DataChannel.Buffer(buffer, false))
                    remoteTextView?.text = "Estou enviando == " + text.toString()
                    Handler().postDelayed({
                        enviarDados()
                    }, 1000)
                    jaEnviouDados = true;
                }
            }
        }
    }
    private fun sendMessage() {
        val textLocal = localTextView?.text;
        listaChannel.forEach {
            if (it?.state() == DataChannel.State.OPEN) {
                val buffer = ByteBuffer.wrap(textLocal.toString().toByteArray())
                it.send(DataChannel.Buffer(buffer, false))
            }
        }
    }
    private fun onSendCb(chan: DataChannel?) {
        if(chan != null) {
            listaChannel.add(chan);
            if(!jaEnviouDados)
                enviarDados()
        };
    }
    private fun startVideoSession() {
        dataChannelSession = DataChannelSession.connect(this, BACKEND_URL, this::onMesasge, this::onSendCb, this::onStatusChanged)

    }


    companion object {
        private val CAMERA_AUDIO_PERMISSION_REQUEST = 1
        private val TAG = "VideoCallActivity"
        private val BACKEND_URL ="ws://192.168.15.24:7000/" // Change HOST to your server's IP if you want to test
    }
}
