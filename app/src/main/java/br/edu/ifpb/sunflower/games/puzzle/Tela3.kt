package br.edu.ifpb.sunflower.games.puzzle

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifpb.sunflower.R

class Tela3 : AppCompatActivity() {
    private lateinit var errorSound: MediaPlayer
    private lateinit var rightsound: MediaPlayer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tela3)

        val imageViewPart1: ImageView = findViewById(R.id.imageViewPart1)
        val imageViewPart2: ImageView = findViewById(R.id.imageViewPart2)
        val imageViewPart3: ImageView = findViewById(R.id.imageViewPart3)
        val imageViewPart4: ImageView = findViewById(R.id.imageViewPart4)
        val imageViewPart5: ImageView = findViewById(R.id.imageViewPart5)
        val imageViewPart6: ImageView = findViewById(R.id.imageViewPart6)
        val imageViewPart7: ImageView = findViewById(R.id.imageViewPart7)
        val imageViewPart8: ImageView = findViewById(R.id.imageViewPart8)
        val imageViewPart9: ImageView = findViewById(R.id.imageViewPart9)
        val imageViewPart10: ImageView = findViewById(R.id.imageViewPar10)
        val imageViewPart11: ImageView = findViewById(R.id.imageViewPart11)
        val imageViewPart12: ImageView = findViewById(R.id.imageViewPart12)
        val imageViewPart13: ImageView = findViewById(R.id.imageViewPart13)
        val imageViewPart14: ImageView = findViewById(R.id.imageViewPart14)
        val imageViewPart15: ImageView = findViewById(R.id.imageViewPart15)
        val imageViewPart16: ImageView = findViewById(R.id.imageViewPart16)
        errorSound = MediaPlayer.create(this, R.raw.errorsound)
        rightsound = MediaPlayer.create(this, R.raw.rightsound)

        val nextLevelButton: Button = findViewById(R.id.nextPhaseButton3)

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
        imageViewPart8.setOnClickListener {
            imageViewPart8.rotation = (imageViewPart8.rotation + 90) % 360
        }
        imageViewPart9.setOnClickListener {
            imageViewPart9.rotation = (imageViewPart9.rotation + 90) % 360
        }
        imageViewPart10.setOnClickListener {
            imageViewPart10.rotation = (imageViewPart10.rotation + 90) % 360
        }
        imageViewPart11.setOnClickListener {
            imageViewPart11.rotation = (imageViewPart11.rotation + 90) % 360
        }

        imageViewPart13.setOnClickListener {
            imageViewPart13.rotation = (imageViewPart13.rotation + 90) % 360
        }
        imageViewPart14.setOnClickListener {
            imageViewPart14.rotation = (imageViewPart14.rotation + 90) % 360
        }
        imageViewPart15.setOnClickListener {
            imageViewPart15.rotation = (imageViewPart15.rotation + 90) % 360
        }
        imageViewPart16.setOnClickListener {
            imageViewPart16.rotation = (imageViewPart16.rotation + 90) % 360
        }

        val exit: Button = findViewById(R.id.sair)

        exit.setOnClickListener {
            finish()
        }

        nextLevelButton.setOnClickListener {
            if (areRotationsZero(imageViewPart1, imageViewPart2, imageViewPart3, imageViewPart4, imageViewPart5,
                    imageViewPart6, imageViewPart7, imageViewPart8, imageViewPart9, imageViewPart10, imageViewPart11,
                    imageViewPart12, imageViewPart13, imageViewPart14, imageViewPart15, imageViewPart16)) {
                playRightSound()
                Handler(Looper.myLooper()!!).postDelayed( {
                    //val intent = Intent(this, MainActivity::class.java)
                    //startActivity(intent)
                    finish()
                }, 1500)
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
    private fun areRotationsZero(vararg imageViews: ImageView): Boolean {
        for (imageView in imageViews) {
            if (imageView.rotation != 0f) {
                return false
            }
        }
        return true
    }
}