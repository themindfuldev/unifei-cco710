package comum.servidor;


import java.util.*;

public interface Logger
{
   /**
    * Escreve uma collection de linhas.
    * 
    * @param entry
    *           Collection contendo as linhas a serem escritas.
    */
   public void writeEntry(Collection entry);

   /**
    * Escreve uma linha.
    * 
    * @param entry
    *           Linha a ser escrita.
    */
   public void writeEntry(String entry);
}
