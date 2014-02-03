package servidorNomes;
import java.net.*;

import comum.servidor.*;

public class Principal
{
   public static void main(String[] args) throws Exception
   {
   	DatagramSocket socket;
   	DispatcherUDP dispatcher;
   	Logger logger;
   	int pos;
   	
      // Validacao dos argumentos.
      if (args.length < 0 || args.length > 3)
         throw new IllegalArgumentException(
               "Parameter(s): -[dl] [<Dispatcher>] [<Log File>]");

   	if (args.length == 0 || args[0].contains("d") == false)
   		dispatcher = new PoolDispatcherUDP();
   	else
   	{
   		pos = args[0].indexOf("d");
   		dispatcher = (DispatcherUDP) Class.forName(
               args[pos] + "DispatcherUDP").newInstance();
   	}
   	
   	if (args.length == 0 || args[0].contains("l"))
   		logger = new ConsoleLogger();	
   	else
   	{
   		pos = args[0].indexOf("l");
         logger = new FileLogger(args[pos]);      		
   	}
      
   	socket = new DatagramSocket(Constantes.PORTA_SERVIDOR_NOMES);
   	
      // In�cio do dispatching
      dispatcher.startDispatching(socket, logger, new NomesProtocolFactory());
      
      System.out.println("Servidor iniciado.");
   }
}
