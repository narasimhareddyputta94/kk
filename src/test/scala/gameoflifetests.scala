import org.scalatest.*
import org.scalatest.prop.TableDrivenPropertyChecks.*

class gameoflifetests extends UnitSpec:
  val EX : Map[Int, Tag] =
    (for i <- (1 to 10).toList yield {
      object T extends Tag ("golex%02d".format (i))
      (i, T)
    }).toMap

  import GameOfLife.*

  private val gliderPattern = Array(
    Array(0, 0, 1),
    Array(1, 0, 1),
    Array(0, 1, 1)
  )

  private val blinkerPattern = Array(
    Array(0, 1, 0),
    Array(0, 1, 0),
    Array(0, 1, 0)
  )

  property ("EX01 - Create Blinker", EX (1)) {
    createBlinker should be (blinkerPattern)
  }

  property ("EX02 - Create Glider", EX (2)) {
    createGlider should be (gliderPattern)
  }

  property ("EX03 - Print Matrix", EX (3)) {    
    print(gliderPattern) should be 
      """  #
        |# #
        | ##""".stripMargin
    
    val full = Array(
      Array(1, 1, 1),
      Array(1, 1, 1),
      Array(1, 1, 1)
    )
    print(full) should be 
      """###
        |###
        |###""".stripMargin

    val empty = Array(
      Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0)
    )
    print(empty) should be 
      """   
        |   
        |   """.stripMargin
    
    print(Array()) should be ("")
    print(Array(Array[Int]())) should be ("\n")
    print(Array(Array[Int](), Array[Int]())) should be ("\n\n")
  }

  property ("EX04 - Create a Shape", EX (4)) {
    val s = new Shape(Array(
      Array(0, 0),
      Array(1, 0),
      Array(0, 1)
    ))
    s.numRows should be (3)
    s.numCols should be (2)

    a [IllegalArgumentException] should be thrownBy {
      new Shape(Array())
    }

    a [IllegalArgumentException] should be thrownBy {
      new Shape(Array(Array[Int]()))
    }
  }

  property ("EX05 - Set Up Board Game", EX (5)) {
    val g = new Game(4, 3)
    g.board should be (Array(
      Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0),
      Array(0, 0, 0)
    ))
  }
  
  property ("EX06 - Complain about Off-Board Adding", EX (6)) {
    val g = new Game(10, 9)
    a [IllegalArgumentException] should be thrownBy {
      g.add(new Shape(gliderPattern), 9, 0)
    }
  }

  property ("EX07 - Set Up Board", EX (7)) {
    val g = new Game(10, 9)    
    g.add(new Shape(gliderPattern), 2, 3)
    g.add(new Shape(blinkerPattern), 7, 0)
    g.board should be (Array(
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 1, 1, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 1, 0, 0, 0, 0, 0, 0, 0)
    ))
  }

  property ("EX08 - Step Board", EX (8)) {
    val jg = new GameOfLifeJava.Game(20, 80)
    jg.add(new GameOfLifeJava.Shape(gliderPattern), 3, 10)
    jg.add(new GameOfLifeJava.Shape(blinkerPattern), 15, 5)

    val g = new Game(20, 80)
    g.add(new Shape(gliderPattern), 3, 10)
    g.add(new Shape(blinkerPattern), 15, 5)

    // step a couple of times
    jg.step()
    g.step()
    g.board should be (jg.getBoard())

    jg.step()
    g.step()
    g.board should be (jg.getBoard())

    jg.step()
    g.step()
    g.board should be (jg.getBoard())

    jg.step()
    g.step()
    g.board should be (jg.getBoard())

    // step another random number of times
    val steps = new java.util.Random().nextInt(100)
    for i <- 0 to steps do {
      jg.step()
      g.step()
    }

    g.board should be (jg.getBoard())
  }

  property ("BONUS EXERCISE - Read Shape from String", EX (9)) {
    val blockString =
      """    
        | ## 
        | ## 
        |    """.stripMargin
    val blockPattern = Array(
      Array(0, 0, 0, 0),
      Array(0, 1, 1, 0),
      Array(0, 1, 1, 0),
      Array(0, 0, 0, 0)
    )
    fromString(blockString).pattern should be (blockPattern)
  }

end gameoflifetests
