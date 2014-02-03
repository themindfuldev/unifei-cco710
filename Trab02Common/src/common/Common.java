package common;

public final class Common
{
   // Defini��es de OpCodes.
   /**
    * Iniciar um novo jogo.
    */
   public static final byte OPCODE_START = 1;

   /**
    * Tentar um movimento.
    */
   public static final byte OPCODE_MOVE = 2;

   // Defini��es de resultados.
   /**
    * Tentativa de movimento correto.
    */
   public static final byte MOVE_CORRECT = 3;
   
   /**
    * Tentativa de movimento incorreto.
    */
   public static final byte MOVE_INCORRECT = 4;

   /**
    * Fim de jogo.
    */
   public static final byte MOVE_ENDGAME = 5;

   // Defini��es de respostas.
   /**
    * Opera��o executada corretamente.
    */
   public static final byte OK = 0;   
   
   /**
    * Opera��o n�o identificada.
    */
   public static final byte ERROR_BAD_OPCODE = -1;

   /**
    * Erro de par�metros.
    */
   public static final byte ERROR_BAD_PARAM = -2;

   /**
    * Erro de E/S.
    */
   public static final byte ERROR_IO = -3;

   /**
    * Erro do servidor.
    */
   public static final byte ERROR_SERVER = -4;
}
