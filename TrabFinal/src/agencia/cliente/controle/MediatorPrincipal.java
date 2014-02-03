package agencia.cliente.controle;

import agencia.servidor.ServidorAgencia;
import comum.objetos.Galinha;
import comum.objetos.ObjetoMovel;
import comum.objetos.Pessoa;
import comum.objetos.Vaca;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author  tiago
 */
public class MediatorPrincipal
{
	private File arquivo;
	private List<ObjetoMovel> listaObjetosMovel;
	private ServidorAgencia servidorAgencia;
	private String[] agencias;
	private List<AgenciaListener> listeners;
	
	public MediatorPrincipal(ServidorAgencia servidorAgencia)
	{
		arquivo = new File("objetoAdormecido.dat");
		listaObjetosMovel = new ArrayList<ObjetoMovel>();
		listeners = new ArrayList<AgenciaListener>();
		
		this.servidorAgencia = servidorAgencia;
		this.servidorAgencia.setMediator(this);		
	}
	
	public String criarObjeto(String nome, byte tipo)
	{
		ObjetoMovel novoObjeto = null;
		
		if (tipo < 0 || tipo > 3) return null;
		
		switch(tipo)
		{
			case 0:
				novoObjeto = new Galinha(nome);
				break;
				
			case 1:
				novoObjeto = new Pessoa(nome);
				break;

			case 2:
				novoObjeto = new Vaca(nome);
				break;
		}
		
		listaObjetosMovel.add(novoObjeto);
		
		return novoObjeto.toString();
	}
	
	public boolean removerObjeto(int posicao)
	{
		boolean retorno = false;
		
		if (listaObjetosMovel.remove(posicao) != null)
			retorno = true;
		
		return retorno;
	}
	
	public String clonarObjeto(int posicao)
	{
		ObjetoMovel objetoOriginal, objetoClone;
		String descricao = null;
		
		objetoOriginal = listaObjetosMovel.get(posicao);
		
		if (objetoOriginal != null)
		{
			objetoClone = (ObjetoMovel) objetoOriginal.clone();
			descricao = objetoClone.toString();
			
			listaObjetosMovel.add(objetoClone);
		}
		
		return descricao;
	}
	
	public String executarObjeto(int posicao)
	{
		ObjetoMovel objeto;
		String descricao = null;
		
		objeto = listaObjetosMovel.get(posicao);
		
		if (objeto != null)
			descricao = objeto.expressar();
		
		return descricao;
	}
	
	public String acordarObjeto()
	{
		ObjectInputStream ois;
		ObjetoMovel objeto;
		String descricao = null;
		
		try
		{
			ois = new ObjectInputStream(new FileInputStream(arquivo));
			objeto = (ObjetoMovel) ois.readObject();
			listaObjetosMovel.add(objeto);
			
			descricao = objeto.toString();
			
			ois.close();
			arquivo.delete();
		}
		catch (Exception e)	{ }

		return descricao;
	}

	public boolean adormecerObjeto(int posicao)
	{
		ObjectOutputStream oos;
		ObjetoMovel objeto;
		boolean retorno = true;
		
		objeto = listaObjetosMovel.remove(posicao);
		
		try
		{
			if (objeto != null)
			{
				arquivo.createNewFile();				
				oos = new ObjectOutputStream(new FileOutputStream(arquivo));
				oos.writeObject(objeto);
				oos.close();
			}
		}
		catch (Exception e)
		{
			retorno = false;
		}
		
		return retorno;
	}
	
	public String[] listarObjetos()
	{
		String[] listagem = new String[listaObjetosMovel.size()];
		int pos = 0;
		
		for (ObjetoMovel objeto: listaObjetosMovel)
			listagem[pos++] = objeto.toString();
		
		return listagem;
	}
	
	public String[] listarAgenciasConhecidas()
	{
		Map<String, String[]> listaAgencias = servidorAgencia.getTabelaHosts(); 
		agencias = new String[listaAgencias.size()];
		int pos = 0;
		
		for (String endereco: listaAgencias.keySet())
			agencias[pos++] = endereco;
		
		return agencias;
	}
	
	public boolean inserirObjeto(ObjetoMovel objeto)
	{
		boolean retorno = listaObjetosMovel.add(objeto);
		
		if (retorno)
			for (AgenciaListener cliente: listeners)
				cliente.receberObjetoDespachado(objeto.toString());
		
		return retorno; 
	}
	
	public ObjetoMovel retirarObjeto(int posicao)
	{
		ObjetoMovel objeto = listaObjetosMovel.remove(posicao);
		
		if (objeto != null)
			for (AgenciaListener cliente: listeners)
				cliente.removerObjetoRetraido(posicao);
		
		return objeto;
	}

	public void despacharObjeto(int posicaoObjetoLocal, int posicaoAgencia)
	{
		Map<String, String[]> listaAgencias = servidorAgencia.getTabelaHosts();
		String enderecoHost = null;
		ObjetoMovel objeto = listaObjetosMovel.remove(posicaoObjetoLocal);
		
		for(String endereco: listaAgencias.keySet())
		{
			if (endereco.equals(agencias[posicaoAgencia]))
			{
				enderecoHost = endereco;
				break;
			}
		}
		
		servidorAgencia.despachar(enderecoHost, objeto);
	}

	public String retrairObjeto(int posicaoObjetoRemoto, int posicaoAgencia)
	{
		Map<String, String[]> listaAgencias = servidorAgencia.getTabelaHosts();
		String enderecoHost = null;
		
		for(String endereco: listaAgencias.keySet())
		{
			if (endereco.equals(agencias[posicaoAgencia]))
			{
				enderecoHost = endereco;
				break;
			}
		}
		
		ObjetoMovel objeto = servidorAgencia.retrair(enderecoHost, posicaoObjetoRemoto);
		listaObjetosMovel.add(objeto);
		
		return objeto.toString();
	}

	public void sair()
	{
		servidorAgencia.notificarSaida();		
	}

	public void atualizar(String[] listagem)
	{
		listarAgenciasConhecidas();
		for (AgenciaListener cliente: listeners)
			cliente.atualizarAgencias(listagem);
	}
	
	public void addAgenciaListener(AgenciaListener cliente)
	{
		listeners.add(cliente);
	}

	public String[] obterObjetosRemotos(int posicaoAgencia)
	{
		Map<String, String[]> listaAgencias = servidorAgencia.getTabelaHosts();
		String enderecoHost = null;
		String[] listaObjetos = null;
		
		for(String endereco: listaAgencias.keySet())
		{
			if (endereco.equals(agencias[posicaoAgencia]))
			{
				enderecoHost = endereco;
				break;
			}
		}
		
		if (enderecoHost == null) return null;
		
		listaObjetos = servidorAgencia.obterObjetosRemotos(enderecoHost);
		listaAgencias.put(enderecoHost, listaObjetos);
		
		return listaObjetos;
	}
	
}
