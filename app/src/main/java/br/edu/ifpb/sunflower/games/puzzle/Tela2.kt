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

class Tela2 : AppCompatActivity() {
    private lateinit var imageViewPart1: ImageView
    private lateinit var imageViewPart2: ImageView
    private lateinit var imageViewPart3: ImageView
    private lateinit var imageViewPart4: ImageView
    private lateinit var imageViewPart5: ImageView
    private lateinit var imageViewPart6: ImageView
    private lateinit var imageViewPart7: ImageView
    private lateinit var imageViewPart8: ImageView
    private lateinit var imageViewPart9: ImageView
    private lateinit var errorSound: MediaPlayer
    private lateinit var rightsound: MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela2)

        errorSound = MediaPlayer.create(this, R.raw.errorsound)
        rightsound = MediaPlayer.create(this, R.raw.rightsound)

        imageViewPart1 = findViewById(R.id.imageViewPart1)
        imageViewPart2 = findViewById(R.id.imageViewPart2)
        imageViewPart3 = findViewById(R.id.imageViewPart3)
        imageViewPart4 = findViewById(R.id.imageViewPart4)
        imageViewPart5 = findViewById(R.id.imageViewPart5)
        imageViewPart6 = findViewById(R.id.imageViewPart6)
        imageViewPart7 = findViewById(R.id.imageViewPart7)
        imageViewPart8 = findViewById(R.id.imageViewPart8)
        imageViewPart9 = findViewById(R.id.imageViewPart9)

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
        imageViewPart5.setOnClickListener {
            imageViewPart5.rotation = (imageViewPart5.rotation + 90) % 360
        }
        imageViewPart6.setOnClickListener {
            imageViewPart6.rotation = (imageViewPart6.rotation + 90) % 360
        }
        imageViewPart7.setOnClickListener {
            imageViewPart7.rotation = (imageViewPart7.rotation + 90) % 360
        }
        imageViewPart8.setOnClickListener {
            imageViewPart8.rotation = (imageViewPart8.rotation + 90) % 360
        }
        imageViewPart9.setOnClickListener {
            imageViewPart9.rotation = (imageViewPart9.rotation + 90) % 360
        }

        val nextlevel: Button = findViewById(R.id.nextPhaseButton2)

        val exit: Button = findViewById(R.id.sair)

        exit.setOnClickListener {
            finish()
        }

        nextlevel.setOnClickListener {
            if (areRotationsZero()) {
                playRightSound()
                Handler(Looper.myLooper()!!).postDelayed( {
                    val intent = Intent(this, Tela3::class.java)
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
                imageViewPart4.rotation == 0f &&
                imageViewPart5.rotation == 0f &&
                imageViewPart6.rotation == 0f &&
                imageViewPart7.rotation == 0f &&
                imageViewPart8.rotation == 0f &&
                imageViewPart9.rotation == 0f)
    }
}