package common;

import java.io.*;

@SuppressWarnings("serial")
public class Message implements Serializable
{	
	// Atributos
	/**
	 * Código da operação.
	 */
	private MessageOpCode opcode;

	/**
	 * Quantidade de bytes para transferir.
	 */
	private int count;

	/**
	 * Início da entrada do arquivo.
	 */
	private int offset;

	/**
	 * Resultado da operação.
	 */
	private MessageResult result;

	/**
	 * Nome do arquivo.
	 */
	private String name;

	/**
	 * Dadps a serem lidos/escritos.
	 */
	private byte[] data;
	
	private int dataLength;

	// Métodos
	public int getCount()
	{
		return count;
	}

	public void setCount(int count)
	{
		this.count = count;
	}

	public byte[] getData()
	{
		return data;
	}

	public void setData(byte[] data)
	{
		this.data = data;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getOffset()
	{
		return offset;
	}

	public void setOffset(int offset)
	{
		this.offset = offset;
	}

	public MessageOpCode getOpcode()
	{
		return opcode;
	}

	public void setOpcode(MessageOpCode opcode)
	{
		this.opcode = opcode;
	}

	public MessageResult getResult()
	{
		return result;
	}

	public void setResult(MessageResult result)
	{
		this.result = result;
	}

	public int getDataLength()
	{
		return dataLength;
	}

	public void setDataLength(int dataLength)
	{
		this.dataLength = dataLength;
	}

}
