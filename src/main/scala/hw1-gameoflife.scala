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

  // EXERCISE 1: complete the following definition that returns
  // a Blinker shape.
  //
  // You can look up a Java implementation of this function in  
  //    src/main/java/gameoflife.java#createBlinker
  //
  // Replace ??? with your implementation  
  def createBlinker: Array[Array[Int]] = 
    // TODO: Provide definition here
    ???

  // EXERCISE 2: complete the following definition that returns
  // a Glider shape.
  //
  // You can look up a Java implementation of this function in  
  //    src/main/java/gameoflife.java#createGlider
  //
  // Replace ??? with your implementation  
  def createGlider: Array[Array[Int]] = 
    // TODO: Provide definition here
    ???
  
  // EXERCISE 3: complete the following definition, so that print returns a
  // string that renders the two-dimensional array `matrix` with a character
  // space for a zero entry and '#' for a non-zero entry. Every "line" in 
  // the matrix should be its own line in the string.
  //
  // You can look up a Java implementation of this function in  
  //    src/main/java/gameoflife.java#print
  //
  // Replace ??? with your implementation
  //
  // For example, the array
  //   Array(
  //     Array(1, 0, 0), 
  //     Array(1, 0, 1))
  // should result in the string
  //   "#  \n# #"
  def print(matrix: Array[Array[Int]]) : String = {
    // TODO: Provide definition here
    ???
  }
  
  
  // EXERCISE 4: add a contract that requires the array pattern to 
  // have at least 1 line and at least 1 column. Implement the
  // `numRows` function to return the number of rows in `pattern`,
  // and the `numCols` function to return the number of columns in
  // `pattern`.
  //
  // You can look up a Java implementation of this class in  
  //    src/main/java/gameoflife.java#Shape
  //
  // Replace ??? with your implementation
  class Shape(val pattern: Array[Array[Int]]):
    // TODO: Provide definition here
    ???
    def numRows : Int = ???
    def numCols : Int = ???
  end Shape
  
  // You can look up a Java implementation of this class in  
  //    src/main/java/gameoflife.java#Game
  class Game(val rows: Int, val cols: Int):
    // EXERCISE 5: instantiate an array with `rows` number of rows
    // and `cols` number of columns. The array should have all entries
    // set to 0.
    var board: Array[Array[Int]] = {
      // TODO: Provide definition here
      ???
    } ensuring {
      (result: Array[Array[Int]]) => result.length == rows && result.forall(_.length == cols)
    }

    // You can look up a Java implementation of the `add` function in  
    //    src/main/java/gameoflife.java#Game.add  
    def add(s: Shape, row: Int, col: Int) : Unit = 
      // EXERCISE 6: implement a `require` contract that checks 
      // that that shape `s` fully fits onto the board when added
      // at `row`/`col`.      
      // TODO: Provide definition here
      ???
      
      // EXERCISE 7: implement the `add` function that sets the
      // values of the board starting at the upper left corner
      // indicated by `row` and `col` to the values of shape `s`.
      // TODO: Provide definition here
      ???
    end add

    // EXERCISE 8: implement the `step` function by following the
    // Game of Life rules.
    //
    // You can look up a Java implementation of this function in  
    //    src/main/java/gameoflife.java#Game.step
    //
    // GRADING: 1.5 points
    def step() : Unit = 
      // TODO: Provide definition here
      ???
    end step
  end Game
  
  // BONUS EXERCISE: implement the `fromString` function to read
  // a string into a Shape.
  //
  // For example, the string 
  // """    
  //    | ## 
  //    | ## 
  //    |    """.stripMargin
  // should be parsed into the Block pattern.
  //
  // GRADING: 1 point
  def fromString(s: String): Shape =
    // TODO: Provide definition here
    ???
  end fromString

  def main(args: Array[String]) : Unit =
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
