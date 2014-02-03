package comum.servidor;
import java.io.IOException;
import java.net.*; // for Socket, ServerSocket, and InetAddress

public class ThreadPerDispatcherUDP implements DispatcherUDP
{
   public void startDispatching(final DatagramSocket servSock,
         final Logger logger, final ProtocolFactoryUDP protoFactory)
   {
      // Run forever, accepting and spawning threads to service each connection
      for (;;)
      {
      	try
			{
				DatagramPacket packet = new DatagramPacket(new byte[255], 255);
				servSock.receive(packet);
				
				// connection
				Runnable protocol = protoFactory.createProtocol(servSock, packet, logger);
				Thread thread = new Thread(protocol);
				thread.start();
				logger.writeEntry("\nCreated and started Thread = "
				      + thread.getName());
			} catch (IOException e)
			{
				e.printStackTrace();
				logger.writeEntry("Exception = " + e.getMessage());
			}
      }
      /* NOT REACHED */
   }
}