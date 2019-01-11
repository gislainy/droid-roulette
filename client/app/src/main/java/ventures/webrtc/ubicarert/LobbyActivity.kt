package ventures.webrtc.ubicarert

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LobbyActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_lobby)
        val connectButton: Button = findViewById(R.id.connect_button)
        connectButton.setOnClickListener {
            startVideoCall()
        }
        val DataButton: Button = findViewById(R.id.data_button)
        DataButton.setOnClickListener {
            startDataChannelCall()
        }


    }

    private fun startVideoCall() {
        startActivity(Intent(this, VideoCallActivity::class.java))
    }

    private fun startDataChannelCall() {
        startActivity(Intent(this, DataChannelCallTesteActivity::class.java))
    }
//    private fun startDataChannelCall() {
//        startActivity(Intent(this, DataChannelCallActivity::class.java))
//    }

}
