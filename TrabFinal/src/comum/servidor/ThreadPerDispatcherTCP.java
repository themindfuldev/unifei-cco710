package comum.servidor;
import java.net.*; // for Socket, ServerSocket, and InetAddress
import java.io.*; // for IOException and Input/OutputStream

public class ThreadPerDispatcherTCP implements DispatcherTCP
{

   public void startDispatching(ServerSocket servSock, Logger logger,
         ProtocolFactoryTCP protoFactory)
   {
      // Run forever, accepting and spawning threads to service each connection
      for (;;)
      {
         try
         {
            Socket clntSock = servSock.accept(); // Block waiting for
                                                   // connection

            Runnable protocol = protoFactory.createProtocol(clntSock, logger);
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