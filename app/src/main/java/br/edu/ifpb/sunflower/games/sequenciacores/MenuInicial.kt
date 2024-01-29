package br.edu.ifpb.sunflower.games.sequenciacores

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import br.edu.ifpb.sunflower.R
import br.edu.ifpb.sunflower.databinding.ActivityMenuInicialBinding

class MenuInicial : AppCompatActivity() {

    private lateinit var binding: ActivityMenuInicialBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        binding = ActivityMenuInicialBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        binding.iniciar.setOnClickListener {
            iniciarJogo()
        }

        binding.sair.setOnClickListener {
            finish()
        }

    }

    fun iniciarJogo() {
        val i = Intent(this, BlackSplashActivity::class.java)
        i.putExtra("tela", "br.edu.ifpb.sunflower.games.sequenciacores.TelaJogo")
        startActivity(i)
        overridePendingTransition(0, R.anim.fadeout)
    }

}
