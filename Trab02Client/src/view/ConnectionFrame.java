package view;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

import control.*;

/**
 * Janela JFrame principal do programa. 
 * 
 * Abstrai a camada de viso do programa (MVC).  
 */
@SuppressWarnings("serial")
public class ConnectionFrame extends JFrame implements ActionListener
{
   // Componentes GUI
   private JLabel lblServidor, lblPorta;
   private JTextField txfServidor, txfPorta;
   private JButton btnConectar, btnSair;
   private JPanel pnlBotoes;
   
   // Construtor
   public ConnectionFrame()
   {
      // Construtor da superclasse
      super("Assistente de conexao");

      // Inicializao do GUI
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(250, 150);
      setResizable(false);

      // Posicionamento
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      int posX = (d.width - this.getWidth()) / 2;
      int posY = (d.height - this.getHeight()) / 2;
      setLocation(posX, posY);

      // Inicializao dos componentes
      initComponents();

      // Configuraes dos componentes
      lblServidor.setHorizontalAlignment(SwingConstants.RIGHT);
      lblPorta.setHorizontalAlignment(SwingConstants.RIGHT);
      
      // Registro dos listeners
      txfServidor.addActionListener(this);
      txfPorta.addActionListener(this);
      btnConectar.addActionListener(this);
      btnSair.addActionListener(this);

      // Adio dos componentes    
      
      GridLayout layout = new GridLayout(1, 2);
      layout.setHgap(10);
      pnlBotoes.setLayout(layout);
      pnlBotoes.add(btnConectar);
      pnlBotoes.add(btnSair);
      
      getContentPane().setLayout(new GridBagLayout());
      GridBagConstraints c = new GridBagConstraints();
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(5, 0, 0, 0);
      c.ipadx = 10;      
      add(lblServidor, c);
      
      c.fill = GridBagConstraints.NONE;
      c.gridx = 1;
      c.ipadx = 80;
      add(txfServidor, c);
      
      c.fill = GridBagConstraints.HORIZONTAL;
      c.insets = new Insets(0, 0, 0, 0);      
      c.gridy = 1;
      c.gridx = 0;  
      c.ipadx = 10; 
      add(lblPorta, c);
      
      c.fill = GridBagConstraints.NONE;
      c.gridx = 1;    
      c.ipadx = 80;
      add(txfPorta, c);
      
      c.fill = GridBagConstraints.NONE;  
      c.insets = new Insets(20, 0, 0, 0);
      c.anchor = GridBagConstraints.CENTER;
      c.gridy = 2;
      c.gridx = 0;
      c.ipadx = 0;
      c.gridwidth = 2;
      add(pnlBotoes, c);
      
      // Exibio
      setVisible(true);
   }

   // Mtodos
   /**
    * Instanciao dos componentes GUI.
    */
   private void initComponents()
   {
      // JLabel
      lblServidor = new JLabel("Servidor: ");
      lblPorta = new JLabel("Porta: ");      

      // JTextField
      txfServidor = new JTextField("localhost", 20);
      txfPorta = new JTextField("7", 6);      
      
      // JButton
      btnConectar = new JButton("Conectar");
      btnSair = new JButton("Sair");      
      
      // JPanel
      pnlBotoes = new JPanel();
   }

   /**
    * Manipulador do listener de aes dos componentes GUI.
    */
   public void actionPerformed(ActionEvent evento)
   {
      Object obj = evento.getSource();

      // Carregar o arquivo
      if (obj == btnConectar || obj == txfServidor || obj == txfPorta)
      {
         optConectar();            
      }

      // Sair do programa
      else if (obj == btnSair)
      {
         System.exit(0);
      }      
   }

   private void optConectar()
   {
      String server = txfServidor.getText();
      String portTxt = txfPorta.getText();
      
      if (server.equals("") || portTxt.equals(""))
      {
         JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!", "Erro!", JOptionPane.ERROR_MESSAGE);
         if (server.equals(""))
            txfServidor.requestFocus();
         else
            txfPorta.requestFocus();
         return;
      }
      
      int port;         

      try
      {
         port = Integer.parseInt(portTxt);
      } 
      catch (NumberFormatException e)
      {
         JOptionPane.showMessageDialog(this, "Preencha os campos corretamente!", "Erro!", JOptionPane.ERROR_MESSAGE);
         txfPorta.requestFocus();
         return;
      }

      MainControl control = new MainControl(server, port);
      
      if (control.testConnection())
      {
         JOptionPane.showMessageDialog(this, "Conexo efetuada com sucesso!", "Erro!", JOptionPane.INFORMATION_MESSAGE);
         setVisible(false);
         dispose();
         
         new MainFrame(control);
      }
      else
         JOptionPane.showMessageDialog(this, "Erro na tentativa de conexo!", "Erro!", JOptionPane.ERROR_MESSAGE);
   }
}
