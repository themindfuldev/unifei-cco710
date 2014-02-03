package server;

import java.io.*;
import java.net.*;

public class FileServer
{
   /**
    * Tamanho padr�o do thread-pool.
    */
   static final String NUMTHREADS = "4";
   /**
    * Nome da propriedade do sistema para o n�mero de threads.
    */
   static final String THREADPROP = "Threads";

   public static void main(String[] args) throws IOException,
         ClassNotFoundException
   {
      // Valida��o dos argumentos.
      if (args.length < 1 || args.length > 2)
         throw new IllegalArgumentException("Parameter(s): <Port> [<Log File>]");

      // Cria��o do serverSocket.
      int serverPort = Integer.parseInt(args[0]);
      final ServerSocket serverSocket = new ServerSocket(serverPort);

      // Cria��o do logger.
      final Logger logger;
      if (args.length == 2)
         logger = new FileLogger(args[1]);
      else
         logger = new ConsoleLogger();

      // Obt�m o tamanho do thread-pool.
      int numThreads = Integer.parseInt(System.getProperty(THREADPROP,
            NUMTHREADS));

      // Cria N-1 threads, cada uma executando o servidor iterativo.
      for (int i = 0; i < (numThreads - 1); i++)
      {
         Thread thread = new Thread()
         {
            public void run()
            {
               dispatchLoop(serverSocket, logger);
            }
         };
         thread.start();
         logger.writeEntry("Created and started Thread = " + thread.getName());
      }
      logger.writeEntry("Iterative server starting in main thread = "
            + Thread.currentThread().getName());

      // Usa a thread principal como o n-�simo servidor iterativo.
      dispatchLoop(serverSocket, logger);
   }

   /**
    * Servidor iterativo.
    * 
    * @param serverSocket
    * @param logger
    */
   private static void dispatchLoop(ServerSocket serverSocket, Logger logger)
   {
      // Executa continuamente, manipulando cada conex�o.
      while (true)
      {
         try
         {
            // Aguarda por conex�o.
            Socket clientSocket = serverSocket.accept();

            // Inicia o handler.
            Runnable handler = new FileServerHandler(clientSocket, logger);
            handler.run();
         } catch (IOException e)
         {
            logger.writeEntry("Exception = " + e.getMessage());
         }
      }
   }
}
