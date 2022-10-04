package com.ergea.myapplication

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.ergea.myapplication.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnTester.setOnClickListener {
            checkPermissions()
        }
    }

    private fun checkPermissions() {
        val permissionCheck =
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS)

        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "Permission SMS DIIZINKAN", Toast.LENGTH_LONG).show()
            sendSMS()
        } else {
            Toast.makeText(this, "Permission SMS DITOLAK", Toast.LENGTH_LONG).show()
            requestSMSPermission()
        }
    }

    private fun sendSMS() {
        val et1 = binding.et1.text.toString().trim()
        val et2 = binding.et2.text.toString().trim()
        binding.tv1.text = "$et1 $et2"
        Toast.makeText(this, "SMS Berhasil dikirimkan!", Toast.LENGTH_LONG).show()
    }

    private fun requestSMSPermission() {
        requestPermissions(arrayOf(Manifest.permission.SEND_SMS), 100)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            100 -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    permissions[0] == Manifest.permission.SEND_SMS
                ) {
                    sendSMS()
                    Toast.makeText(this, "Permissions for SMS Permitted", Toast.LENGTH_LONG)
                        .show()
                } else {
                    Toast.makeText(this, "Permissions for SMS Denied", Toast.LENGTH_LONG)
                        .show()
                    if (shouldShowRequestPermissionRationale(Manifest.permission.SEND_SMS)) {
                        Toast.makeText(this, "Berikan akses SMS", Toast.LENGTH_LONG).show()
                    } else {
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                        val uri: Uri = Uri.fromParts("package", packageName, null)
                        intent.data = uri
                        startActivity(intent)
                    }
                }
            }
            else -> {
                Toast.makeText(this, "The request code doesn't match", Toast.LENGTH_LONG).show()
            }
        }
    }

}