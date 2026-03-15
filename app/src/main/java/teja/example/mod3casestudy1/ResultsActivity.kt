package teja.example.mod3casestudy1

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import teja.example.mod3casestudy1.databinding.ActivityResultsBinding

class ResultsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResultsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val score = intent.getIntExtra("score",0)
        val total = intent.getIntExtra("totalQuestions",0)
        val time = intent.getLongExtra("timeTaken",0)

        binding.resultsText.text =
            "Score: $score / $total\nTime Taken: $time seconds"

        binding.restartButton.setOnClickListener {
            finish()
        }
    }
}