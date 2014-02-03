import java.net.*; // for Socket
import java.io.*; // for IOException and Input/OutputStream
import java.util.*; // for ArrayList
import static common.Common.*;

class SudokuProtocol implements Runnable
{
   private static Map<String, SudokuGame> gamesMap;
   
   static
   {
      gamesMap = new HashMap<String, SudokuGame>();
   }

   private Socket clntSock; // Connection socket
   private Logger logger; // Logging facility

   public SudokuProtocol(Socket clntSock, Logger logger)
   {
      this.clntSock = clntSock;
      this.logger = logger;
   }

   public void run()
   {
      ArrayList<String> entry = new ArrayList<String>();
      String host = clntSock.getInetAddress().getHostAddress();
      
      entry.add("Client address and port = " + host + ":" + clntSock.getPort());
      entry.add("Thread = " + Thread.currentThread().getName());

      try
      {
         // Get the input and output I/O streams from socket
         BufferedReader in = new BufferedReader(new InputStreamReader(clntSock.getInputStream()));
         BufferedWriter out = new BufferedWriter(new OutputStreamWriter(clntSock.getOutputStream()));
         
         String message = in.readLine();
         if (message == null) return;
         
         String[] requestMessage = message.split(";");
         
         StringBuilder responseMessage = new StringBuilder();         
         
         byte opCode = Byte.parseByte(requestMessage[0]);
         
         switch (opCode)
         {
            case OPCODE_START:
               byte startPositions = Byte.parseByte(requestMessage[1]);               
               SudokuGame newGame = new SudokuGame(startPositions);               
               gamesMap.put(host, newGame);
               
               responseMessage.append(OK + ";");
               responseMessage.append(newGame.writeStartTable());
                              
               break;
            case OPCODE_MOVE:
               byte posI = Byte.parseByte(requestMessage[1].substring(0, 1));  
               byte posJ = Byte.parseByte(requestMessage[1].substring(1, 2));  
               byte value = Byte.parseByte(requestMessage[1].substring(2, 3));  
               
               SudokuGame game = gamesMap.get(host);
               
               if (game != null)
               {
                  boolean isValid = game.tryMove(posI, posJ, value);
                  
                  if (isValid)
                  {
                     Byte errorCounter = game.checkEnd();
                     if (errorCounter != null)
                     {
                        responseMessage.append(MOVE_ENDGAME + ";");
                        responseMessage.append(errorCounter.toString());
                        gamesMap.remove(host);
                     }
                     else
                        responseMessage.append(MOVE_CORRECT);
                  }                  
                  else
                     responseMessage.append(MOVE_INCORRECT);                  
               }
               
               break;
            default:
               responseMessage.append(ERROR_BAD_OPCODE);               
         }
         
         entry.add("Client finished.");
         out.write(responseMessage.toString());         
      } catch (IOException e)
      {
         entry.add("Exception = " + e.getMessage());
      }

      try
      {
         clntSock.close();
      } catch (IOException e)
      {
         entry.add("Exception = " + e.getMessage());
      }

      logger.writeEntry(entry);
   }
}
