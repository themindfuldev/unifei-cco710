import java.net.*; // for Socket

public class SudokuProtocolFactory implements ProtocolFactory
{
   public Runnable createProtocol(Socket clntSock, Logger logger)
   {
      return new SudokuProtocol(clntSock, logger);
   }
}
