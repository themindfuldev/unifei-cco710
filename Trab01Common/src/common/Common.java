package common;

public final class Common
{
   // Defini��es necess�rias para a comunica��o.
   /**
    * Quantidade m�xima de dados transferidos por vez.
    */
   public static final short BUF_SIZE = 1024;

   // Defini��es de OpCodes.
   /**
    * Criar um novo arquivo.
    */
   public static final byte OPCODE_CREATE = 1;

   /**
    * Ler um bloco do arquivo.
    */
   public static final byte OPCODE_READ = 2;

   /**
    * Escrever um bloco do arquivo.
    */
   public static final byte OPCODE_WRITE = 3;

   /**
    * Apagar um arquivo existente.
    */
   public static final byte OPCODE_DELETE = 4;

   // Defini��es de resultados.
   /**
    * Opera��o executada corretamente.
    */
   public static final int OK = 0;

   /**
    * Opera��o n�o identificada.
    */
   public static final int ERROR_BAD_OPCODE = -1;

   /**
    * Erro de par�metros.
    */
   public static final int ERROR_BAD_PARAM = -2;

   /**
    * Erro de E/S.
    */
   public static final int ERROR_IO = -3;

   /**
    * Erro do servidor.
    */
   public static final int ERROR_SERVER = -4;
}
