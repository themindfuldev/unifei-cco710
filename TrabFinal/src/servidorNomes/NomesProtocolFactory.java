package servidorNomes;
import java.net.*; // for Socket
import comum.servidor.*;

public class NomesProtocolFactory implements ProtocolFactoryUDP
{
   public Runnable createProtocol(DatagramSocket clntSock, DatagramPacket packet, Logger logger)
   {
      return new NomesProtocol(clntSock, packet, logger);
   }
}
