package comum.servidor;
import java.net.*; // for Socket

public interface ProtocolFactoryTCP
{
   public Runnable createProtocol(Socket clntSock, Logger logger);
}
