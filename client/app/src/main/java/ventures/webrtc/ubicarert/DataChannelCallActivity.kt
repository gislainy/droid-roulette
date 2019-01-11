package ventures.webrtc.ubicarert

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Button
import android.widget.TextView
import ventures.webrtc.ubicarert.webrtc.*

class DataChannelCallActivity : AppCompatActivity() {

    private var dataChannelSession: DataChannelSession? = null;
    private var localTextView: TextView? = null
    private var remoteTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_call)
        localTextView = findViewById(R.id.localTextView)
        remoteTextView = findViewById(R.id.localTextView)

        val hangup : Button = findViewById(R.id.sendButton)
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
                    remoteTextView?.text = resources.getString(newStatus.label)
                    remoteTextView?.setTextColor(ContextCompat.getColor(this, newStatus.color))
                }
            }
        }
    }


    private fun startVideoSession() {
//        dataChannelSession = DataChannelSession.connect(this, BACKEND_URL, this::onStatusChanged)

    }



    companion object {
        private val CAMERA_AUDIO_PERMISSION_REQUEST = 1
        private val TAG = "VideoCallActivity"
        private val BACKEND_URL = "ws://192.168.15.8:4433/" // Change HOST to your server's IP if you want to test
    }
}
