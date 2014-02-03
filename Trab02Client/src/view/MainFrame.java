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
public class MainFrame extends JFrame implements ActionListener
{
   /**
    * Controle utilizado.
    */
   private MainControl control;
   
   // Componentes GUI
   private JMenuBar mnbMain;
   private JMenu mnuJogar, mnuAjuda;
   private JMenuItem mniNovoJogo, mniSair, mniSobre;
   private JPanel pnlTable, pnlStatus;
   private JLabel lblError, lblErrorCounter, lblFill, lblFillCounter;
   
   // Construtor
   public MainFrame(MainControl control)
   {
      // Construtor da superclasse
      super("Trabalho 2: Jogo de Sudoku");
      
      // Inicializao do controle
      this.control = control;

      // Inicializao do GUI
      setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setSize(400, 400);
      setResizable(false);

      // Posicionamento
      Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
      int posX = (d.width - this.getWidth()) / 2;
      int posY = (d.height - this.getHeight()) / 2;
      setLocation(posX, posY);

      // Inicializao dos componentes
      initComponents();

      // Configuraes dos componentes
      mnbMain.add(mnuJogar);
      mnbMain.add(mnuAjuda);

      mnuJogar.add(mniNovoJogo);
      mnuJogar.addSeparator();
      mnuJogar.add(mniSair);

      mnuAjuda.add(mniSobre);
      
      pnlStatus.setLayout(new BorderLayout());
      pnlStatus.add(lblError, BorderLayout.WEST);
      pnlStatus.add(lblErrorCounter, BorderLayout.WEST);
      pnlStatus.add(lblFill, BorderLayout.EAST);
      pnlStatus.add(lblFillCounter, BorderLayout.EAST);

      // Registro dos listeners
      mniNovoJogo.addActionListener(this);
      mniSair.addActionListener(this);
      mniSobre.addActionListener(this);

      // Adio dos componentes
      setJMenuBar(mnbMain);
      
      Container c = getContentPane();
      c.setLayout(new BorderLayout());
      c.add(pnlTable, BorderLayout.CENTER); 
      c.add(pnlStatus, BorderLayout.SOUTH);

      // Exibio
      setVisible(true);
   }

   // Mtodos
   /**
    * Instanciao dos componentes GUI.
    */
   private void initComponents()
   {
      // JMenuBar e JMenu
      mnbMain = new JMenuBar();
      mnuJogar = new JMenu("Arquivo");
      mnuAjuda = new JMenu("Ajuda");

      // JMenuItem
      mniNovoJogo = new JMenuItem("Novo Jogo");
      mniSair = new JMenuItem("Sair");
      mniSobre = new JMenuItem("Sobre...");
      
      // JPanel
      pnlTable = new TablePanel();
      pnlStatus = new JPanel();
      
      // JLabel
      lblError = new JLabel("Erros: ");
      lblErrorCounter = new JLabel("0");
      lblFill = new JLabel("Restantes: ");
      lblFillCounter = new JLabel("0");
   }

   /**
    * Manipulador do listener de aes dos componentes GUI.
    */
   public void actionPerformed(ActionEvent event)
   {
      JMenuItem obj = (JMenuItem) event.getSource();

      // Carregar o arquivo
      if (obj == mniNovoJogo)
      {
         
      }

      // Sair do programa
      else if (obj == mniSair)
      {
         System.exit(0);
      }
      
      // Exibir dados do programa
      else if (obj == mniSobre)
      {
         JOptionPane.showMessageDialog(this, "Trabalho de CCO710 - Sistemas Distribudos\n\nTiago Romero Garcia - 12643", "Crditos",
               JOptionPane.INFORMATION_MESSAGE);
      }
   }

}
