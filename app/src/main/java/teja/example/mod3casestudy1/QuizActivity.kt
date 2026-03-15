package teja.example.mod3casestudy1

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatActivity
import teja.example.mod3casestudy1.databinding.ActivityQuizBinding

class QuizActivity : AppCompatActivity() {

    private lateinit var binding: ActivityQuizBinding

    private val questions = listOf(
        Question("What is the capital of France?", listOf("Paris","Berlin","Madrid","Rome"),0),
        Question("What is 5 + 3?", listOf("5","8","10","15"),1),
        Question("Which planet is known as Red Planet?", listOf("Earth","Mars","Jupiter","Venus"),1)
    )

    private var currentQuestionIndex = 0
    private var score = 0
    private var timer: CountDownTimer? = null

    private var quizStartTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizBinding.inflate(layoutInflater)
        setContentView(binding.root)

        quizStartTime = System.currentTimeMillis()

        showQuestion()

        binding.submitButton.setOnClickListener {

            checkAnswer()
            currentQuestionIndex++

            if (currentQuestionIndex < questions.size) {
                showQuestion()
            } else {

                val endTime = System.currentTimeMillis()
                val timeTaken = (endTime - quizStartTime) / 1000

                val intent = Intent(this, ResultsActivity::class.java)
                intent.putExtra("score", score)
                intent.putExtra("totalQuestions", questions.size)
                intent.putExtra("timeTaken", timeTaken)

                startActivity(intent)
                finish()
            }
        }
    }

    private fun showQuestion() {

        timer?.cancel()

        val question = questions[currentQuestionIndex]

        binding.questionText.text = question.text
        binding.option1.text = question.options[0]
        binding.option2.text = question.options[1]
        binding.option3.text = question.options[2]
        binding.option4.text = question.options[3]

        binding.optionsGroup.clearCheck()

        timer = object : CountDownTimer(30000,1000){

            override fun onTick(millisUntilFinished: Long) {
                binding.timerText.text = "Time Left: ${millisUntilFinished/1000}s"
            }

            override fun onFinish() {
                binding.submitButton.performClick()
            }

        }.start()
    }

    private fun checkAnswer(){

        val selectedOption = when(binding.optionsGroup.checkedRadioButtonId){

            binding.option1.id -> 0
            binding.option2.id -> 1
            binding.option3.id -> 2
            binding.option4.id -> 3

            else -> -1
        }

        if(selectedOption == questions[currentQuestionIndex].correctOption){
            score++
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}