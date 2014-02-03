package agencia.servidor;

import agencia.cliente.controle.*;
import comum.objetos.*;
import comum.servidor.*;
import comum.servidor.Constantes.*;
import java.io.*;
import java.net.*;
import java.util.*; 

/**
 * @author  tiago
 */
public class AgenciaProtocol implements Runnable
{
	private static ServidorAgencia servidorAgencia;
	
   private Socket clntSock; // Connection socket
   private Logger logger; // Logging facility

   public AgenciaProtocol(Socket clntSock, Logger logger)
   {
      this.clntSock = clntSock;
      this.logger = logger;
   }

   public void run()
   {
   	ObjectInputStream in;
   	ObjectOutputStream out;
      ArrayList<String> entry = new ArrayList<String>();
      String[] listagem;
      String host = clntSock.getInetAddress().getHostAddress();
      ObjetoMovel objeto;
      int posicao;
   	byte opCode = 0;
      
      entry.add("Client address and port = " + host + ":" + clntSock.getPort());
      entry.add("Thread = " + Thread.currentThread().getName());

      try
      {
         // Get the input and output I/O streams from socket
         in = new ObjectInputStream(clntSock.getInputStream());
         out = new ObjectOutputStream(clntSock.getOutputStream());
         
         opCode = in.readByte();
         
         switch (opCode)
         {
            case LISTAGEM_OBJETOS:
            	listagem = servidorAgencia.getMediator().listarObjetos();
            	out.writeObject(listagem);
            	clntSock.close();
               break;
               
            case DESPACHAR:
            	objeto = (ObjetoMovel) in.readObject();
            	servidorAgencia.getMediator().inserirObjeto(objeto);
            	clntSock.close();
               break;

            case RETRAIR:
            	posicao = in.readInt();
            	objeto = servidorAgencia.getMediator().retirarObjeto(posicao);
            	out.writeObject(objeto);
            	clntSock.close();
               break;

            case ATUALIZAR:
            	listagem = (String[]) in.readObject();
            	clntSock.close();
            	servidorAgencia.atualizar(listagem);
               break;
               
            default:
               //responseMessage.append(ERROR_BAD_OPCODE);               
         }
         
         entry.add("Client finished.");
         //out.write(responseMessage.toString());         
      } catch (Exception e)
      {
         entry.add("Exception = " + e.getMessage());
      }

      logger.writeEntry(entry);
   }
   
	/**
	 * @param servidorAgencia  the servidorAgencia to set
	 * @uml.property  name="servidorAgencia"
	 */
	public static void setServidorAgencia(ServidorAgencia servidorAgencia) 
	{
		AgenciaProtocol.servidorAgencia = servidorAgencia;
	}

}
