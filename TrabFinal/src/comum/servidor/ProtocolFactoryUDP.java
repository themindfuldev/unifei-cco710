package comum.servidor;
import java.net.*; // for Socket

public interface ProtocolFactoryUDP
{
   public Runnable createProtocol(DatagramSocket clntSock, DatagramPacket packet, Logger logger);
}
