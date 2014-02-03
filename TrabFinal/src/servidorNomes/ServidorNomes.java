package servidorNomes;

import static comum.servidor.Constantes.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServidorNomes
{
	private Set<InetAddress> hosts;

	public ServidorNomes()
	{
		hosts = new HashSet<InetAddress>();
	}
	
	public boolean entrada(InetAddress host)
	{
		return hosts.add(host);
	}
	
	public boolean saida(InetAddress host)
	{
		return hosts.remove(host);
	}
	
	public String[] listagem()
	{
		int pos = 0;
		String[] listagem;
		
		listagem = new String[hosts.size()];
		
		for (InetAddress host: hosts)
			listagem[pos++] = host.getHostAddress();
		
		return listagem;		
	}

	public void atualizarAgencias()
	{
		ObjectOutputStream out; 
		//ObjectInputStream in;
		Socket socket;
		
		try
		{
			for (InetAddress endereco: hosts)
			{
				socket = new Socket(endereco, PORTA_AGENTES);
				
				out = new ObjectOutputStream(socket.getOutputStream());
				
				out.writeByte(ATUALIZAR);
				out.writeObject(listagem());
				
				socket.close();
			}
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		
	}

}
