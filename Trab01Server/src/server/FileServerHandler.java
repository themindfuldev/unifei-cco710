package server;

import java.io.*;
import java.net.*;
import java.util.*;
import static common.Common.*;

/**
 * Manipula um tratamento de requisição recebida pelo servidor.
 */
public class FileServerHandler implements Runnable
{
   /**
    * Socket da conexão.
    */
   private Socket clientSocket;
   /**
    * Logger das mensagens.
    */
   private Logger logger;

   public FileServerHandler(Socket clientSocket, Logger logger)
   {
      this.clientSocket = clientSocket;
      this.logger = logger;
   }

   public void run()
   {
      // Cria um ArrayList para preencher o log.
      ArrayList<String> entry = new ArrayList<String>();

      // Preenche o log com informações iniciais.
      entry.add("Client address and port = "
            + clientSocket.getInetAddress().getHostAddress() + ":"
            + clientSocket.getPort());
      entry.add("Thread = " + Thread.currentThread().getName());
      entry.add("");

      try
      {
         // Cria os streams de entrada e saída do socket.
         DataInputStream in = new DataInputStream(clientSocket.getInputStream());
         DataOutputStream out = new DataOutputStream(clientSocket
               .getOutputStream());

         // Leitura do opCode.
         byte opCode = in.readByte();

         // Escolha da operação desejada.
         switch (opCode)
         {
            // Criar arquivo.
            case OPCODE_CREATE:
               entry.add("Operation: Create file");

               doCreate(in, out);
               break;

            // Ler arquivo.
            case OPCODE_READ:
               entry.add("Operation: Read file");

               doRead(in, out);
               break;

            // Escrever no arquivo.
            case OPCODE_WRITE:
               entry.add("Operation: Write file");

               doWrite(in, out);
               break;

            // Deletar arquivo.
            case OPCODE_DELETE:
               entry.add("Operation: Delete file");

               doDelete(in, out);
               break;

            default:
               out.writeInt(ERROR_BAD_OPCODE);
               break;
         }

         // Fecha o socket.
         clientSocket.close();

      } catch (IOException e)
      {
         // Escreve a exceção ocorrida.
         entry.add("Exception = " + e.getMessage());
      }

      // Escreve no logger.
      logger.writeEntry(entry);
   }

   /**
    * Realiza a criação de um arquivo.
    * 
    * @param in
    *           Stream de entrada do socket
    * @param out
    *           Stream de saída do socket
    * @throws IOException
    */
   private void doCreate(DataInputStream in, DataOutputStream out)
         throws IOException
   {
      try
      {
         // Leitura do name.
         String name = in.readUTF();

         // Abertura do arquivo.
         File file = new File(name);

         // Criação do arquivo.
         if (file.createNewFile() == true)
            out.writeInt(OK);
         else
            out.writeInt(ERROR_BAD_PARAM);

      } catch (FileNotFoundException e)
      {
         // Resposta: Erro de parâmetros.
         out.writeInt(ERROR_SERVER);
      } catch (IOException ex)
      {
         // Resposta: Erro de E/S.
         out.writeInt(ERROR_IO);
      }
   }

   /**
    * Realiza a escrita de um arquivo.
    * 
    * @param in
    *           Stream de entrada do socket
    * @param out
    *           Stream de saída do socket
    * @throws IOException
    */
   private void doWrite(DataInputStream in, DataOutputStream out)
         throws IOException
   {
      try
      {
         // Leitura do count e name.
         int count = in.readInt();
         String name = in.readUTF();

         // Leitura do data.
         byte[] data = new byte[BUF_SIZE];
         in.read(data);

         // Abertura do stream de escrita no arquivo.
         FileOutputStream writer = new FileOutputStream(new File(name), true);

         // Escrita no arquivo.
         writer.write(data, 0, count);

         // Resposta: OK
         out.writeInt(OK);

         // Fechamento do stream.
         writer.close();
      } catch (FileNotFoundException e)
      {
         // Resposta: Erro de parâmetros.
         out.writeInt(ERROR_SERVER);
      } catch (IOException ex)
      {
         // Resposta: Erro de E/S.
         out.writeInt(ERROR_IO);
      }
   }

   /**
    * Realiza a leitura de um arquivo.
    * 
    * @param in
    *           Stream de entrada do socket
    * @param out
    *           Stream de saída do socket
    * @throws IOException
    */
   private void doRead(DataInputStream in, DataOutputStream out)
         throws IOException
   {
      try
      {
         // Leitura do offset, count e name.
         int offset = in.readInt();
         int count = in.readInt();
         String name = in.readUTF();

         // Abertura do stream de leitura do arquivo.
         FileInputStream reader = new FileInputStream(new File(name));

         // Leitura do arquivo.
         byte[] data = new byte[BUF_SIZE];
         reader.skip(offset);
         int dataLength = reader.read(data, 0, count);

         // Resposta: Tamanho do buffer lido e buffer
         out.writeInt(dataLength);
         out.write(data);

         // Fechamento do stream.
         reader.close();
      } catch (FileNotFoundException ex)
      {
         // Resposta: Erro de arquivo inválido.
         out.writeInt(ERROR_BAD_PARAM);
      } catch (IOException ex)
      {
         // Resposta: Erro de E/S.
         out.writeInt(ERROR_IO);
      }
   }

   /**
    * Realiza a exclusão de um arquivo.
    * 
    * @param in
    *           Stream de entrada do socket
    * @param out
    *           Stream de saída do socket
    * @throws IOException
    */
   private void doDelete(DataInputStream in, DataOutputStream out)
         throws IOException
   {
      try
      {
         // Leitura do name.
         String name = in.readUTF();

         // Abertura do arquivo.
         File file = new File(name);

         // Exclusão do arquivo.
         if (file.delete() == true)
            out.writeInt(OK);
         else
            out.writeInt(ERROR_BAD_PARAM);

      } catch (FileNotFoundException e)
      {
         // Resposta: Erro de parâmetros.
         out.writeInt(ERROR_BAD_PARAM);
      } catch (IOException ex)
      {
         // Resposta: Erro de E/S.
         out.writeInt(ERROR_IO);
      }
   }

}
