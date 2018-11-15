package br.com.wobbu.redbadgerrobot

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity(), MainView {
    lateinit var editCoordinateX: EditText
    lateinit var editCoordinateY: EditText
    lateinit var editInstruction: EditText
    lateinit var spinnerOrientation: Spinner
    lateinit var btTop: ImageButton
    lateinit var btLeft: ImageButton
    lateinit var btRight: ImageButton
    lateinit var btSend: Button
    lateinit var btClean: Button
    lateinit var txtResult: TextView
    lateinit var resutView: RelativeLayout

    var instruction = ""
    lateinit var presenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editCoordinateX = findViewById(R.id.edit_coordinate_X)
        editCoordinateY = findViewById(R.id.edit_coordinate_Y)
        editInstruction = findViewById(R.id.edit_instruction)
        spinnerOrientation = findViewById(R.id.spinner_orientation)
        btTop = findViewById(R.id.bt_top)
        btLeft = findViewById(R.id.bt_left)
        btRight = findViewById(R.id.bt_right)
        btSend = findViewById(R.id.bt_send)
        btClean = findViewById(R.id.bt_clean)
        txtResult = findViewById(R.id.txt_result)
        resutView = findViewById(R.id.result_view)

        var orientationAdapter = ArrayAdapter.createFromResource(this, R.array.orientation, android.R.layout.simple_dropdown_item_1line)
        spinnerOrientation.adapter = orientationAdapter

        presenter = MainPresenter(this)

        btSend.setOnClickListener(sendClick)
        btTop.setOnClickListener(topClick)
        btRight.setOnClickListener(rightClick)
        btLeft.setOnClickListener(leftClick)
        resutView.setOnClickListener(hideViewClick)
        btClean.setOnClickListener(cleanClick)
    }

    // Presenter send return to this method to update the view.
    override fun resultRobotMove(result: String) {
        txtResult.text = result
        resutView.visibility = View.VISIBLE
    }

//    ===========---------- ON CLICK -----------================

    // Click prepare the move to presenter
    var sendClick = View.OnClickListener {
        var move = Move()

        var x = editCoordinateX.text.toString()
        var y = editCoordinateY.text.toString()

        if (x.isEmpty()) {
            x = "0"
        }

        if (y.isEmpty()) {
            y = "0"
        }

        move.x = x.toInt()
        move.y = y.toInt()
        move.heading = spinnerOrientation.selectedItemPosition

        var finalInstruction = editInstruction.text.toString()

        presenter.executeMove(move, finalInstruction)
    }

    // Move to left when clicked
    var leftClick = View.OnClickListener {
        instruction += "L "
        editInstruction.setText(instruction)

    }

    // Move to right when clicked
    var rightClick = View.OnClickListener {
        instruction += "R "
        editInstruction.setText(instruction)

    }

    // Move to forward when clicked
    var topClick = View.OnClickListener {
        instruction += "F "
        editInstruction.setText(instruction)
    }

    // Hide the result view
    var hideViewClick = View.OnClickListener {
        resutView.visibility = View.GONE
    }

    // Clean all instructions
    var cleanClick = View.OnClickListener {
        editInstruction.setText("")
        instruction = ""
    }
}
