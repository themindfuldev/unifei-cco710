package agencia.servidor;

import agencia.cliente.controle.MediatorPrincipal;
import comum.objetos.ObjetoMovel;
import comum.servidor.Constantes.*;
import java.io.*;
import java.net.*;
import java.util.*;

/**
 * @author  tiago
 */
public class ServidorAgencia
{
	/**
	 * @uml.property  name="tabelaHosts"
	 * @uml.associationEnd  qualifier="key:java.lang.Object java.lang.Stri[]"
	 */
	private Map<String, String[]> tabelaHosts;
	private MediatorPrincipal mediator;
	private InetAddress servidorNomes;

	public ServidorAgencia()
	{
		tabelaHosts = new HashMap<String, String[]>();

		notificarEntrada();
	}

	/**
	 * @return  the tabelaHosts
	 * @uml.property  name="tabelaHosts"
	 */
	public Map<String, String[]> getTabelaHosts()
	{
		return tabelaHosts;
	}

	/**
	 * @return  the mediator
	 * @uml.property  name="mediator"
	 */
	public MediatorPrincipal getMediator()
	{
		return mediator;
	}

	/**
	 * @param mediator  the mediator to set
	 * @uml.property  name="mediator"
	 */
	public void setMediator(MediatorPrincipal mediator)
	{
		this.mediator = mediator;
	}

	private void notificarEntrada()
	{
		try
		{
			DatagramSocket socket = new DatagramSocket();
			// socket.setSoTimeout(); // Maximum receive blocking time
			byte[] bytesToSend = new byte[]
			{ NOTIFICAR_ENTRADA };

			DatagramPacket packet = new DatagramPacket(bytesToSend,
					bytesToSend.length, InetAddress.getByName("255.255.255.255"),
					PORTA_SERVIDOR_NOMES);

			socket.send(packet); // Send the echo string
			socket.receive(packet); // Attempt echo reply
			// Check source
			servidorNomes = packet.getAddress();
		} catch (InterruptedIOException e)
		{ // We didn't get anything
			/*
			 * tries += 1; System.out.println("Timed out, " + (MAXTRIES - tries) + "
			 * more tries ...");
			 */
		} catch (Exception e)
		{
			// TODO: retornar false
		}
	}

	/*private void obtemHosts()
	{
		ObjectOutputStream out; 
		ObjectInputStream in;
		Socket socket;
		String[] hosts;
		
		try
		{
			for (Map.Entry<InetAddress, String[]> entrada: tabelaHosts.entrySet())
			{
				socket = new Socket(entrada.getKey(), PORTA_AGENTES);
				
				out = new ObjectOutputStream(socket.getOutputStream());
				in = new ObjectInputStream(socket.getInputStream());
				
				out.writeByte(LISTAGEM_OBJETOS);
				hosts = (String[]) in.readObject();
				
				entrada.setValue(hosts);
			
				socket.close();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/

	public void despachar(String enderecoHost, ObjetoMovel movel)
	{
		ObjectOutputStream out; 
		//ObjectInputStream in;
		Socket socket;
		
		try
		{
			socket = new Socket(enderecoHost, PORTA_AGENTES);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeByte(DESPACHAR);
			out.writeObject(movel);
			
			socket.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	public ObjetoMovel retrair(String enderecoHost, int posicaoObjetoRemoto)
	{
		ObjectOutputStream out; 
		ObjectInputStream in;
		Socket socket;
		ObjetoMovel objeto = null;
		
		try
		{
			socket = new Socket(enderecoHost, PORTA_AGENTES);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			out.writeByte(RETRAIR);
			out.writeInt(posicaoObjetoRemoto);
			out.flush();
			
			objeto = (ObjetoMovel) in.readObject();
			
			socket.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return objeto;
	}

	public void notificarSaida()
	{
		try
		{
			DatagramSocket socket = new DatagramSocket();
			// socket.setSoTimeout(); // Maximum receive blocking time
			byte[] bytesToSend = new byte[]
			{ NOTIFICAR_SAIDA };

			DatagramPacket packet = new DatagramPacket(bytesToSend,
					bytesToSend.length, servidorNomes, PORTA_SERVIDOR_NOMES);

			socket.send(packet); // Send the echo string
		} catch (InterruptedIOException e)
		{ // We didn't get anything
			/*
			 * tries += 1; System.out.println("Timed out, " + (MAXTRIES - tries) + "
			 * more tries ...");
			 */
		} catch (Exception e)
		{
			// TODO: retornar false
		}
	}

	public void atualizar(String[] listagemHosts)
	{
		//InetAddress endereco;
		String[] listagemObjetos = null;
		//String[] agencias = new String[listagemHosts.length];
		//int pos = 0;
		
		tabelaHosts.clear();
		
		/*try
		{*/
			for (String agencia: listagemHosts)
			{
				//endereco = InetAddress.getByName(agencia);
				
				//if (endereco.equals(enderecoLocal)) continue;
				
				//agencias[pos++] = agencia;
				
				// TODO: obter objetos da agencia
				//listagemObjetos = obterAplicacoes(endereco);
				
				tabelaHosts.put(agencia, listagemObjetos);
			}
			
			mediator.atualizar(listagemHosts);
		/*} catch (UnknownHostException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}

	/*public String[] obterAplicacoes(InetAddress endereco)
	{
		ObjectOutputStream out; 
		ObjectInputStream in;
		Socket socket;
		String[] listagem = null;
		
		try
		{
			socket = new Socket(endereco, PORTA_AGENTES);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
			
			out.writeByte(LISTAGEM_OBJETOS);
			listagem = (String[]) in.readObject();
			
			socket.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listagem;
	}*/

	public String[] obterObjetosRemotos(String enderecoHost)
	{
		ObjectOutputStream out; 
		ObjectInputStream in;
		Socket socket;
		String[] listagem = null;
		
		try
		{
			socket = new Socket(enderecoHost, PORTA_AGENTES);
			
			out = new ObjectOutputStream(socket.getOutputStream());
			
			out.writeByte(LISTAGEM_OBJETOS);
			out.flush();
			
			in = new ObjectInputStream(socket.getInputStream());
			listagem = (String[]) in.readObject();
			
			socket.close();
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return listagem;
	}
}
