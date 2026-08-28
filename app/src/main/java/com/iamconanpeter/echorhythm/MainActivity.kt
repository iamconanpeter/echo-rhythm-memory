// MainActivity.kt — programmatic 4-ring grid for Echo Rhythm Memory
package com.iamconanpeter.echorhythm

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Bundle
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.time.LocalDate

class MainActivity : AppCompatActivity() {
    private lateinit var generator: SequenceGenerator
    private lateinit var engine: ReplayEngine
    private lateinit var state: GameState

    private val taps = mutableListOf<Int>()
    private var sequence: List<Int> = emptyList()

    private lateinit var board: RingBoardView
    private lateinit var status: TextView
    private lateinit var scoreView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val seed = LocalDate.now().toEpochDay()
        generator = SequenceGenerator(seed)
        engine = ReplayEngine()
        state = GameState()
        sequence = generator.next(size = state.round, colorCount = 4)

        val root = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setBackgroundColor(Color.parseColor("#101020"))
        }

        status = TextView(this).apply {
            textSize = 18f
            setTextColor(Color.WHITE)
            text = "Round 1 — tap Start, then replay ${sequence.size} step(s)"
        }
        scoreView = TextView(this).apply {
            textSize = 16f
            setTextColor(Color.parseColor("#CCCCDD"))
            text = "Best: 0  Streak: 0  Echo: locked (round 5)"
        }
        val btnRow = LinearLayout(this).apply { orientation = LinearLayout.HORIZONTAL }
        val startBtn = Button(this).apply { text = "Start" }
        val echoBtn = Button(this).apply { text = "Echo" }
        val replayBtn = Button(this).apply { text = "Replay" }
        btnRow.addView(startBtn)
        btnRow.addView(echoBtn)
        btnRow.addView(replayBtn)

        board = RingBoardView(this).apply {
            onRingTapped = { idx -> onTap(idx) }
        }

        root.addView(status)
        root.addView(scoreView)
        root.addView(btnRow)
        root.addView(board, LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        ))

        startBtn.setOnClickListener {
            taps.clear()
            sequence = generator.next(size = state.round, colorCount = 4)
            board.flashSequence(sequence)
            status.text = "Round ${state.round} — play back ${sequence.size} steps"
        }
        echoBtn.setOnClickListener {
            if (state.round < 5) {
                status.text = "Echo unlocks at round 5"
                return@setOnClickListener
            }
            if (state.echoMode) {
                status.text = "Echo already active"
                return@setOnClickListener
            }
            if (!state.useToken()) {
                status.text = "No listen-again tokens"
                return@setOnClickListener
            }
            state.echoMode = true
            sequence = generator.echo(sequence)
            board.flashSequence(sequence)
            status.text = "Echo reverse — ${sequence.size} steps"
        }
        replayBtn.setOnClickListener {
            taps.clear()
            board.flashSequence(sequence)
            status.text = "Replay: ${sequence.size} steps"
        }

        setContentView(root)
        updateScoreView()
    }

    private fun onTap(idx: Int) {
        taps.add(idx)
        val target = if (state.echoMode) sequence else sequence
        when (engine.evaluate(target, taps)) {
            ReplayEngine.Result.COMPLETE -> {
                val s = engine.score(state.round, 0)
                state.onComplete(s)
                state.nextRound()
                sequence = generator.next(size = state.round, colorCount = 4)
                status.text = "Correct! +$s — next round ${state.round}"
                taps.clear()
            }
            ReplayEngine.Result.WRONG -> {
                state.onFail()
                status.text = "Wrong — streak reset"
                taps.clear()
            }
            ReplayEngine.Result.IN_PROGRESS -> {
                status.text = "OK (${taps.size}/${target.size})"
            }
            ReplayEngine.Result.PARTIAL -> Unit
        }
        updateScoreView()
    }

    private fun updateScoreView() {
        val echoText = if (state.round < 5) "locked (round 5)" else "ready"
        scoreView.text = "Best: ${state.best}  Streak: ${state.streak}  Echo: $echoText"
    }
}

/** 4 colored rings laid out in a square; tap registers as a hit in 0..3. */
class RingBoardView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : View(context, attrs) {
    var onRingTapped: ((Int) -> Unit)? = null

    private val ringColors = intArrayOf(
        Color.parseColor("#E74C3C"), // red
        Color.parseColor("#2ECC71"), // green
        Color.parseColor("#3498DB"), // blue
        Color.parseColor("#F1C40F")  // yellow
    )
    private val basePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val flashPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rect = RectF()

    private var flashing: Int = -1

    fun flashSequence(steps: List<Int>) {
        // Display the sequence as a short animated flash; this MVP just shows the length.
        // Hook is left ready for AnimationDrawable / ValueAnimator in Post-MVP.
        invalidate()
        flashing = -1
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val w = width.toFloat()
        val h = height.toFloat()
        val cellW = w / 2f
        val cellH = h / 2f
        val pad = 16f
        for (i in 0..3) {
            val cx = (i % 2) * cellW + cellW / 2f
            val cy = (i / 2) * cellH + cellH / 2f
            val r = (minOf(cellW, cellH) / 2f) - pad
            basePaint.color = ringColors[i]
            basePaint.alpha = 200
            canvas.drawCircle(cx, cy, r, basePaint)
            if (flashing == i) {
                flashPaint.color = Color.WHITE
                flashPaint.alpha = 160
                canvas.drawCircle(cx, cy, r * 0.4f, flashPaint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action != MotionEvent.ACTION_DOWN) return super.onTouchEvent(event)
        val w = width.toFloat()
        val h = height.toFloat()
        val cellW = w / 2f
        val cellH = h / 2f
        val cx = event.x
        val cy = event.y
        val col = if (cx < cellW) 0 else 1
        val row = if (cy < cellH) 0 else 1
        val idx = row * 2 + col
        onRingTapped?.invoke(idx)
        return true
    }
}
