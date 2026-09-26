package com.raja.hrsender

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.raja.hrsender.databinding.ActivityMainBinding
import java.io.File
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    // ---- EDIT THESE THREE VALUES IF YOU EVER NEED TO CHANGE THE TEMPLATE ----
    private val EMAIL_SUBJECT = "Job Application - IAM / Cybersecurity Professional - Raja Srinivas"

    private val EMAIL_BODY = """
        Good day !

        I hope you're doing well.

        I'm reaching out regarding the job opportunity.

        I have 14+ years of experience in IT operations and cybersecurity, with a strong focus on Identity and Access Management (IAM).

        My experience includes:
        - IAM Operations and Access Management
        - Active Directory administration
        - User onboarding and offboarding
        - Provisioning and deprovisioning
        - ABAC and access governance
        - Privileged Access Management
        - Security operations and incident management
        - ServiceNow and ITIL processes

        Please find my resume attached for your consideration. I would appreciate the opportunity to discuss how my experience could align with the requirements.

        You can also find my professional profile here:

        LinkedIn: https://www.linkedin.com/in/srinivasbe/

        Thank you for your time and consideration.

        Regards,
        RAJA SRINIVAS
        IAM / Cybersecurity Professional
        +91 9789722838
    """.trimIndent()

    private val ATTACHMENT_ASSET_NAME = "Raja_Srinivas_Resume.docx"
    // --------------------------------------------------------------------------

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSend.setOnClickListener {
            val hrEmail = binding.editHrEmail.text.toString().trim()
            if (hrEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(hrEmail).matches()) {
                Toast.makeText(this, "Please paste a valid HR email address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            sendEmail(hrEmail)
        }
    }

    private fun sendEmail(hrEmail: String) {
        try {
            val attachmentUri = copyAssetToCacheAndGetUri(ATTACHMENT_ASSET_NAME)

            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "application/vnd.openxmlformats-officedocument.wordprocessingml.document"
                putExtra(Intent.EXTRA_EMAIL, arrayOf(hrEmail))
                putExtra(Intent.EXTRA_SUBJECT, EMAIL_SUBJECT)
                putExtra(Intent.EXTRA_TEXT, EMAIL_BODY)
                putExtra(Intent.EXTRA_STREAM, attachmentUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                setPackage("com.google.android.gm") // force Gmail
            }

            try {
                startActivity(intent)
            } catch (e: android.content.ActivityNotFoundException) {
                // Gmail app not found on device -> fall back to any email app
                intent.setPackage(null)
                startActivity(Intent.createChooser(intent, "Send email using..."))
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Could not prepare email: ${e.message}", Toast.LENGTH_LONG).show()
        }
    }

    /**
     * Copies the bundled resume from assets into the app's cache folder
     * (fresh copy every time, so it always matches what's inside the APK)
     * and returns a content:// Uri that Gmail is allowed to read.
     */
    private fun copyAssetToCacheAndGetUri(assetName: String): Uri {
        val attachmentsDir = File(cacheDir, "attachments").apply { mkdirs() }
        val outFile = File(attachmentsDir, assetName)

        assets.open(assetName).use { input ->
            FileOutputStream(outFile).use { output ->
                input.copyTo(output)
            }
        }

        return FileProvider.getUriForFile(
            this,
            "com.raja.hrsender.fileprovider",
            outFile
        )
    }
}
