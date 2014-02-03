package agencia.cliente.controle;

public interface AgenciaListener
{
	public void atualizarAgencias(String[] agencias);
	public void receberObjetoDespachado(String objeto);
	public void removerObjetoRetraido(int posicao);
}
