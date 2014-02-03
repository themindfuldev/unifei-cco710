package common;

public final class Common
{
   // Definições necessárias para a comunicação.
   /**
    * Quantidade máxima de dados transferidos por vez.
    */
   public static final short BUF_SIZE = 1024;

   // Definições de OpCodes.
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

   // Definições de resultados.
   /**
    * Operação executada corretamente.
    */
   public static final int OK = 0;

   /**
    * Operação não identificada.
    */
   public static final int ERROR_BAD_OPCODE = -1;

   /**
    * Erro de parâmetros.
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
