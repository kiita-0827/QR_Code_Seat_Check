import android.hardware.Camera
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private fun safeCameraOpen(id: Int): Boolean {
        return try {
            releaseCameraAndPreview()
            mCamera = Camera.open(id)
            true
        } catch (e: Exception) {
            Log.e(getString(R.string.app_name), "failed to open Camera")
            e.printStackTrace()
            false
        }
    }

    private fun releaseCameraAndPreview() {
        preview?.setCamera(null)
        mCamera?.also { camera ->
            camera.release()
            mCamera = null
        }
    }

}