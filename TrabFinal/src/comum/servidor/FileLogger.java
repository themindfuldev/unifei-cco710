package comum.servidor;


import java.io.*;
import java.util.*;

/**
 * Faz o log em um arquivo.
 */
public class FileLogger implements Logger
{
   /**
    * Stream de escrita no arquivo.
    */
   private PrintWriter out;

   public FileLogger(String filename) throws IOException
   {
      // Cria o stream de escrita no arquivo.
      out = new PrintWriter(new FileWriter(filename), true);
   }

   public synchronized void writeEntry(Collection entry)
   {
      for (Iterator line = entry.iterator(); line.hasNext();)
         out.println(line.next());
      out.println();
   }

   public synchronized void writeEntry(String entry)
   {
      out.println(entry);
   }
}