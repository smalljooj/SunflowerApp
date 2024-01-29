package br.edu.ifpb.sunflower.games.sequenciacores

import android.graphics.drawable.GradientDrawable
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.WindowInsets
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.forEach
import androidx.core.view.get
import br.edu.ifpb.sunflower.R
import br.edu.ifpb.sunflower.databinding.ActivityTelaJogoBinding
import br.edu.ifpb.sunflower.databinding.LayoutRespostaBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

class TelaJogo : AppCompatActivity(), View.OnTouchListener {

    private var offsetX = 0f
    private var offsetY = 0f
    private lateinit var binding: ActivityTelaJogoBinding
    private lateinit var sequenciaCores: LinearLayout
    private lateinit var sequenciaCorreta: LinearLayout
    private lateinit var sequenciaVazia: LinearLayout
    private lateinit var bloqueio: TextView
    private lateinit var nivel: TextView
    private var shortAnimationDuration: Int = 0
    private val criadorShape = CriadorShape()
    private val cores = listOf(
        R.color.vermelho,
        R.color.laranja,
        R.color.amarelo,
        R.color.verde,
        R.color.azul,
        R.color.roxo
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaJogoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        sequenciaCores = binding.sequenciaCores
        sequenciaVazia = binding.sequenciaVazia
        sequenciaCorreta = binding.sequenciaCorreta
        nivel = binding.nivel
        bloqueio = binding.bloqueio
        bloqueio.visibility = View.INVISIBLE
        criarOpcoesCores()

        binding.btnconferir.setOnClickListener {
            conferir()
        }
        gerarComponentes(2)
        iniciarJogo()
        shortAnimationDuration = resources.getInteger(android.R.integer.config_shortAnimTime)
        addListener()

    }

    private fun addNivel() {
        var num = Integer.parseInt(nivel.text.toString())
        num++
        nivel.text = "$num"
    }

    private fun gerarComponentes(qtd: Int) {
        for (i in 1..qtd) {
            sequenciaVazia.addView(criadorShape.duplicarTextView(sequenciaVazia[0], this))
            sequenciaCorreta.addView(criadorShape.duplicarTextView(sequenciaVazia[0], this))
        }
    }

    private fun conferir() {
        crossfade(sequenciaCorreta, false)
        var acertou = true
        for (i in 0 until sequenciaCorreta.childCount) {
            val bgcorreto = sequenciaCorreta[i].background as GradientDrawable
            val bgvazia = sequenciaVazia[i].background as GradientDrawable
            if (bgcorreto.color != bgvazia.color) acertou = false
        }
        resultado(acertou)
    }

