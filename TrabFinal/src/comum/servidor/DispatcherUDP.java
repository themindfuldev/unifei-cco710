package comum.servidor;
import java.net.*; // for Socket

public interface DispatcherUDP
{
   public void startDispatching(DatagramSocket socket, Logger logger,
         ProtocolFactoryUDP protoFactory);
}
