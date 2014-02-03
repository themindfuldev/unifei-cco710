import java.net.*;

public class Server
{
   public static void main(String[] args) throws Exception
   {
      // Valida��o dos argumentos.
      if (args.length < 3 || args.length > 4)
         throw new IllegalArgumentException(
               "Parameter(s): <Port> <Protocol> <Dispatcher> [<Log File>]");

      // Cria��o do serverSocket.
      int serverPort = Integer.parseInt(args[0]);
      ServerSocket serverSocket = new ServerSocket(serverPort);

      // Cria��o do logger.
      Logger logger;
      if (args.length == 4)
         logger = new FileLogger(args[1]);
      else
         logger = new ConsoleLogger();

      // Cria��o do protocolFactory
      String protocolName = args[1];
      ProtocolFactory protoFactory = (ProtocolFactory) Class.forName(
            protocolName + "ProtocolFactory").newInstance();
      
      // Cria��o do dispatcher
      String dispatcherName = args[2];
      Dispatcher dispatcher = (Dispatcher) Class.forName(
            dispatcherName + "Dispatcher").newInstance();

      // In�cio do dispatching
      dispatcher.startDispatching(serverSocket, logger, protoFactory);
   }
}
