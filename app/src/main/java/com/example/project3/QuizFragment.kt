package com.example.project3

import android.os.Bundle
import com.example.project3.R
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import java.util.Random

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_DIFFICULTY = "difficulty"
private const val ARG_OPERATION = "operation"
private const val ARG_QUESTION_COUNT = "questionCount"

/**
 * A simple [Fragment] subclass.
 * Use the [QuizFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuizFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var difficulty: String? = null
    private var operation: String? = null
    private var questionCount: Int = 0
    private lateinit var questionTextView: TextView
    private lateinit var userAnswer: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            difficulty = it.getString(ARG_DIFFICULTY)
            operation = it.getString(ARG_OPERATION)
            questionCount = it.getInt(ARG_QUESTION_COUNT, 0)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_quiz, container, false)

        questionTextView = view.findViewById(R.id.textView4)
        userAnswer = view.findViewById(R.id.typeAnswerBox)

        // TODO: IMPORT VARIABLES FROM StartFragment (difficulty, operation, questionCount)
        val bundle = arguments
        val difficulty = bundle?.getString("difficulty")
        val operation = bundle?.getString("operation")
        val questionCount = bundle?.getInt("questionCount", 0) ?: 0
        val currentStrQuestion = getString(R.string.EquationText)


        var questions = arrayListOf<String>() // store questions here
        var answers = arrayListOf<Int>() // store answers here
        fun updateQuestion(question: String) {
            // Update the question TextView with the new question
            questionTextView.text = question
        }


        fun generateQuestions(difficulty: String?, operation: String?, questionCount: Int) {

            val random = Random()

            // Define operand limits based on difficulty
            val maxOperand: Int = when (difficulty) {
                "easy" -> 10
                "medium" -> 25
                "hard" -> 50
                else -> 10 // Default to easy if difficulty is not recognized
            }

            for (i in 0 until questionCount) {
                val number1 = random.nextInt(maxOperand + 1)
                val number2 = random.nextInt(maxOperand + 1)
                var question: String = ""
                var answer: Int = 0

                when (operation) {
                    "addition" -> {
                        question = "$number1 + $number2"
                        answer = number1 + number2
                    }
                    "subtraction" -> {
                        question = "$number1 - $number2"
                        answer = number1 - number2
                    }
                    "multiplication" -> {
                        question = "$number1 x $number2"
                        answer = number1 * number2
                    }
                    "division" -> {
                        // Ensure division produces a whole number answer
                        val divisor = if (number2 != 0) number2 else 1
                        val dividend = number1 * divisor
                        question = "$dividend ÷ $divisor"
                        answer = dividend / divisor
                    }
                    else -> {
                        // Default to addition if operation is not recognized
                        // question = "$number1 + $number2"
                        answer = number1 + number2
                    }

                }


                questions.add(question)
                answers.add(answer)

            }

            // Now, 'questions' and 'answers' lists contain the generated questions and answers
            // You can access them using their respective indices.
        }

        generateQuestions(difficulty, operation, questionCount)
        // call generateQuestions()

        updateQuestion(questions[0])

        var currentQuestionNumber = 0 // start with 0 instead of 1 so you can use this for indexing the question/answer

        var wrong = 0 // keep track of questions user gets wrong/right
        var correct = 0

        // now you can do the quiz stuff here.

        // TODO: WHEN QUIZ DONE -- send variables wrong, correct to ResultFragment using SafeArgs
        //done button, navigates to the next screen.

        val doneButton: Button = view.findViewById(R.id.doneButton)
        doneButton.setOnClickListener {
            val userResponse = userAnswer.text.toString()
//
            if (userResponse.isNotEmpty()) {
                val correctAnswer = answers[currentQuestionNumber].toString()
                if (userResponse == correctAnswer) {
                    correct += 1
                } else {
                    wrong += 1
                }
                currentQuestionNumber += 1
//
                if (currentQuestionNumber == questionCount) {
                    val sendBundle = Bundle().apply {
                        putString("correct", correct.toString())
                        putString("total", questionCount.toString())
                    }

                    val navController = findNavController()
                    navController.navigate(R.id.action_quizFragment_to_resultFragment, sendBundle)
                } else {
                    // Update the question for the next round
                    userAnswer.text = ""
                    updateQuestion(questions[currentQuestionNumber])
            }
            }
        }



        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuizFragment.
         */
        // TODO: Rename and change types and the number of parameters
        @JvmStatic
        fun newInstance(difficulty: String, operation: String, questionCount: Int) =
            QuizFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_DIFFICULTY, difficulty)
                    putString(ARG_OPERATION, operation)
                    putInt(ARG_QUESTION_COUNT, questionCount)
                }
            }
    }
}
