package control;

import java.io.*;
import java.net.*;

public class MainControl
{
   private String server;
   private int port;

   public MainControl(String server, int port)
   {
      this.server = server;
      this.port = port;
   }

   public boolean testConnection()
   {
      boolean hasConnection = true;

      try
      {
         InetAddress address = InetAddress.getByName(server);
         if (address.isReachable(0) == false)
            hasConnection = false;
      } catch (UnknownHostException e)
      {
         hasConnection = false;
      } catch (IOException e)
      {
         hasConnection = false;
      }

      return hasConnection;
   }

}
