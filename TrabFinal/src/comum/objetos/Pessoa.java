package comum.objetos;

@SuppressWarnings("serial")
public class Pessoa extends ObjetoMovel
{
	public Pessoa(String nome)
	{
		super(nome);
	}
	
	public String expressar()
	{
		return toString() + " se expressa: Olá!";
	}

}
