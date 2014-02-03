package comum.objetos;

@SuppressWarnings("serial")
public class Vaca extends ObjetoMovel
{
	public Vaca(String nome)
	{
		super(nome);
	}
	
	public String expressar()
	{
		return toString() + " se expressa: Muuu!";
	}

}
