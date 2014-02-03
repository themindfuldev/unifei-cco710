package servidorNomes;

import comum.servidor.*;
import comum.servidor.Constantes.*;
import java.io.IOException;
import java.net.*;
import java.util.*;

/**
 * @author  tiago
 */
class NomesProtocol implements Runnable
{
	private static ServidorNomes servidorNomes;

	static
	{
		servidorNomes = new ServidorNomes();
	}

	private DatagramSocket clntSock; // Connection socket
	private DatagramPacket packet;
	private Logger logger; // Logging facility

	public NomesProtocol(DatagramSocket clntSock, DatagramPacket packet,
			Logger logger)
	{
		this.clntSock = clntSock;
		this.logger = logger;
		this.packet = packet;
	}

	public void run()
	{
		byte[] opCode, listagem;
		ArrayList<String> entry = new ArrayList<String>();
		String host = packet.getAddress().getHostAddress();

		entry.add("Client address and port = " + host + ":" + packet.getPort());
		entry.add("Thread = " + Thread.currentThread().getName());

		// Get the input and output I/O streams from socket
		opCode = packet.getData();

		try
		{
			switch (opCode[0])
			{
				case NOTIFICAR_ENTRADA:
					servidorNomes.entrada(packet.getAddress());
					clntSock.send(packet);
					entry.add("Acao: entrada");
					
					servidorNomes.atualizarAgencias();
					break;
	
				case NOTIFICAR_SAIDA:
					servidorNomes.saida(packet.getAddress());
					entry.add("Acao: saida");

					servidorNomes.atualizarAgencias();
					break;
	
				default:
					return;
			}
		} catch (IOException e)
		{
			entry.add("Error: " + e.getMessage());
		}

		logger.writeEntry(entry);
	}
}
