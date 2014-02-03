import java.io.*;
import java.net.*;
import java.util.ArrayList;

import static common.Common.*;

public class CopyClient
{
/*   public static void main(String[] args)
   {
      // Cria um ArrayList para preencher o log.
      ArrayList<String> entry = new ArrayList<String>();

      try
      {


         // Declarao de variveis.
         Socket socket;
         BufferedReader in;
         BufferedWriter out;
         int resultRead, resultWrite;

         // Obtm o caminho do servidor.
         String server = args[0];

         // Obtm a porta.
         int servPort = (args.length == 4) ? Integer.parseInt(args[3]) : 7;

         // Obtm os nomes dos arquivos Origem e Destino.
         String nameSource = args[1];
         String nameDestiny = args[2];

         // Posio corrente do arquvo.
         int position = 0;

         // Itera enquanto no houver erro, nem finalizar o processo.
         do
         {
            // Cria um socket para a comunicao e obtm os streams de E/S.
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

                  // Cria um socket para a comunicao e obtm os streams de
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
         // Escreve a exceo ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (IllegalArgumentException e)
      {
         // Escreve a exceo ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (UnknownHostException e)
      {
         // Escreve a exceo ocorrida.
         entry.add("Exception = " + e.getMessage());
      } catch (IOException e)
      {
         // Escreve a exceo ocorrida.
         entry.add("Exception = " + e.getMessage());
      }

      for (String line : entry)
         System.out.println(line);

   }*/
}
