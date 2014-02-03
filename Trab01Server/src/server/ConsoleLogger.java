package server;

import java.util.*;

/**
 * Faz o log no console.
 */
class ConsoleLogger implements Logger
{
   public synchronized void writeEntry(Collection entry)
   {
      for (Iterator line = entry.iterator(); line.hasNext();)
         System.out.println(line.next());
      System.out.println();
   }

   public synchronized void writeEntry(String entry)
   {
      System.out.println(entry);
   }
}