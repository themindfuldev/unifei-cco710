package client;

import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static common.Common.*;

public class CopyClient
{
   public static void main(String[] args)
   {
      // Cria um ArrayList para preencher o log.
      ArrayList<String> entry = new ArrayList<String>();

      try
      {
         // Valida��o dos argumentos.
         if ((args.length < 3) || (args.length > 4))
            throw new IllegalArgumentException(
                  "Parameter(s): <Server> <FileSource> <FileDestiny> [<Port>]");

         // Declara��o de vari�veis.
         Socket socket;
         DataInputStream in;
         DataOutputStream out;
         byte[] data = new byte[BUF_SIZE];
         int resultRead, resultWrite;

         // Obt�m o caminho do servidor.
         String server = args[0];

         // Obt�m a porta.
         int servPort = (args.length == 4) ? Integer.parseInt(args[3]) : 7;

         // Obt�m os nomes dos arquivos Origem e Destino.
         String nameSource = args[1];
         String nameDestiny = args[2];

         // Posi��o corrente do arquvo.
         int position = 0;

         // Itera enquanto n�o houver erro, nem finalizar o processo.
         do
         {
            // Cria um socket para a comunica��o e obt�m os streams de E/S.
            socket = new Socket(server, servPort);
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            // Envia o opcode, position, count e name.
            out.writeByte(OPCODE_READ);
            out.writeInt(position);
            out.writeInt(BUF_SIZE);
            out.writeUTF(nameSource);

            // Recebe o result.
            resultRead = in.readInt();
            switch (resultRead)
            {
               // Erro no opcode.
               case ERROR_BAD_OPCODE:
                  entry.add("Error = Bad opcode");
                  resultWrite = resultRead;
                  break;

               // Erro no nome do arquivo.
               case ERROR_BAD_PARAM:
                  entry.add("Error = Bad file name");
                  resultWrite = resultRead;
                  break;

               // Erro de E/S.
               case ERROR_IO:
                  entry.add("Error = I/O error");
                  resultWrite = resultRead;
                  break;

               // Erro do servidor:
               case ERROR_SERVER:
                  entry.add("Error = Server error");
                  resultWrite = resultRead;
                  break;

               // Sucesso.
               default:
                  in.read(data, 0, resultRead);

                  // Cria um socket para a comunica��o e obt�m os streams de
                  // E/S.
                  socket = new Socket(server, servPort);
                  in = new DataInputStream(socket.getInputStream());
                  out = new DataOutputStream(socket.getOutputStream());

                  // Envia o opcode, position, count e name.
                  out.writeByte(OPCODE_WRITE);
                  out.writeInt(resultRead);
                  out.writeUTF(nameDestiny);
                  out.write(data);

                  // Incrementa position.
                  position += resultRead;

                  // Recebe o resultado.
                  resultWrite = in.readInt();
            }

         } while (resultWrite == OK && resultRead == BUF_SIZE);

         // Fecha o socket.
         socket.close();
      } catch (NumberFormatException e)
      {
         // Escreve a exce��o ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (IllegalArgumentException e)
      {
         // Escreve a exce��o ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (UnknownHostException e)
      {
         // Escreve a exce��o ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (IOException e)
      {
         // Escreve a exce��o ocorrida.
         entry.add("Exception = " + e.getMessage());
      }

      for (String line : entry)
         System.out.println(line);

   }
}
