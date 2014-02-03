package agencia.cliente.visao;

import java.net.*;

import comum.servidor.*;
import agencia.cliente.controle.*;
import agencia.servidor.*;

public class Principal
{
	public static void main(String[] args) throws Exception
	{
		ServidorAgencia servidorAgencia;
		MediatorPrincipal mediator;
   	final ServerSocket socket;
   	final DispatcherTCP dispatcher;
   	final Logger logger;
   	int pos;
   	
      // Validacao dos argumentos.
      if (args.length < 0 || args.length > 3)
         throw new IllegalArgumentException(
               "Parameter(s): -[dl] [<Dispatcher>] [<Log File>]");

   	if (args.length == 0 || args[0].contains("d") == false)
   		dispatcher = new ThreadPerDispatcherTCP();
   	else
   	{
   		pos = args[0].indexOf("d");
   		dispatcher = (DispatcherTCP) Class.forName(
               args[pos] + "DispatcherTCP").newInstance();
   	}
   	
   	if (args.length == 0 || args[0].contains("l"))
   		logger = new ConsoleLogger();	
   	else
   	{
   		pos = args[0].indexOf("l");
         logger = new FileLogger(args[pos]);      		
   	}
      
   	socket = new ServerSocket(Constantes.PORTA_AGENTES);
   	
      // In�cio do dispatching
   	new Thread() {
   		public void run() {
   			dispatcher.startDispatching(socket, logger, new AgenciaProtocolFactory());		
   		}
   	}.start();

		servidorAgencia = new ServidorAgencia();
		AgenciaProtocol.setServidorAgencia(servidorAgencia);
		
		mediator = new MediatorPrincipal(servidorAgencia);
		new JanelaPrincipal(mediator, InetAddress.getLocalHost().getHostName());
      
	}

}
