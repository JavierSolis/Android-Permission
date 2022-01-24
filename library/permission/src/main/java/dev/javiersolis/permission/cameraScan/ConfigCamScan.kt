package dev.javiersolis.permission.cameraScan

import android.Manifest
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.zxing.integration.android.IntentIntegrator
import dev.javiersolis.permission.IRequestPermission

class ConfigCamScan
{
    companion object{
        fun initCameraScan(fragment:Fragment, iRequestPermission: IRequestPermission) {
            val iScanHome: IRequestPermission = iRequestPermission
            if (!iScanHome.hasPermission(Manifest.permission.CAMERA)) {
                iScanHome.requestPermission(
                    Manifest.permission.CAMERA,
                    "Se necesita acceder a la camara para escanear el QR"//getString(R.string.scan_pallet_permission_rationale)
                )
                return
            }

            val integrator = IntentIntegrator.forSupportFragment(fragment).apply {
                captureActivity = CaptureActivity::class.java
                setOrientationLocked(false)
                setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES)
                setPrompt("Escanear")
            }
            integrator.initiateScan()
        }

        fun onResult(context:Context?,requestCode:Int,resultCode:Int,data: Intent?,f:(it:String)->Unit){
            val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
            if (result != null) {
                if (result.contents == null) {
                    Toast.makeText(context, "Cancelo escaneo", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(context, "Escaneo: " + result.contents, Toast.LENGTH_LONG).show()
                    f(result.contents)
                }
            }
        }
    }

}