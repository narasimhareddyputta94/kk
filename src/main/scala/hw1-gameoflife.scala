// GENERATED
/* INSTRUCTIONS
 *
 * This first assignment makes you familiar with the Scala programming
 * language by translating Java code to Scala.
 * 
 * An overview of Scala can be found at 
 * https://docs.scala-lang.org/scala3/book/introduction.html
 * 
 * The assignment implements Conway's Game of Life, a cellular automation
 * of a zero-player game that evolves in rounds (the game's outcome is 
 * determined by the initial state of the game): 
 * https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life
 * 
 * 
 * Complete the exercises below.  For each "EXERCISE" comment, add code
 * immediately below the comment.
 *
 * Please see README.md for instructions, including compilation and testing.
 *
 * GRADING
 *
 * 1. Submissions MUST compile using SBT with UNCHANGED configuration and tests
 *    with no compilation errors.  Submissions with compilation errors will
 *    receive 0 points.  Note that refactoring the code will cause the tests to
 *    fail.
 *
 * 2. You MUST NOT edit the SBT configuration and tests.  Altering it in your
 *    submission will result in 0 points for this assignment.
 *
 * 3. You may declare auxiliary functions if you like.
 *
 * SUBMISSION
 *
 * 1. Submit this file on D2L before the deadline.
 *
 * RUBRIC
 * Total number of points: 8
 * Number of questions: 8
 * Points per exercise
 *    Exercise 1 (Blinker): 0.5
 *    Exercise 2 (Glider):  0.5
 *    Exercise 3 (print):   1.0
 *    Exercise 4 (Shape):   0.5 
 *    Exercise 5 (board):   0.5 
 *    Exercise 6 (contract):0.5 
 *    Exercise 7 (add):     1.0 
 *    Exercise 8 (step):    1.5 
 *    Bonus Exercise:       1.0
 * Code clarity and style: 2 points
 * Does not compile: 0 points
 */

import contracts.*
object GameOfLife:

  // EXERCISE 1: blinker (vertical 3x3, middle col alive)
  def createBlinker: Array[Array[Int]] =
    Array(
      Array(0, 1, 0),
      Array(0, 1, 0),
      Array(0, 1, 0)
    )

  // EXERCISE 2: glider (classic 3x3)
  def createGlider: Array[Array[Int]] =
    Array(
      Array(0, 0, 1),
      Array(1, 0, 1),
      Array(0, 1, 1)
    )

  // EXERCISE 3: render board
  // 0 -> space, non-zero -> '#'
  // keep a newline for empty rows; no extra newline at end unless last row is empty
  def print(matrix: Array[Array[Int]]) : String =
    if matrix.isEmpty then ""
    else
      val rows = matrix.map(row => row.map(v => if v == 0 then ' ' else '#').mkString)
      val withNL = rows.map(_ + "\n").mkString
      if rows.lastOption.exists(_.nonEmpty) then withNL.dropRight(1) else withNL

  // EXERCISE 4: shape must be at least 1x1
  class Shape(val pattern: Array[Array[Int]]):
    require(pattern.nonEmpty && pattern(0).nonEmpty, "pattern must be at least 1x1")
    def numRows: Int = pattern.length
    def numCols: Int = pattern(0).length
  end Shape

  // game board
  class Game(val rows: Int, val cols: Int):
    // EXERCISE 5: init zeros, ensure dims
    var board: Array[Array[Int]] =
      Array.fill(rows)(Array.fill(cols)(0))
        .ensuring(res => res.length == rows && res.forall(_.length == cols))

    // EXERCISE 6+7: place a shape at (row, col)
    def add(s: Shape, row: Int, col: Int): Unit =
      require(
        row >= 0 && col >= 0 &&
        row + s.numRows <= rows &&
        col + s.numCols <= cols,
        "shape does not fit on board at this place"
      )
      for
        r <- 0 until s.numRows
        c <- 0 until s.numCols
      do
        board(row + r)(col + c) = s.pattern(r)(c)

    // EXERCISE 8: one life step
    def step(): Unit =
      inline def inBounds(r: Int, c: Int) = r >= 0 && r < rows && c >= 0 && c < cols
      val nbrs = for dr <- -1 to 1; dc <- -1 to 1 if !(dr == 0 && dc == 0) yield (dr, dc)

      def countN(r: Int, c: Int): Int =
        nbrs.count { case (dr, dc) =>
          val rr = r + dr; val cc = c + dc
          inBounds(rr, cc) && board(rr)(cc) != 0
        }

      board = Array.tabulate(rows, cols) { (r, c) =>
        val alive = board(r)(c) != 0
        val n = countN(r, c)
        if alive && (n == 2 || n == 3) then 1
        else if !alive && n == 3 then 1
        else 0
      }
  end Game

  // BONUS: parse multi-line string into a Shape
  def fromString(s: String): Shape =
    val lines = s.stripMargin.split("\n", -1).toIndexedSeq
    val width = if lines.isEmpty then 0 else lines.map(_.length).max
    val arr =
      if lines.isEmpty then Array(Array(0))
      else
        lines
          .map(_.padTo(width, ' '))
          .map(_.map(ch => if ch == '#' then 1 else 0).toArray)
          .toArray
    new Shape(arr)
  end fromString

  // demo
  def main(args: Array[String]): Unit =
    val blinker = new Shape(createBlinker)
    val glider = new Shape(createGlider)

    val game = new Game(20, 80)
    game.add(blinker, 5, 40)
    game.add(glider, 1, 1)

    println(print(game.board))
    while true do
      game.step()
      println(print(game.board))
      Thread.sleep(100)
    end while
  end main

end GameOfLife