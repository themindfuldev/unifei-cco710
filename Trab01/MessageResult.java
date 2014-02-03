package common;

import java.io.*;

public enum MessageResult implements Serializable {
	/**
	 * Operação executada corretamente.
	 */
	OK, 
	
	/**
	 * Operação não identificada.
	 */
	ERROR_BAD_OPCODE,
	
	/**
	 * Erro de parâmetros.
	 */
	ERROR_BAD_PARAM, 
	
	/**
	 * Erro de E/S. 
	 */
	ERROR_IO
}
