package common;

import java.io.*;

public enum MessageOpCode implements Serializable {
	/** 
	 * Criar um novo arquivo.
	 */
	CREATE,
	
	/**
	 * Ler um bloco do arquivo.
	 */
	READ,
	
	/**
	 * Escrever um bloco do arquivo.
	 */
	WRITE,
	
	/**
	 * Apagar um arquivo existente.
	 */
	DELETE
}