    private fun resultado(acertou: Boolean) {
        val layout = LayoutRespostaBinding.inflate(layoutInflater).layoutResultado
        layout.layoutParams = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layout.visibility = View.INVISIBLE
        val bg: Int
        var terminouJogo = false
        if (acertou) {
            addNivel()
            if (sequenciaCorreta.childCount == 6 && nivel.text.equals("9")) {
                bg = R.drawable.trofeu
                terminouJogo = true
            } else {
                bg = R.drawable.emoji_certo
            }
        } else {
            bg = R.drawable.emoji_errado
        }

        layout[0].setBackgroundResource(bg) // background = bg
        binding.mainLayout.addView(layout)
        val fadeout = AnimationUtils.loadAnimation(this, R.anim.slideout)
        bloqueio.startAnimation(fadeout)
        bloqueio.visibility = View.INVISIBLE
        MainScope().launch {
            crossfade(layout, false)
            delay(1900)
            crossfade(layout, true)
            runOnUiThread {
                if (terminouJogo) {
                    onBackPressed() //finalizou o jogo
                } else if (acertou) {
                    val num = Integer.parseInt(nivel.text.toString())
                    if (sequenciaCorreta.childCount < 6 && num % 2 == 1) {
                        gerarComponentes(1)
                    }
                    iniciarJogo()
                } else {
                    onBackPressed() //errou uma sequencia
                }
            }
        }
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

        v.apply {
            alpha = alphaInicio
            visibility = View.VISIBLE

            animate()
                .alpha(alphaFim)
                .setDuration(shortAnimationDuration.toLong())
                .setListener(null)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        /*
        val i = Intent(this, BlackSplashActivity::class.java)
        i.putExtra("tela", "br.edu.ifpb.sunflower.games.sequenciacores.MenuInicial")
        startActivity(i)
         */
        finish()
        overridePendingTransition(0, R.anim.fadeout)
    }

    private fun criarOpcoesCores() {
        val campoCor = sequenciaCores[0]
        val bg = campoCor.background as GradientDrawable
        campoCor.background = criadorShape.criarShapeRedondo(this, cores[0], bg)
        for (i in 1 until 6) {
            val campoCor = criadorShape.duplicarTextView(campoCor, this)
            campoCor.background = criadorShape.criarShapeRedondo(this, cores[i], bg)
            sequenciaCores.addView(campoCor)
        }

    }

    private fun embaralharSequencia() {
        val coresSorteadas = ArrayList<Int>()
        for (i in 0 until sequenciaCorreta.childCount) {
            val componente = sequenciaCorreta[i]
            var cor = cores[Random.nextInt(0, cores.size)]
            while (cor in coresSorteadas) {
                cor = cores[Random.nextInt(0, cores.size)]
            }
            coresSorteadas.add(cor)
            componente.background =
                criadorShape.criarShapeRedondo(this, cor, componente.background as GradientDrawable)
        }
    }

    private fun iniciarJogo() {
        embaralharSequencia()
        for (i in 0 until sequenciaVazia.childCount) {
            sequenciaVazia[i].background = criadorShape.criarShapeRedondo(
                this,
                R.color.white, sequenciaVazia[0].background as GradientDrawable
            )
        }
        val fadein = AnimationUtils.loadAnimation(this, R.anim.slidein)
        MainScope().launch {
            val componentes = binding.componentes
            componentes.visibility = View.INVISIBLE
            delay(3000)
            val layout = bloqueio.layoutParams
            layout.width = sequenciaCorreta.width + 10
            bloqueio.layoutParams = layout
            bloqueio.visibility = View.VISIBLE
            bloqueio.startAnimation(fadein)
            delay(1300)
            crossfade(componentes, false)
        }
    }

    private fun addListener() {
        sequenciaCores.forEach { view ->
            view.setOnTouchListener(this)
        }
    }

    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        val index = sequenciaCores.indexOfChild(view)
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                offsetX = event.rawX - view!!.x
                offsetY = event.rawY - view.y
                view.translationZ = 1f
                return true
            }

            MotionEvent.ACTION_MOVE -> {
                val newX = event.rawX - offsetX
                val newY = event.rawY - offsetY
                view!!.x = newX
                view.y = newY

                return true
            }

            MotionEvent.ACTION_UP -> {
                val xyCor = intArrayOf(0, 0)
                val xyTextView = intArrayOf(0, 0)
                view!!.getLocationOnScreen(xyCor)
                val xCor = xyCor[0] + view.width / 2
                val yCor = xyCor[1] + view.height / 2

                for (i in 0 until sequenciaVazia.childCount) {
                    val textView = sequenciaVazia[i]
                    textView.getLocationOnScreen(xyTextView)

                    if (xCor > xyTextView[0] && xCor < xyTextView[0] + textView.width &&
                        yCor > xyTextView[1] && yCor < xyTextView[1] + textView.height
                    ) {
                        textView.background = view.background
                        break
                    }
                }
                sequenciaCores.removeView(view)
                val novoCampoCor = criadorShape.duplicarTextView(view, this)
                sequenciaCores.addView(novoCampoCor, index)
                addListener()
                return true
            }

            else -> return false
        }
    }

}
