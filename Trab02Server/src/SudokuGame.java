public class SudokuGame
{
   private byte startPositions, errorCounter, fillCounter;
   private byte[][] sudokuProblem;
   private static SudokuGenerator sudokuGenerator;
   
   static
   {
      sudokuGenerator = new SudokuGenerator();
   }   
   
   public SudokuGame(byte startPositions) 
   {
      sudokuProblem = sudokuGenerator.generateProblem((byte) 15);
      this.startPositions = startPositions;
      this.fillCounter = startPositions;
   }
   
   private byte[][] getStartTable()
   {
      byte[][] startTable = new byte[9][9];
      
      for (byte gen=0; gen<startPositions; gen++)
      {
         byte pos, posI, posJ;
         
         do
         {
            pos = (byte) (Math.random()*81);
            posI = (byte) (pos / 9);
            posJ = (byte) (pos % 9);
         } while (startTable[posI][posJ] != 0);         
         
         startTable[posI][posJ] = sudokuProblem[posI][posJ];         
      }      
      
      return startTable;      
   }

   public boolean tryMove(byte posI, byte posJ, byte value)
   {
      if (sudokuProblem[posI][posJ] == value)
      {
         fillCounter++;
         return true;
      }
      
      errorCounter++;      
      return false;
   }

   public Byte checkEnd()
   {
      if (fillCounter == 81)
         return errorCounter;
      
      return null;
   }

   public String writeStartTable()
   {
      StringBuilder sb = new StringBuilder();
      byte[][] startTable = getStartTable();
      
      for (int i=0; i<9; i++)
         for (int j=0; j<9; j++)
            sb.append(String.format("%d%d%d ", i, j, startTable[i][j]));
      
      return sb.toString();
   }
}
