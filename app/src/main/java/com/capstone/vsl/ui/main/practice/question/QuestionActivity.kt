package com.capstone.vsl.ui.main.practice.question

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.capstone.vsl.R
import com.capstone.vsl.databinding.ActivityQuestionBinding

class QuestionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuestionBinding

    private var questionImages = intArrayOf(
        R.drawable.a1,//1
        R.drawable.b,//2
        R.drawable.e,//3
        R.drawable.f,//4
        R.drawable.h,//5
        R.drawable.l,//6
        R.drawable.n,//7
        R.drawable.o,//8
        R.drawable.r,//9
        R.drawable.q,//10
    )

    private var option = arrayOf(
        arrayOf("A", "B", "C"),//1
        arrayOf("Z", "B", "I"),//2
        arrayOf("T", "H", "E"),//3
        arrayOf("F", "G", "H"),//4
        arrayOf("P", "Z", "H"),//5
        arrayOf("L", "O", "V"),//6
        arrayOf("C", "V", "N"),//7
        arrayOf("W", "O", "X"),//8
        arrayOf("Q", "R", "S"),//9
        arrayOf("Q", "G", "T"),//10
    )

    private var correctAnswer = arrayOf(0, 1, 2, 0, 2, 0, 2, 1, 1, 0)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuestionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        shuffleQuiz()
        displayQuestion()

        binding.btnOption1.setOnClickListener {
            checkAnswerr(0)
        }

        binding.btnOption2.setOnClickListener {
            checkAnswerr(1)
        }

        binding.btnOption3.setOnClickListener {
            checkAnswerr(2)
        }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.btnOption1.setBackgroundColor(Color.GREEN)
            1 -> binding.btnOption2.setBackgroundColor(Color.GREEN)
            2 -> binding.btnOption3.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColors(buttonIndex: Int) {
        when(buttonIndex) {
            0 -> binding.btnOption1.setBackgroundColor(Color.RED)
            1 -> binding.btnOption2.setBackgroundColor(Color.RED)
            2 -> binding.btnOption3.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        binding.btnOption1.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.btnOption2.setBackgroundColor(Color.rgb(50, 59, 96))
        binding.btnOption3.setBackgroundColor(Color.rgb(50, 59, 96))
    }

    private fun displayQuestion() {
        binding.quizImage.setImageResource(questionImages[currentQuestionIndex])
        binding.btnOption1.text = option[currentQuestionIndex][0]
        binding.btnOption2.text = option[currentQuestionIndex][1]
        binding.btnOption3.text = option[currentQuestionIndex][2]
        resetButtonColors()
    }

    private fun checkAnswerr(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswer[currentQuestionIndex]

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColors(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }
        if (currentQuestionIndex < questionImages.size - 1) {
            currentQuestionIndex++
            binding.quizText.postDelayed({displayQuestion()}, 1000)
        } else {
            showResult()
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        shuffleQuiz()
        displayQuestion()
    }

    private fun shuffleQuiz() {
        val shuffledIndices = questionImages.indices.shuffled()
        val shuffledQuestions = shuffledIndices.take(5).toTypedArray()

        val shuffledOption = mutableListOf<Array<String>>()
        val shuffledCorrectAnswer = mutableListOf<Int>()
        val shuffledImages = mutableListOf<Int>()

        for (index in shuffledQuestions) {
            shuffledOption.add(option[index])
            shuffledCorrectAnswer.add(correctAnswer[index])
            shuffledImages.add(questionImages[index])
        }

        option = shuffledOption.toTypedArray()
        correctAnswer = shuffledCorrectAnswer.toTypedArray()
        questionImages = shuffledImages.toIntArray()
    }

    private fun showResult() {
        val scaledScore = (score.toDouble() / questionImages.size.toDouble() * 100).toInt()
        val resultMessage = "Your Score:\n$scaledScore"

        val dialogView = layoutInflater.inflate(R.layout.pop_up_layout, null)
        val textResult = dialogView.findViewById<TextView>(R.id.tv_result)
        val ivEmote = dialogView.findViewById<ImageView>(R.id.ivEmote)
        val tvDesc = dialogView.findViewById<TextView>(R.id.tv_desc)
        val buttonAgain = dialogView.findViewById<Button>(R.id.btn_back)
        val buttonBack = dialogView.findViewById<Button>(R.id.btn_again)

        val spannableStringBuilder = SpannableStringBuilder(resultMessage)
        val spanStart = resultMessage.indexOf("$scaledScore")
        val spanEnd = spanStart + scaledScore.toString().length

        spannableStringBuilder.setSpan(
            RelativeSizeSpan(1.5f),
            spanStart,
            spanEnd,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )

        textResult.text = spannableStringBuilder

        val resultData = getResultData(scaledScore)
        ivEmote.setImageResource(resultData.emoteResourceId)
        tvDesc.text = resultData.descriptionMessage

        val alertDialogBuilder = AlertDialog.Builder(this)
            .setView(dialogView)

        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        buttonAgain.setOnClickListener {
            restartQuiz()
            alertDialog.dismiss()
        }

        buttonBack.setOnClickListener {
            finish()
        }
    }

    private fun getResultData(score: Int): ResultData {
        return when {
            score >= 100 -> ResultData(R.drawable.smiley, getString(R.string.score1))
            score in 80..99 -> ResultData(R.drawable.emote2, getString(R.string.score2))
            score in 60..79 -> ResultData(R.drawable.emote3, getString(R.string.score3))
            score in 40..59 -> ResultData(R.drawable.emote4, getString(R.string.score4))
            score in 20..39 -> ResultData(R.drawable.emote5, getString(R.string.score5))
            score in 0..19 -> ResultData(R.drawable.emote4, getString(R.string.score5))
            else -> ResultData(R.drawable.emote3, getString(R.string.score_else))
        }
    }
}