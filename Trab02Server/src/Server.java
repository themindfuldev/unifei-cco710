import java.net.*;

public class Server
{
   public static void main(String[] args) throws Exception
   {
      // Validação dos argumentos.
      if (args.length < 3 || args.length > 4)
         throw new IllegalArgumentException(
               "Parameter(s): <Port> <Protocol> <Dispatcher> [<Log File>]");

      // Criação do serverSocket.
      int serverPort = Integer.parseInt(args[0]);
      ServerSocket serverSocket = new ServerSocket(serverPort);

      // Criação do logger.
      Logger logger;
      if (args.length == 4)
         logger = new FileLogger(args[1]);
      else
         logger = new ConsoleLogger();

      // Criação do protocolFactory
      String protocolName = args[1];
      ProtocolFactory protoFactory = (ProtocolFactory) Class.forName(
            protocolName + "ProtocolFactory").newInstance();
      
      // Criação do dispatcher
      String dispatcherName = args[2];
      Dispatcher dispatcher = (Dispatcher) Class.forName(
            dispatcherName + "Dispatcher").newInstance();

      // Início do dispatching
      dispatcher.startDispatching(serverSocket, logger, protoFactory);
   }
}
