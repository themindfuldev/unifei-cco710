package agencia.cliente.visao;

import agencia.cliente.controle.AgenciaListener;
import agencia.cliente.controle.MediatorPrincipal;
import java.awt.GridBagConstraints;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EtchedBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

/**
 * @author  tiago
 */
@SuppressWarnings("serial")
public class JanelaPrincipal extends JFrame implements AgenciaListener
{
	private MediatorPrincipal mediator;
	
	private JPanel jContentPane = null;
	private JLabel lblObjetosLocais = null;
	private JLabel lblAgenciasConhecidas = null;
	private JButton btnCriarObjeto = null;
	private JButton btnAcordarObjeto = null;
	private JButton btnDestruirObjeto = null;
	private JButton btnAdormecerObjeto = null;
	private JButton btnClonarObjeto = null;
	private JButton btnExecutarObjeto = null;
	private JButton btnDespacharObjeto = null;
	private JButton btnRetrairObjeto = null;
	private JLabel lblAreaExecucao = null;
	private JLabel lblObjetosRemotos = null;
	private JList lstObjetosLocais = null;
	private JScrollPane scrObjetosLocais = null;
	private JScrollPane scrAgenciasConhecidas = null;
	private JScrollPane scrAreaExecucao = null;
	private JScrollPane scrObjetosRemotos = null;
	private JList lstAgenciasConhecidas = null;
	private JTextArea txaAreaExecucao = null;
	private JList lstObjetosRemotos = null;
	private JMenuBar mnbMenu = null;
	private JMenu mnuAplicacao = null;
	private JMenu mnuAjuda = null;
	private JMenuItem mniSobre = null;
	private JMenuItem mniSair = null;
	private DefaultListModel lsmAgenciasConhecidas = null;
	private DefaultListModel lsmObjetosRemotos = null;
	private DefaultListModel lsmObjetosLocais = null;
	
