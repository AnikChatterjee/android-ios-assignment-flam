fun solveNQueens(n: Int): List<List<String>> {
    val solutions = mutableListOf<List<String>>()
    val board = Array(n) { CharArray(n) { '.' } }

    fun isSafe(row: Int, col: Int): Boolean {
        for (i in 0 until row) {
            if (board[i][col] == 'Q') return false
        }
        var i = row - 1
        var j = col - 1
        while (i >= 0 && j >= 0) {
            if (board[i][j] == 'Q') return false
            i--; j--
        }
        i = row - 1
        j = col + 1
        while (i >= 0 && j < n) {
            if (board[i][j] == 'Q') return false
            i--; j++
        }
        return true
    }

    fun placeQueens(row: Int) {
        if (row == n) {
            solutions.add(board.map { String(it) })
            return
        }

        for (col in 0 until n) {
            if (isSafe(row, col)) {
                board[row][col] = 'Q'
                placeQueens(row + 1)
                board[row][col] = '.'
            }
        }
    }

    placeQueens(0)
    return solutions
}

fun main() {
    val n = 4
    val results = solveNQueens(n)
    println("Total solutions: ${results.size}")
    for (solution in results) {
        println("---")
        solution.forEach { println(it) }
    }
}
