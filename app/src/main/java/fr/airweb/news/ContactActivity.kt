package fr.airweb.news

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.airweb.news.databinding.ActivityContactBinding

class ContactActivity : AppCompatActivity() {

    private lateinit var viewBinding: ActivityContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewBinding = ActivityContactBinding.inflate(layoutInflater)
        setContentView(viewBinding.root)

        configureViews(viewBinding)
    }

    private fun configureViews(viewBinding: ActivityContactBinding) {
        viewBinding.emailView.setOnClickListener {
            sendEmail(getString(R.string.contact_airweb_email))
        }

        viewBinding.phoneView.setOnClickListener {
            callPhoneNumber(getString(R.string.contact_airweb_phone_number))
        }

        viewBinding.postalAddressView.setOnClickListener {
            startDirections(getString(R.string.contact_airweb_postal_address))
        }
    }

    private fun sendEmail(email: String) {
        val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", email, null))
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.contact))
        emailIntent.putExtra(Intent.EXTRA_TEXT, "")

        try {
            // TODO extract string
            startActivity(Intent.createChooser(emailIntent, "Choose email provider"))
        } catch (ex: ActivityNotFoundException) {
            Toast.makeText(
                this,
                "No email clients installed.", // TODO extract
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun callPhoneNumber(phone: String) {
        val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null))
        startActivity(intent)
    }

    private fun startDirections(address: String) {
        val gmmIntentUri = Uri.parse("google.navigation:q=$address")
        val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
        mapIntent.setPackage("com.google.android.apps.maps")
        mapIntent.resolveActivity(packageManager)?.let {
            startActivity(mapIntent)
        }
    }
}