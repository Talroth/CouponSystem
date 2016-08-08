package com.CouponSystem.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import com.CouponSystem.CouponSystem.CouponSystem;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.Facade.CompanyFacade;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

public class AppFrame extends JFrame implements ActionListener 
{
	private static final long serialVersionUID = 1L;
	
	private CouponSystem mainSystem = CouponSystem.getInstance();
	private AdminFacade	adminUser = null;
	private CustomerFacade customerUser = null;
	private CompanyFacade companyUser = null;
	
	// Menu items
	private JMenuItem menuItemLogin = new JMenuItem("Login");
	private JMenuItem menuItemLogout = new JMenuItem("Logout");
	private JMenuItem menuItemExit = new JMenuItem("Exit");
	
	private JPanel activePanel;
	
	public static void main(String[] args) 
	{
		try 
		{	
			AppFrame frame = new AppFrame();	
			frame.setVisible(true);		
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
	}
	
	
	
	public AppFrame()
	{
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1200, 700);
		setTitle("Coupon System Client");
		
		// Menu
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		JMenu menuLog = new JMenu("Log");
		menuBar.add(menuLog);
		
		
		menuItemLogin.addActionListener(this); 

		menuLog.add(menuItemLogin);
		
		menuItemLogout.addActionListener(this); 
		
		menuLog.add(menuItemLogout);
		
		menuItemExit.addActionListener(this); 
		menuLog.add(menuItemExit);
		getContentPane().setLayout(null);
	}



	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == menuItemLogin)
		{
			loginScreen logDialog = new loginScreen(this);
			String[] loginData = logDialog.run();
			if (mainSystem == null)
			{
				JOptionPane.showMessageDialog(rootPane, "Fail to initiate the system, please refer to the admin");
				return;
			}
			
			try 
			{
				if (loginData[2] == "admin")
				{
					adminUser = (AdminFacade) mainSystem.login(loginData[0], loginData[1], loginData[2]);
					activePanel = new AdminPanel(adminUser);

				}
				else if (loginData[2] == "customer")
				{
					customerUser = (CustomerFacade) mainSystem.login(loginData[0], loginData[1], loginData[2]);
					activePanel = new CustomerPanel(customerUser);
					
				}
				else if (loginData[2] == "company")
				{
					companyUser = (CompanyFacade) mainSystem.login(loginData[0], loginData[1], loginData[2]);
					activePanel = new CompanyPanel(companyUser);
				}
				
				if (activePanel != null)
				{
					setContentPane(activePanel);
					activePanel.setVisible(true);
					activePanel.revalidate();
					activePanel.repaint();
				}

			} 
			catch (FacadeException e1) 
			{
				JOptionPane.showMessageDialog(rootPane, "Login was failed");
			}
		}
		
		// logout from current user
		else if (e.getSource() == menuItemLogout)
		{
			// Reset last user
			adminUser = null;
			customerUser = null;
			companyUser = null;
			
			// Change screen to blank
			activePanel.setVisible(false);
			activePanel.revalidate();
			activePanel.repaint();
			
		}
		
		// Terminate app
		else if (e.getSource() == menuItemExit)
		{
			try 
			{
				mainSystem.shutdown();
			} 
			catch (InterruptedException e1) 
			{
				
			}
			this.dispose();
		}
	}

}
