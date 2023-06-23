import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    private lateinit var camera: Camera
    private lateinit var cameraPreview: CameraPreview

    companion object {
        private const val CAMERA_PERMISSION_REQUEST_CODE = 200
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // カメラのパーミッションをリクエストする
        requestCameraPermission()
    }

    private fun requestCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), CAMERA_PERMISSION_REQUEST_CODE)
        } else {
            initializeCamera()
        }
    }

    private fun initializeCamera() {
        camera = Camera.open()

        val previewLayout = findViewById<FrameLayout>(R.id.camera_preview)
        cameraPreview = CameraPreview(this, camera)
        previewLayout.addView(cameraPreview)
    }

    private fun startCamera() {
        camera.startPreview()
    }

    private fun stopCamera() {
        camera.stopPreview()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initializeCamera()
        }
    }

    override fun onPause() {
        super.onPause()
        stopCamera()
        camera.release()
    }
}