package com.CouponSystem.GUI;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class loginScreen extends JDialog implements ActionListener 
{
	private final JPanel contentPanel = new JPanel();
	private JTextField textUserName;
	private JPasswordField passwordPassword;
	private JButton okButton = new JButton("OK");
	private JButton cancelButton = new JButton("Cancel");
	private JComboBox<String> cmbUserType = new JComboBox<String>();
	String[] data = new String[3];
	
	public String[] run() 
	{
		try 
		{
			this.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			this.setVisible(true);
			return data;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			return null;
		}
	}




	/**
	 * Create the dialog.
	 */
	public loginScreen(JFrame frmae) {
		setModal(true);
		setAlwaysOnTop(true);
		
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

			
		cmbUserType.setBounds(121, 142, 193, 20);
		cmbUserType.setModel(new DefaultComboBoxModel<String>(new String[] {"admin", "company", "customer"}));
		contentPanel.add(cmbUserType);

		
		textUserName = new JTextField();
		textUserName.setBounds(121, 53, 193, 20);
		contentPanel.add(textUserName);
		textUserName.setColumns(10);
		
		passwordPassword = new JPasswordField();
		passwordPassword.setBounds(121, 93, 193, 20);
		contentPanel.add(passwordPassword);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

				
		okButton.addActionListener(this);
		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		cancelButton.addActionListener(this);
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);


	}





	@SuppressWarnings("deprecation")
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getActionCommand().equals("OK"))
		{
	      Object source = e.getSource();
	      if (source == okButton) {
	         data[0] = textUserName.getText();
	         data[1] = passwordPassword.getText();
	         data[2] = (String)cmbUserType.getSelectedItem();
	      }
	      else {
	         data[0] = null;
	      }
	      dispose();
		}
		
		else if (e.getActionCommand().equals("Cancel"))
		{
			dispose();
		}
	}
}
