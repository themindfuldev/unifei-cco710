package server;

import java.io.*;
import java.net.*;
import common.*;

public class FileServer
{
	public static void main(String[] args) throws IOException, ClassNotFoundException
	{
		if (args.length != 1)
			throw new IllegalArgumentException("Parameter(s): <Port>");
		
		int servPort = Integer.parseInt(args[0]);
		
		// Create a server socket to accept client connection requests
		ServerSocket servSock = new ServerSocket(servPort);
		Message messageReceived, messageToSend = null;
		
		while (true)
		{ 
			// Run forever, accepting and servicing connections
			Socket clntSock = servSock.accept(); // Get client connection
			System.out.println("Handling client at "
					+ clntSock.getInetAddress().getHostAddress() + " on port "
					+ clntSock.getPort());
			ObjectInputStream in = new ObjectInputStream(clntSock.getInputStream());
			ObjectOutputStream out = new ObjectOutputStream(clntSock.getOutputStream());
			
			// Receive until client closes connection,
			// indicated by -1 return
			messageReceived = (Message) in.readObject();			
			if (messageReceived != null)
			{
				switch (messageReceived.getOpcode())
				{
					case CREATE:
						//messageToSend = doCreate(messageReceived);
						break;
						
					case READ:
						messageToSend = doRead(messageReceived);
						break;
						
					case WRITE:
						messageToSend = doWrite(messageReceived);
						break;
						
					case DELETE:
						//messageToSend = doDelete(messageReceived);
						break;					
				}
			}
			
			out.writeObject(messageToSend);
			
			in.close();
			out.close();			
			clntSock.close(); // Close de socket.
		}
	}

	private static Message doWrite(Message messageReceived) throws IOException 
	{
		byte[] data = new byte[Common.BUF_SIZE];
		Message messageToSend = new Message();		
		
		File file = new File(messageReceived.getName());
		FileOutputStream writer = new FileOutputStream(file);
		
		writer.write(data, messageReceived.getOffset(), Common.BUF_SIZE);
		
		messageToSend.setResult(MessageResult.OK);
		
		return messageToSend;
	}

	private static Message doRead(Message messageReceived) throws FileNotFoundException, IOException
	{
		byte[] data = new byte[Common.BUF_SIZE];
		Message messageToSend = new Message();		
		
		File file = new File(messageReceived.getName());
		FileInputStream reader = new FileInputStream(file);
		
		int dataLength = reader.read(data, messageReceived.getOffset(), Common.BUF_SIZE);
		
		messageToSend.setData(data);
		messageToSend.setDataLength(dataLength);
		messageToSend.setResult(MessageResult.OK);		
		
		return messageToSend;
	}	
	
}
