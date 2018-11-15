package br.com.wobbu.redbadgerrobot

class MainPresenter(var mainView: MainView) {

    lateinit var orientation: ArrayList<String>
    private var moves = ArrayList<Move>()
    var directions = arrayOf("N", "S", "E", "W")

    // Set bounds from mars
    fun analysisBound(move: Move): Boolean {
        var bounds = Bounds()
        var inBound = ((move.x >= bounds.minX && move.x <= bounds.maxX) && (move.y >= bounds.minY && move.y <= bounds.maxY))
        return inBound

    }

    // Turn directions when recivied L or R
    fun turnDirections(move: Move, direction: Int) {
        var heading = move.heading + direction

        if (heading < 0) {
            heading = 3
        } else if (heading > 3) {
            heading = 0
        }

        move.heading = directions.indexOf(directions[heading])
        move.direction = directions[heading]

        addMove(move)
    }

    // Move robot to one direction
    fun move(move: Move) {
        if (moves.isEmpty()) {
            var newMove = Move()
            newMove.x = 0
            newMove.y = 0
            newMove.heading = 0
            newMove.lost = false

            moves.add(move)
        }

        var lastMove = moves[moves.size - 1]

        if (lastMove.heading.equals("N")) {
            move.y = lastMove.y + 1
        } else if (lastMove.heading.equals("S")) {
            move.y = lastMove.y - 1
        } else if (lastMove.heading.equals("E")) {
            move.x = lastMove.x + 1
        } else if (lastMove.heading.equals("W")) {
            move.x = lastMove.y - 1
        }

        addMove(move)
    }

    // Add coordinates to move array
    // verify if robot is lost
    fun addMove(move: Move) {
        if (move.lost) {
            return
        }

        if (analysisBound(move)) {
            moves.add(move)
            move.lost = false
        } else {
            move.lost = true
            moves.add(move)
        }
    }

    // Execute a move and when get a result, return to MainActivity using a Interface MainView
    fun executeMove(move: Move, instuctions: String) {
        var res = ""
        if (instuctions.length > 100) {
            mainView.resultRobotMove("All instruction strings will be less than 100 characters in length")
            return
        }
        instuctions.split(" ").forEach {
            if (it.equals("L")) {
                turnDirections(move, -1)
            } else if (it.equals("R")) {
                turnDirections(move, 1)
            } else if (it.equals("F")) {
                move(move)
            }
        }

        var lastMove = moves[moves.size - 1]

        if (lastMove.lost) {
            res = "LOST"
        } else {
            res = "${lastMove.x} ${lastMove.y} ${lastMove.direction}"
        }

        mainView.resultRobotMove(res)
    }
}