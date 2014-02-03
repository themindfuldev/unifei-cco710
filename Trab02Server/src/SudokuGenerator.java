/**
 * The <code>Sudoku</code> class povides a static <code>main</code> method
 * allowing it to be called from the command line to print the solution to a
 * specified Sudoku problem.
 * 
 * <p>
 * See <a href="http://en.wikipedia.org/wiki/Sudoku">Wikipedia: Sudoku</a> for
 * more information on Sudoku.
 * 
 * <p>
 * The algorithm employed is similar to the standard backtracking <a
 * href="http://en.wikipedia.org/wiki/Eight_queens_puzzle">eight queens
 * algorithm</a>.
 * 
 * @version 1.0
 * @author <a href="http://www.colloquial.com/carp">Bob Carpenter</a>
 */
public class SudokuGenerator
{

   /**
    * Print the specified Sudoku problem and its solution. The problem is
    * encoded as specified in the class documentation above.
    * 
    * @param args
    *           The command-line arguments encoding the problem.
    */
   public byte[][] generateProblem(byte start)
   {      
      byte[][] matrix;
      boolean solved;
      
      do
      {
         matrix = generate(start); 
         
         solved = solve((byte) 0, (byte) 0, matrix);
      } while (solved == false);
      
      return matrix;
   }

   private byte[][] generate(byte numbers)
   {
      byte[][] matrix = new byte[9][9];
      
      for (byte gen=0; gen<numbers; gen++)
      {
         byte pos, posI, posJ, value;
         boolean valid;
         
         do
         {
            pos = (byte) (Math.random()*81);
            posI = (byte) (pos / 9);
            posJ = (byte) (pos % 9);
         } while (matrix[posI][posJ] != 0);
         
         do 
         {
            valid = true;
            
            value = (byte) (Math.random()*8 + 1);
            
            for (byte i=0; i<9; i++)
               if (matrix[i][posJ] == value)
                  valid = false;
            
            for (byte j=0; j<9; j++)
               if (matrix[posI][j] == value)
                  valid = false;
            
            byte iMin = (byte) (((byte) posI/3)*3);
            byte jMin = (byte) (((byte) posJ/3)*3);
            for (byte i=iMin; i<iMin+3; i++)
               for (byte j=jMin; j<jMin+3; j++)
                  if (matrix[i][j] == value)
                     valid = false;
            
         } while (valid == false);
         
         matrix[posI][posJ] = value;         
      }

      return matrix;
   }

   private boolean solve(byte i, byte j, byte[][] cells)
   {
      if (i == 9)
      {
         i = 0;
         if (++j == 9)
            return true;
      }
      if (cells[i][j] != 0) // skip filled cells
         return solve((byte) (i + 1), j, cells);

      for (byte val = 1; val <= 9; ++val)
      {
         if (legal(i, j, val, cells))
         {
            cells[i][j] = val;
            if (solve((byte) (i + 1), j, cells))
               return true;
         }
      }
      cells[i][j] = 0; // reset on backtrack
      return false;
   }

   private boolean legal(byte i, byte j, byte val, byte[][] cells)
   {
      for (byte k = 0; k < 9; ++k)
         // row
         if (val == cells[k][j])
            return false;

      for (byte k = 0; k < 9; ++k)
         // col
         if (val == cells[i][k])
            return false;

      byte boxRowOffset = (byte) ((i / 3) * 3);
      byte boxColOffset = (byte) ((j / 3) * 3);
      for (byte k = 0; k < 3; ++k)
         // box
         for (byte m = 0; m < 3; ++m)
            if (val == cells[boxRowOffset + k][boxColOffset + m])
               return false;

      return true; // no violations, so it's legal
   }

}