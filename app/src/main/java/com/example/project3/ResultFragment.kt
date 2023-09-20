package com.example.project3

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController

// TODO: Rename fragment and its initialization parameters, e.g., ARG_PARAM1 and ARG_PARAM2
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResultFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResultFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var score: String? = null
    private var total: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            score = it.getString("correct")
            total = it.getString("total")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_result, container, false)

        //set score text
        val ScoreTextView: TextView = view.findViewById(R.id.scoreText)
        val scoreText = "Your Score: $score/$total"
        ScoreTextView.text = scoreText

        //set restart button
        val RestartButton = view.findViewById<Button>(R.id.restartButton)
        RestartButton.setOnClickListener {
            val navController = findNavController()
            navController.navigate(R.id.action_resultFragment_to_startFragment)
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
         * @return A new instance of [ResultFragment].
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResultFragment().apply {
                arguments = Bundle().apply {
                    putString("correct", score)
                    putString("total", total)
                }
            }
    }
}
