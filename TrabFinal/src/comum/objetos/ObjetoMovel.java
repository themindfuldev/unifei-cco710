package comum.objetos;

import java.io.Serializable;

public abstract class ObjetoMovel implements Serializable, Cloneable
{
	private String nome;	

	ObjetoMovel(String nome)
	{
		super();
		this.nome = nome;
	}

	public abstract String expressar();

	public String toString()
	{
		return this.getClass().getSimpleName() + " " + nome;
	}
	
	public Object clone()
	{
		ObjetoMovel clone = null;
		
		try
		{
			clone = (ObjetoMovel) getClass().getConstructors()[0].newInstance(new Object[]{this.nome});
		}
		catch (Exception e )	{	}
		
		return clone;
	}

}
