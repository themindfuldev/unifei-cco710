package common;

import java.io.*;

public enum MessageResult implements Serializable {
	/**
	 * Opera��o executada corretamente.
	 */
	OK, 
	
	/**
	 * Opera��o n�o identificada.
	 */
	ERROR_BAD_OPCODE,
	
	/**
	 * Erro de par�metros.
	 */
	ERROR_BAD_PARAM, 
	
	/**
	 * Erro de E/S. 
	 */
	ERROR_IO
}
