package agencia.servidor;
import java.net.*; // for Socket
import comum.servidor.*;

public class AgenciaProtocolFactory implements ProtocolFactoryTCP
{
   public Runnable createProtocol(Socket clntSock, Logger logger)
   {
      return new AgenciaProtocol(clntSock, logger);
   }
}
