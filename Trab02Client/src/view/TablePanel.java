package view;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class TablePanel extends JPanel implements ActionListener
{
   private JButton[][] btnTable;

   public TablePanel()
   {
      super();
      initComponents();       
   }

   public TablePanel(boolean arg0)
   {
      super(arg0);
      initComponents(); 
   }

   public TablePanel(LayoutManager arg0, boolean arg1)
   {
      super(arg0, arg1);
      initComponents(); 
   }

   public TablePanel(LayoutManager arg0)
   {
      super(arg0);
      initComponents(); 
   }   

   private void initComponents()
   {
      btnTable = new JButton[9][9];
      
      setLayout(new GridLayout(9, 9));
      
      for (byte i=0; i<9; i++)
         for (byte j=0; j<9; j++)
         {
            JButton newButton = new JButton();
            
            Color color = getColor(i, j);
            
            newButton.setBackground(color);
            
            newButton.addActionListener(this);
            add(newButton);
            
            btnTable[i][j] = newButton;           
         }
   }

   private Color getColor(byte i, byte j)
   {
      Color color = null;
      
      byte iSquare = (byte) (i/3);
      byte jSquare = (byte) (j/3);
      
      switch (iSquare)
      {
         case 0:
            switch (jSquare)
            {
               case 0:
                  color = new Color(0xCC, 0xFF, 0xFF);
                  break;
                  
               case 1:
                  color = new Color(0xFF, 0xFF, 0xCC);
                  break;
                  
               case 2:
                  color = new Color(0xCC, 0xFF, 0xFF);
                  break;                  
            }
            break;

         case 1:
            switch (jSquare)
            {
               case 0:
                  color = new Color(0xFF, 0xFF, 0xCC);
                  break;
                  
               case 1:
                  color = new Color(0xCC, 0xFF, 0xFF);
                  break;
                  
               case 2:
                  color = new Color(0xFF, 0xFF, 0xCC);
                  break;               
            }
            break;
            
         case 2:
            switch (jSquare)
            {
               case 0:
                  color = new Color(0xCC, 0xFF, 0xFF);
                  break;
                  
               case 1:
                  color = new Color(0xFF, 0xFF, 0xCC);
                  break;
                  
               case 2:
                  color = new Color(0xCC, 0xFF, 0xFF);
                  break;                  
            }
            break;
      }
    
      return color;
   }


   public void actionPerformed(ActionEvent event)
   {
      JButton btn = (JButton) event.getSource();
      byte posI=0, posJ=0;
      
      for (byte i=0; i<9; i++)
         for (byte j=0; j<9; j++)
            if (btnTable[i][j] == btn)
            {
               posI = i;
               posJ = j;
            }      
      
      Object[] possibleValues = { "1", "2", "3", "4", "5", "6", "7", "8", "9" };
      int value = JOptionPane.showOptionDialog(this.getParent(), String.format("Escolha um valor para a posio (%d, %d): ", posI, posJ), 
            "Preenchimento de posicao", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, possibleValues, possibleValues[0]) + 1;
      
      btn.setText(Integer.toString(value));
      requestFocus();
      
   }     
   
}
