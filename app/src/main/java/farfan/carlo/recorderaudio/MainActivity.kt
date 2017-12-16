package farfan.carlo.recorderaudio

import android.Manifest.permission.RECORD_AUDIO
import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.content.pm.PackageManager
import android.media.MediaRecorder
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private var nameFile: String? = null
    var mediaRecorder: MediaRecorder? = null
    private val RequestPermissionCode = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onCaptureClick(view: View) {
        if (checkPermission()) {
            nameFile = "${Environment.getExternalStorageDirectory().absolutePath}/1234AudioRecording.3gp"

            MediaRecorderReady()

            mediaRecorder?.prepare()
            mediaRecorder?.start()

            Toast.makeText(this@MainActivity, "Recording started", Toast.LENGTH_SHORT).show()
            Toast.makeText(this@MainActivity, "Recording started", Toast.LENGTH_SHORT).show()
        } else {
            requestPermission()
        }
    }

    fun onCaptureClickStop(view: View) {
        mediaRecorder?.stop()
        Toast.makeText(this@MainActivity, "Recording completed", Toast.LENGTH_SHORT).show()
    }

    fun MediaRecorderReady() {
        mediaRecorder = MediaRecorder()
        mediaRecorder?.setAudioSource(MediaRecorder.AudioSource.MIC)
        mediaRecorder?.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder?.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB)
        mediaRecorder?.setOutputFile(nameFile)
    }

    fun checkPermission() : Boolean {
        val result: Int = ContextCompat.checkSelfPermission(applicationContext,
        WRITE_EXTERNAL_STORAGE)
        val result1: Int = ContextCompat.checkSelfPermission(applicationContext,
            RECORD_AUDIO)
        return result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    }

    fun requestPermission() {
        ActivityCompat.requestPermissions(this@MainActivity, arrayOf(WRITE_EXTERNAL_STORAGE, RECORD_AUDIO), RequestPermissionCode)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RequestPermissionCode -> if (grantResults.isNotEmpty()) {
                val StoragePermission: Boolean = grantResults[0] == PackageManager.PERMISSION_GRANTED
                val RecordPermission: Boolean = grantResults[1] == PackageManager.PERMISSION_GRANTED

                if (StoragePermission && RecordPermission) {
                    Toast.makeText(this@MainActivity, "Permission granted", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@MainActivity, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}