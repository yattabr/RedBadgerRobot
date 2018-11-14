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
    }

    var sendClick = View.OnClickListener {
        var move = Move()

        move.x = editCoordinateX.text.toString().toInt()
        move.y = editCoordinateY.text.toString().toInt()
        move.heading = spinnerOrientation.selectedItemPosition

        var finalInstruction = editInstruction.text.toString()

        presenter.executeMove(move, finalInstruction)
    }

    var leftClick = View.OnClickListener {
        instruction += "L,"
        editInstruction.setText(instruction)

    }

    var rightClick = View.OnClickListener {
        instruction += "R,"
        editInstruction.setText(instruction)

    }

    var topClick = View.OnClickListener {
        instruction += "F,"
        editInstruction.setText(instruction)
    }

    var hideViewClick = View.OnClickListener {
        resutView.visibility = View.GONE
    }


    override fun resultRobotMove(result: String) {
        txtResult.text = result
        resutView.visibility = View.VISIBLE
    }
}
