package com.example.kulit

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.SimpleDateFormat
import java.util.*

class ResultActivity : AppCompatActivity() {

    private lateinit var tvResult: TextView
    private lateinit var ivPhoto: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        // Initialize views
        tvResult = findViewById(R.id.tvResult)
        ivPhoto = findViewById(R.id.ivPhoto)

        // Show the result of skin analysis
        showResult()
    }

    private fun showResult() {
        // Display analysis result text
        tvResult.text = "Your Skin analysis result:\nYour skin is healthy."

        // Set the photo (replace with the actual image from the analysis)
        ivPhoto.setImageResource(R.drawable.ic_launcher_background) // Replace with the actual image

        // Display the analysis time
        val currentTime = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date())
        tvResult.append("\nAnalyzed in: $currentTime")
    }
}
