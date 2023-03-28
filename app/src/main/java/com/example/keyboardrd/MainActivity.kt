package com.example.keyboardrd


import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout


lateinit var et1: EditText
lateinit var et2: EditText
lateinit var et3: EditText
lateinit var doneBtn: ImageButton
lateinit var btnPrevious: ImageButton
lateinit var btnNext: ImageButton
lateinit var constraintLayout: ConstraintLayout

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        et1 = findViewById(R.id.et1)
        et2 = findViewById(R.id.et2)
        et3 = findViewById(R.id.et3)
        doneBtn = findViewById(R.id.done)
        btnNext = findViewById(R.id.next)
        btnPrevious = findViewById(R.id.previous)
        constraintLayout = findViewById(R.id.root)

       keyboardListener(constraintLayout)

        et1.setOnFocusChangeListener(View.OnFocusChangeListener{ v, hasFocus ->
            if(hasFocus){
                showNavigationButtons()
            }
        })

        et2.setOnFocusChangeListener(View.OnFocusChangeListener{ v, hasFocus ->
            if(hasFocus){
                showNavigationButtons()
            }
        })

        et3.setOnFocusChangeListener(View.OnFocusChangeListener{ v, hasFocus ->
            if(hasFocus){
                showNavigationButtons()
            }
        })

        doneBtn.setOnClickListener {
            hideKeyboard(currentFocus!!)
        }

        btnPrevious.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var previousView: View? = currentFocus!!.focusSearch(View.FOCUS_UP)

                while(previousView !is EditText){
                    if (previousView == null) {
                        break;
                    }
                    previousView = currentFocus!!.focusSearch(View.FOCUS_UP)
                }
                previousView!!.requestFocus()
            }
        })

        btnNext.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val nextView: View? = currentFocus!!.focusSearch(View.FOCUS_DOWN)
                if (nextView != null) {
                    nextView.requestFocus()
                }
            }
        })

    }

    private fun showNavigationButtons() {
        val navigationLayout: ConstraintLayout = findViewById(R.id.navigation_layout)
        navigationLayout.visibility = View.VISIBLE
    }

    private fun changeFocus(view: View, position: Int){
        var previousView: View? = currentFocus!!.focusSearch(position)

        while(previousView !is EditText){
            if (previousView == null) {
                break;
            }
            previousView = currentFocus!!.focusSearch(position)
        }
        previousView!!.requestFocus()
    }

    private fun keyboardListener(parent: ViewGroup){
        parent.viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            parent.getWindowVisibleDisplayFrame(r)
            val screenHeight = parent.rootView.height
            val keypadHeight: Int = screenHeight - r.bottom
            if (keypadHeight > screenHeight * 0.15) {
                showButtons()

            } else {
                hideButtons()
            }
        }
    }

    private fun hideButtons(){
        btnNext.visibility = View.INVISIBLE
        btnPrevious.visibility = View.INVISIBLE
        doneBtn.visibility = View.INVISIBLE
    }

    private fun showButtons(){
        btnPrevious.visibility = View.VISIBLE
        btnNext.visibility = View.VISIBLE
        doneBtn.visibility = View.VISIBLE
    }

    private fun hideKeyboard(view: View){
        if (view != null) {
            val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}