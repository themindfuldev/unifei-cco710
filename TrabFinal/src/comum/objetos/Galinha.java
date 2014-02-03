package comum.objetos;

@SuppressWarnings("serial")
public class Galinha extends ObjetoMovel
{
	public Galinha(String nome)
	{
		super(nome);
	}

	public String expressar()
	{
		return toString() + " se expressa: Cocoricó!";
	}

}
