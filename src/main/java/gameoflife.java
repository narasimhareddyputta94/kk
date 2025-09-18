class GameOfLifeJava {
  public static String print(int[][] matrix) {
    StringBuilder result = new StringBuilder();
    for (int[] row : matrix) {
      for (int cell : row) {
        if (cell != 0) result.append('#');
        else result.append(' ');
      }
      result.append("\n");
    }
    return result.toString();
  }
  
  public static class Shape {
    private int[][] pattern;

    public Shape(int[][] pattern) {
      if (pattern.length < 1 || pattern[0].length < 1) throw new IllegalArgumentException();
      this.pattern = pattern;
    }

    public int numRows() { return pattern.length; }
    public int numCols() { return pattern[0].length; }
    public int[][] getPattern() { return pattern; }
  }
  
  public static class Game {
    private int rows;
    private int cols;
    private int[][] board;
    
    public Game(int rows, int cols) {
      this.rows = rows;
      this.cols = cols;
      board = new int[rows][cols];
    }

    public int[][] getBoard() { return board; }

    public void add(Shape s, int row, int col) {
      if ( row + s.numRows() > rows
        || col + s.numCols() > cols) 
          throw new IllegalArgumentException("Shape must fit fully onto game field");
      
      int[][] pattern = s.getPattern();
      for (int i = 0; i < pattern.length; i++) {
        int[] r = pattern[i];
        for (int j = 0; j < r.length; j++) {
          board[row+i][col+j] = r[j];
        }
      }
    }
  
    public void step() {
      int[][] next = new int[rows][cols];
      for (int i = 0; i < rows; i++) {      
        for (int j = 0; j < cols; j++) {
          int alive = 0;
          for (int ni = -1; ni <= 1; ni++) {
            for (int nj = -1; nj <= 1; nj++) {
              if (i+ni>=0 && i+ni<rows 
                    && j+nj>=0 && j+nj<cols
                    && (ni != 0 || nj != 0)) {
                alive += board[i + ni][j + nj];
              }
            }
          }
          
          // Cell is lonely and dies
          if ((board[i][j] == 1) && (alive < 2)) next[i][j] = 0;
          // Cell dies due to over population
          else if ((board[i][j] == 1) && (alive > 3)) next[i][j] = 0;
          // A new cell is born
          else if ((board[i][j] == 0) && (alive == 3)) next[i][j] = 1;
          // Remains the same
          else next[i][j] = board[i][j];        
        }
      }
      board = next;
    }
  }

  public static Shape createBlinker() {
    return new Shape(new int[][] {
      {0, 1, 0},
      {0, 1, 0},
      {0, 1, 0}
    });
  }

  public static Shape createGlider() {
    return new Shape(new int[][] {
      {0, 0, 1},
      {1, 0, 1},
      {0, 1, 1}
    });
  }
    
  public static void main(String[] args) {
    var blinker = createBlinker();    
    var glider = createGlider();
    
    Game g = new Game(20, 80);
    g.add(blinker, 5, 40);
    g.add(glider, 1, 1);

    System.out.println(print(g.board));
    while (true) {
      g.step();
      System.out.println(print(g.board));
      try {
        Thread.sleep(100);
      } catch(InterruptedException e) {}
    }
  }
}