	/**
	 * This is the default constructor
	 */
	public JanelaPrincipal(MediatorPrincipal mediator, String endereco)
	{
		super();
		
		this.mediator = mediator;
		
		initialize(endereco);
		
		mediator.addAgenciaListener(this);
		
		atualizarAgencias(mediator.listarAgenciasConhecidas());
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize(String endereco)
	{
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setContentPane(getJContentPane());
		this.setJMenuBar(getMnbMenu());
		this.setTitle("Agencia de objetos moveis - endereco: " + endereco);
		this.setBounds(new Rectangle(0, 0, 660, 660));
		this.setVisible(true);
		this.addWindowListener(new java.awt.event.WindowAdapter()
		{
			public void windowClosing(java.awt.event.WindowEvent e)
			{
				mediator.sair();
			}
		});
	}

	/**
	 * This method initializes jContentPane
	 * @return  javax.swing.JPanel
	 * @uml.property  name="jContentPane"
	 */
	private JPanel getJContentPane()
	{
		if (jContentPane == null)
		{
			lblObjetosRemotos = new JLabel();
			lblObjetosRemotos.setBounds(new Rectangle(340, 376, 141, 15));
			lblObjetosRemotos.setText("Objetos Remotos:");
			lblAreaExecucao = new JLabel();
			lblAreaExecucao.setBounds(new Rectangle(12, 376, 183, 15));
			lblAreaExecucao.setText("Area de execucao:");
			lblAgenciasConhecidas = new JLabel();
			lblAgenciasConhecidas.setBounds(new Rectangle(340, 13, 148, 15));
			lblAgenciasConhecidas.setDisplayedMnemonic(KeyEvent.VK_UNDEFINED);
			lblAgenciasConhecidas.setText("Agencias conhecidas:");
			lblObjetosLocais = new JLabel();
			lblObjetosLocais.setBounds(new Rectangle(12, 12, 100, 15));
			lblObjetosLocais.setText("Objetos locais:");
			GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
			gridBagConstraints1.gridx = 0;
			gridBagConstraints1.gridy = 0;
			GridBagConstraints gridBagConstraints = new GridBagConstraints();
			gridBagConstraints.gridx = 1;
			gridBagConstraints.gridy = 0;
			jContentPane = new JPanel();
			jContentPane.setLayout(null);
			jContentPane.add(lblObjetosLocais, null);
			jContentPane.add(lblAgenciasConhecidas, null);
			jContentPane.add(getBtnCriarObjeto(), null);
			jContentPane.add(getBtnAcordarObjeto(), null);
			jContentPane.add(getBtnDestruirObjeto(), null);
			jContentPane.add(getBtnAdormecerObjeto(), null);
			jContentPane.add(getBtnClonarObjeto(), null);
			jContentPane.add(getBtnExecutarObjeto(), null);
			jContentPane.add(getBtnDespacharObjeto(), null);
			jContentPane.add(getBtnRetrairObjeto(), null);
			jContentPane.add(lblAreaExecucao, null);
			jContentPane.add(lblObjetosRemotos, null);
			jContentPane.add(getScrObjetosLocais(), null);
			jContentPane.add(getScrAgenciasConhecidas(), null);
			jContentPane.add(getScrAreaExecucao(), null);
			jContentPane.add(getScrObjetosRemotos(), null);
		}
		return jContentPane;
	}

	/**
	 * This method initializes btnCriarObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnCriarObjeto"
	 */
	private JButton getBtnCriarObjeto()
	{
		if (btnCriarObjeto == null)
		{
			btnCriarObjeto = new JButton();
			btnCriarObjeto.setText("Criar objeto");
			btnCriarObjeto.setBounds(new Rectangle(12, 255, 150, 25));
			btnCriarObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					byte tipo;
					String nome, descricao;
					
					String[] tipos = new String[] {"Galinha", "Pessoa", "Vaca"};
					String tipoStr = (String) JOptionPane.showInputDialog(null, "Escolha o tipo do objeto:", "Criar objeto", JOptionPane.INFORMATION_MESSAGE, null, tipos, tipos[0]);
					if (tipoStr == null) return;

					nome = JOptionPane.showInputDialog(null, "Entre com o nome do novo objeto " + tipoStr + ":");
					if (nome == null || nome.equals("")) return;
					
					tipo = (byte) Arrays.binarySearch(tipos, tipoStr);						
					descricao = mediator.criarObjeto(nome, tipo);
						
					lsmObjetosLocais.addElement(descricao);
				}
			});
		}
		return btnCriarObjeto;
	}

	/**
	 * This method initializes btnAcordarObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnAcordarObjeto"
	 */
	private JButton getBtnAcordarObjeto()
	{
		if (btnAcordarObjeto == null)
		{
			btnAcordarObjeto = new JButton();
			btnAcordarObjeto.setText("Acordar objeto");
			btnAcordarObjeto.setBounds(new Rectangle(174, 292, 150, 25));
			btnAcordarObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String descricao;
					
					descricao = mediator.acordarObjeto();
					if (descricao == null) return;
						
					lsmObjetosLocais.addElement(descricao);
				}
			});
		}
		return btnAcordarObjeto;
	}

	/**
	 * This method initializes btnDestruirObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDestruirObjeto"
	 */
	private JButton getBtnDestruirObjeto()
	{
		if (btnDestruirObjeto == null)
		{
			btnDestruirObjeto = new JButton();
			btnDestruirObjeto.setText("Destruir objeto");
			btnDestruirObjeto.setBounds(new Rectangle(174, 255, 150, 25));
			btnDestruirObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int posicao;
					
					posicao = lstObjetosLocais.getSelectedIndex();
					if (posicao == -1) return;
					
					mediator.removerObjeto(posicao);
						
					lsmObjetosLocais.remove(posicao);
				}
			});
		}
		return btnDestruirObjeto;
	}

	/**
	 * This method initializes btnAdormecerObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnAdormecerObjeto"
	 */
	private JButton getBtnAdormecerObjeto()
	{
		if (btnAdormecerObjeto == null)
		{
			btnAdormecerObjeto = new JButton();
			btnAdormecerObjeto.setBounds(new Rectangle(12, 292, 150, 25));
			btnAdormecerObjeto.setText("Adormecer objeto");
			btnAdormecerObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int posicao;
					
					posicao = lstObjetosLocais.getSelectedIndex();
					if (posicao == -1) return;
					
					mediator.adormecerObjeto(posicao);
						
					lsmObjetosLocais.remove(posicao);
				}
			});
		}
		return btnAdormecerObjeto;
	}

	/**
	 * This method initializes btnClonarObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnClonarObjeto"
	 */
	private JButton getBtnClonarObjeto()
	{
		if (btnClonarObjeto == null)
		{
			btnClonarObjeto = new JButton();
			btnClonarObjeto.setBounds(new Rectangle(12, 329, 150, 25));
			btnClonarObjeto.setText("Clonar objeto");
			btnClonarObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String descricao;
					int posicao;
					
					posicao = lstObjetosLocais.getSelectedIndex();
					if (posicao == -1) return;
					
					descricao = mediator.clonarObjeto(posicao);
					if (descricao == null) return;
						
					lsmObjetosLocais.addElement(descricao);
				}
			});
		}
		return btnClonarObjeto;
	}

	/**
	 * This method initializes btnExecutarObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnExecutarObjeto"
	 */
	private JButton getBtnExecutarObjeto()
	{
		if (btnExecutarObjeto == null)
		{
			btnExecutarObjeto = new JButton();
			btnExecutarObjeto.setBounds(new Rectangle(174, 329, 150, 25));
			btnExecutarObjeto.setText("Executar objeto");
			btnExecutarObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					String execucao;
					int posicao;
					
					posicao = lstObjetosLocais.getSelectedIndex();
					if (posicao == -1) return;
					
					execucao = mediator.executarObjeto(posicao);
					
					txaAreaExecucao.setText(execucao);
				}
			});
		}
		return btnExecutarObjeto;
	}

	/**
	 * This method initializes btnDespacharObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnDespacharObjeto"
	 */
	private JButton getBtnDespacharObjeto()
	{
		if (btnDespacharObjeto == null)
		{
			btnDespacharObjeto = new JButton();
			btnDespacharObjeto.setBounds(new Rectangle(420, 270, 150, 25));
			btnDespacharObjeto.setText("Despachar objeto");
			btnDespacharObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int posicaoObjetoLocal, posicaoAgencia;
					
					posicaoObjetoLocal = lstObjetosLocais.getSelectedIndex();
					if (posicaoObjetoLocal == -1) return;
					
					posicaoAgencia = lstAgenciasConhecidas.getSelectedIndex();
					if (posicaoAgencia == -1) return;
					
					mediator.despacharObjeto(posicaoObjetoLocal, posicaoAgencia);
					
					lsmObjetosLocais.remove(posicaoObjetoLocal);
					atualizarObjetosRemotos();
				}
			});
		}
		return btnDespacharObjeto;
	}

	/**
	 * This method initializes btnRetrairObjeto	
	 * @return  javax.swing.JButton
	 * @uml.property  name="btnRetrairObjeto"
	 */
	private JButton getBtnRetrairObjeto()
	{
		if (btnRetrairObjeto == null)
		{
			btnRetrairObjeto = new JButton();
			btnRetrairObjeto.setBounds(new Rectangle(420, 308, 150, 25));
			btnRetrairObjeto.setText("Retrair objeto");
			btnRetrairObjeto.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					int posicaoAgencia, posicaoObjetoRemoto;
					String descricao;
					
					posicaoObjetoRemoto = lstObjetosRemotos.getSelectedIndex();
					if (posicaoObjetoRemoto == -1) return;
					
					posicaoAgencia = lstAgenciasConhecidas.getSelectedIndex();
					if (posicaoAgencia == -1) return;
					
					descricao = mediator.retrairObjeto(posicaoObjetoRemoto, posicaoAgencia);
					
					lsmObjetosLocais.addElement(descricao);
					atualizarObjetosRemotos();
				}
			});
		}
		return btnRetrairObjeto;
	}

	/**
	 * This method initializes lstObjetosLocais	
	 * @return  javax.swing.JList
	 * @uml.property  name="lstObjetosLocais"
	 */
	private JList getLstObjetosLocais()
	{
		if (lstObjetosLocais == null)
		{
			lsmObjetosLocais = new DefaultListModel();
			
			lstObjetosLocais = new JList(lsmObjetosLocais);
		}
		return lstObjetosLocais;
	}

	/**
	 * This method initializes scrObjetosLocais	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrObjetosLocais"
	 */
	private JScrollPane getScrObjetosLocais()
	{
		if (scrObjetosLocais == null)
		{
			scrObjetosLocais = new JScrollPane();
			scrObjetosLocais.setBounds(new Rectangle(12, 35, 312, 200));
			scrObjetosLocais.setViewportView(getLstObjetosLocais());
		}
		return scrObjetosLocais;
	}

	/**
	 * This method initializes scrAgenciasConhecidas	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrAgenciasConhecidas"
	 */
	private JScrollPane getScrAgenciasConhecidas()
	{
		if (scrAgenciasConhecidas == null)
		{
			scrAgenciasConhecidas = new JScrollPane();
			scrAgenciasConhecidas.setBounds(new Rectangle(340, 35, 300, 200));
			scrAgenciasConhecidas.setViewportView(getLstAgenciasConhecidas());
		}
		return scrAgenciasConhecidas;
	}

	/**
	 * This method initializes scrAreaExecucao	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrAreaExecucao"
	 */
	private JScrollPane getScrAreaExecucao()
	{
		if (scrAreaExecucao == null)
		{
			scrAreaExecucao = new JScrollPane();
			scrAreaExecucao.setBounds(new Rectangle(12, 398, 312, 200));
			scrAreaExecucao.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED));
			scrAreaExecucao.setViewportView(getTxaAreaExecucao());
		}
		return scrAreaExecucao;
	}

	/**
	 * This method initializes scrObjetosRemotos	
	 * @return  javax.swing.JScrollPane
	 * @uml.property  name="scrObjetosRemotos"
	 */
	private JScrollPane getScrObjetosRemotos()
	{
		if (scrObjetosRemotos == null)
		{
			scrObjetosRemotos = new JScrollPane();
			scrObjetosRemotos.setBounds(new Rectangle(340, 398, 300, 200));
			scrObjetosRemotos.setViewportView(getLstObjetosRemotos());
		}
		return scrObjetosRemotos;
	}

	/**
	 * This method initializes lstAgenciasConhecidas	
	 * @return  javax.swing.JList
	 * @uml.property  name="lstAgenciasConhecidas"
	 */
	private JList getLstAgenciasConhecidas()
	{
		if (lstAgenciasConhecidas == null)
		{
			lsmAgenciasConhecidas = new DefaultListModel();
			
			lstAgenciasConhecidas = new JList(lsmAgenciasConhecidas);
			lstAgenciasConhecidas
					.addListSelectionListener(new javax.swing.event.ListSelectionListener()
					{
						public void valueChanged(javax.swing.event.ListSelectionEvent e)
						{
							atualizarObjetosRemotos();
						}
					});
		}
		return lstAgenciasConhecidas;
	}

	/**
	 * This method initializes txaAreaExecucao	
	 * @return  javax.swing.JTextArea
	 * @uml.property  name="txaAreaExecucao"
	 */
	private JTextArea getTxaAreaExecucao()
	{
		if (txaAreaExecucao == null)
		{
			txaAreaExecucao = new JTextArea();
			txaAreaExecucao.setBounds(new Rectangle(0, 0, 318, 197));
			txaAreaExecucao.setLineWrap(true);
			txaAreaExecucao.setEditable(false);
			txaAreaExecucao.setWrapStyleWord(true);
		}
		return txaAreaExecucao;
	}

	/**
	 * This method initializes lstObjetosRemotos	
	 * @return  javax.swing.JList
	 * @uml.property  name="lstObjetosRemotos"
	 */
	private JList getLstObjetosRemotos()
	{
		if (lstObjetosRemotos == null)
		{
			lsmObjetosRemotos = new DefaultListModel();
			
			lstObjetosRemotos = new JList(lsmObjetosRemotos);
		}
		return lstObjetosRemotos;
	}

	/**
	 * This method initializes mnbMenu	
	 * @return  javax.swing.JMenuBar
	 * @uml.property  name="mnbMenu"
	 */
	private JMenuBar getMnbMenu()
	{
		if (mnbMenu == null)
		{
			mnbMenu = new JMenuBar();
			mnbMenu.add(getMnuAplicacao());
			mnbMenu.add(getMnuAjuda());
		}
		return mnbMenu;
	}

	/**
	 * This method initializes mnuAplicacao	
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuAplicacao"
	 */
	private JMenu getMnuAplicacao()
	{
		if (mnuAplicacao == null)
		{
			mnuAplicacao = new JMenu();
			mnuAplicacao.setText("Aplicacao");
			mnuAplicacao.add(getMniSair());
		}
		return mnuAplicacao;
	}

	/**
	 * This method initializes mnuAjuda	
	 * @return  javax.swing.JMenu
	 * @uml.property  name="mnuAjuda"
	 */
	private JMenu getMnuAjuda()
	{
		if (mnuAjuda == null)
		{
			mnuAjuda = new JMenu();
			mnuAjuda.setText("Ajuda");
			mnuAjuda.add(getMniSobre());
		}
		return mnuAjuda;
	}

	/**
	 * This method initializes mniSobre	
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mniSobre"
	 */
	private JMenuItem getMniSobre()
	{
		if (mniSobre == null)
		{
			mniSobre = new JMenuItem();
			mniSobre.setText("Sobre...");
			mniSobre.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					JOptionPane.showMessageDialog(null, "Trabalho Final de CCO710 - Sistemas Distribuidos\n\nTiago Romero Garcia - 12643", "Creditos",
		               JOptionPane.INFORMATION_MESSAGE);
				}
			});
		}
		return mniSobre;
	}

	/**
	 * This method initializes mniSair	
	 * @return  javax.swing.JMenuItem
	 * @uml.property  name="mniSair"
	 */
	private JMenuItem getMniSair()
	{
		if (mniSair == null)
		{
			mniSair = new JMenuItem();
			mniSair.setText("Sair");
			mniSair.addActionListener(new java.awt.event.ActionListener()
			{
				public void actionPerformed(java.awt.event.ActionEvent e)
				{
					mediator.sair();
				}
			});
		}
		return mniSair;
	}

	public void atualizarAgencias(String[] agencias)
	{
		lsmAgenciasConhecidas.clear();
		
		for (String agencia: agencias)
			lsmAgenciasConhecidas.addElement(agencia);
	}

	public void receberObjetoDespachado(String objeto)
	{
		lsmObjetosLocais.addElement(objeto);
		atualizarObjetosRemotos();
	}

	public void removerObjetoRetraido(int posicao)
	{
		lsmObjetosLocais.remove(posicao);
		atualizarObjetosRemotos();
	}

	private void atualizarObjetosRemotos()
	{
		int posicaoAgencia;
		String[] objetosRemotos;
		
		posicaoAgencia = lstAgenciasConhecidas.getSelectedIndex();
		if (posicaoAgencia == -1) return;
		
		objetosRemotos = mediator.obterObjetosRemotos(posicaoAgencia);
		
		lsmObjetosRemotos.clear();
		
		for (String objetoRemoto: objetosRemotos)								
			lsmObjetosRemotos.addElement(objetoRemoto);
	}

}
