package br.edu.ifpb.sunflower.games.puzzle

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifpb.sunflower.R

class Tela1 : AppCompatActivity() {
    private lateinit var imageViewPart1: ImageView
    private lateinit var imageViewPart2: ImageView
    private lateinit var imageViewPart3: ImageView
    private lateinit var imageViewPart4: ImageView
    private lateinit var errorSound: MediaPlayer
    private lateinit var rightsound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela1)

        errorSound = MediaPlayer.create(this, R.raw.errorsound)
        rightsound = MediaPlayer.create(this, R.raw.rightsound)

        imageViewPart1 = findViewById(R.id.imageViewPart1)
        imageViewPart2 = findViewById(R.id.imageViewPart2)
        imageViewPart3 = findViewById(R.id.imageViewPart3)
        imageViewPart4 = findViewById(R.id.imageViewPart4)

        imageViewPart1.setOnClickListener {
            imageViewPart1.rotation = (imageViewPart1.rotation + 90) % 360
        }
        imageViewPart2.setOnClickListener {
            imageViewPart2.rotation = (imageViewPart2.rotation + 90) % 360
        }
        imageViewPart3.setOnClickListener {
            imageViewPart3.rotation = (imageViewPart3.rotation + 90) % 360
        }
        imageViewPart4.setOnClickListener {
            imageViewPart4.rotation = (imageViewPart4.rotation + 90) % 360
        }

        val nextlevel: Button = findViewById(R.id.nextPhaseButton)

        val exit: Button = findViewById(R.id.sair)

        exit.setOnClickListener {
            finish()
        }

        nextlevel.setOnClickListener {
            if (areRotationsZero()) {
                playRightSound()
                Handler(Looper.myLooper()!!).postDelayed( {
                    val intent = Intent(this, Tela2::class.java)
                    startActivity(intent)
                    finish() }, 1500)

            } else {
                playErrorSound()
                Toast.makeText(this, "Alinhe as peças corretamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun playErrorSound() {
        errorSound.start()
    }

    private fun playRightSound() {
        rightsound.start()
    }

    private fun areRotationsZero(): Boolean {
        return (imageViewPart1.rotation == 0f &&
                imageViewPart2.rotation == 0f &&
                imageViewPart3.rotation == 0f &&
                imageViewPart4.rotation == 0f)
    }
}