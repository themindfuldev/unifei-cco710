package comum.servidor;
import java.net.*; // for Socket

public interface DispatcherTCP
{
   public void startDispatching(ServerSocket servSock, Logger logger,
         ProtocolFactoryTCP protoFactory);
}
