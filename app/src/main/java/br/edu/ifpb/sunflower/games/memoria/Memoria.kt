package br.edu.ifpb.sunflower.games.memoria

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifpb.sunflower.R
import br.edu.ifpb.sunflower.databinding.ActivityJogoMemoriaBinding
import br.edu.ifpb.sunflower.databinding.LayoutResultadoBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class Memoria: AppCompatActivity() {

    private lateinit var binding: ActivityJogoMemoriaBinding
    private var baralho = LinkedHashMap<ImageView, Int>()
    private var cartasViradas = ArrayList<ImageView>()
    private var qtdCartas: Int = 6
    private var qtdColunas: Int = 4
    private val imagens = listOf(
        R.drawable.baleia,
        R.drawable.caranguejo,
        R.drawable.coruja,
        R.drawable.elefante,
        R.drawable.girafa,
        R.drawable.golfinho,
        R.drawable.gorila,
        R.drawable.leao,
        R.drawable.raposa,
        R.drawable.zebra,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJogoMemoriaBinding.inflate(layoutInflater)
        iniciarJogo()
        setContentView(binding.root)
    }


    private fun iniciarJogo() {
        binding.matriz.removeAllViews()
        binding.button2?.setOnClickListener {
            finish()
        }
        binding.button3?.setOnClickListener {
            finish()
        }

        if (qtdCartas == 10) {
            qtdColunas = 5
        }
        binding.matriz.columnCount = qtdColunas
        baralho.clear()
        for (i in 0 until qtdCartas) {
            baralho.put(getCarta(imagens[i]), imagens[i])
            baralho.put(getCarta(imagens[i]), imagens[i])
        }
        qtdCartas += 2
        var cartas = baralho.keys.toList()
        cartas = cartas.shuffled()

        cartas.forEach {
            binding.matriz.addView(it)
        }
        MainScope().launch {
            delay(2000)
            baralho.forEach {
                it.key.setOnClickListener { flipCard(it as ImageView) }
                it.key.performClick()
            }
        }
    }

    private fun flipCard(view: ImageView) {
        if (view.rotationY == 0f) {
            view.animate().rotationY(90f).withEndAction {
                view.setImageResource(0)
                view.setBackgroundResource(R.drawable.verso_carta)
                view.animate().rotationY(180f).withEndAction {
                }.duration = 250
            }.duration = 250

        } else if (view.rotationY == 180f) {
            view.animate().rotationY(90f).withEndAction {
                view.setImageResource(baralho.getValue(view))
                view.setBackgroundResource(R.drawable.bg_carta)
                view.animate().rotationY(0f).withEndAction {
                    synchronized(this) {
                        conferir(view)
                    }
                }.duration = 250
            }.duration = 250
        }
    }

    private fun conferir(view: ImageView) {
        view.isEnabled = false
        cartasViradas.add(view)
        if (cartasViradas.size == 2) {
            baralho.forEach {
                it.key.isEnabled = false
            }
            if (comparar(cartasViradas[0], cartasViradas[1])) {
                cartasViradas.clear()
                Toast.makeText(this, "certo", Toast.LENGTH_SHORT).show()
                ativarCartasViradas()
                if (verificarResultadoFinal()) {
                    val layout = LayoutResultadoBinding.inflate(layoutInflater).root
                    layout.visibility = View.INVISIBLE
                    layout.layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    (binding.root).addView(layout)
                    MainScope().launch {
                        crossfade(layout, false)
                        delay(1500)
                        crossfade(layout, true)
                        if (qtdCartas == 12) { //terminou o jogo
//                            finish()
                        } else {
                            iniciarJogo()
                        }
                    }

                }
            } else {
                val cartas = arrayOf(cartasViradas[0], cartasViradas[1])
                cartasViradas.clear()
                MainScope().launch {
                    delay(500)
                    runOnUiThread {
                        cartas[0].isEnabled = true
                        cartas[1].isEnabled = true
                        cartas[0].performClick()
                        cartas[1].performClick()
                        ativarCartasViradas()
                    }
                }
            }
        }
    }

    private fun verificarResultadoFinal(): Boolean {
        var ganhou = true
        baralho.forEach {
            if (it.key.rotationY == 180f) {
                ganhou = false
            }
        }
        return ganhou
    }

    private fun ativarCartasViradas() {
        baralho.forEach {
            if (it.key.rotationY == 180f) {
                it.key.isEnabled = true
            }
        }
    }

    private fun comparar(carta1: ImageView, carta2: ImageView): Boolean {
        return baralho.getValue(carta1) == baralho.getValue(carta2)
    }

    private fun getCarta(imagem: Int): ImageView {
        val cartaClone = ActivityJogoMemoriaBinding.inflate(layoutInflater).cartaImageView
        (cartaClone.parent as GridLayout).removeView(cartaClone)
        cartaClone.setImageResource(imagem)
        return cartaClone
    }

    private fun crossfade(v: View, out: Boolean) {
        val alphaInicio: Float
        val alphaFim: Float

        if (out) {
            alphaInicio = 1f
            alphaFim = 0f

        } else {
            alphaInicio = 0f
            alphaFim = 1f
        }
        val shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        v.apply {
            alpha = alphaInicio
            visibility = View.VISIBLE

            animate()
                .alpha(alphaFim).duration = shortAnimationDuration.toLong()
        }
    }

}
