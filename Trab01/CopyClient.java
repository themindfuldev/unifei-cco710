package client;

import java.io.*;
import java.net.*;
import common.*;

public class CopyClient
{
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		if ((args.length < 3) || (args.length > 4))
			throw new IllegalArgumentException(
					"Parameter(s): <Server> <FileSource> <FileDestiny> [<Port>]");

		String server = args[0];
		// Convert input String to bytes using the default character encoding

		int servPort = (args.length == 4) ? Integer.parseInt(args[3]) : 7;

		// Create socket that is connected to server on specified port
		Socket socket = new Socket(server, servPort);
		/*try
		{
			Thread.sleep(20000);
		} catch (InterruptedException e)
		{
		}*/

		System.out.println("Connected to server... sending echo string");

		InputStream is = socket.getInputStream();
		ObjectInputStream in = new ObjectInputStream(is);
		ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

		int position = 0; /* posi»c~ao corrente do arquivo */
		Message message;
		String nameSource = args[1];
		String nameDestiny = args[2];

		do {
			message = new Message();
			/* obter um bloco de dados do arquivo de origem */
			message.setOpcode(MessageOpCode.READ); /* opera»c~ao de leitura */			
			message.setOffset(position);
			message.setCount(Common.BUF_SIZE);
			message.setName(nameSource);
			
			out.writeObject(message);
			message = (Message) in.readObject();
			
			message.setOpcode(MessageOpCode.WRITE);
			message.setOffset(position);
			message.setCount(message.getDataLength());
			message.setName(nameDestiny);
			
			out.writeObject(message);
			message = (Message) in.readObject();
			
			position += message.getDataLength();
		} while (message.getResult() == MessageResult.OK);

		in.close();
		out.close();
		socket.close();
	}
}
