package com.CouponSystem.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Customer;
import com.CouponSystem.Facade.AdminFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

public class AdminPanel extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;
	private JTextField txtCompanyName;
	private JTextField txtPassword;
	private JTextField txtEmail;

	private JTable tblCompanies;
	private JTextField textSearchCompanyById = new JTextField();
	private JButton btnSearch = new JButton("Search");
	private DefaultTableModel dataModel; 
	private DefaultTableModel dataModelcustomer; 
	
	private JButton btnCreateCompany = new JButton("Create");
	private JButton btnDeleteCompany = new JButton("Delete");
	private JButton btnUpdateCompany = new JButton("Update");

	private JComboBox<String> cmbCompanyOrCustomer = new JComboBox<String>();
	private JTable tblCustomer;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JLabel lblSearchCompany;
	private JLabel lblCompanyName;
	private JLabel lblCompanyEmail;
	
	private byte[] rowChangedCompanyTable;
	private byte[] rowChangedCustomerTable;
	
	private AdminFacade adminUser = null;
	
	public AdminPanel(AdminFacade user)
	{
		
		adminUser = user;
	setLayout(null);

	// company name
	lblCompanyName = new JLabel("Company name:");
	lblCompanyName.setBounds(24, 154, 154, 14);
	this.add(lblCompanyName);
	
	txtCompanyName = new JTextField();
	txtCompanyName.setBounds(199, 154, 86, 20);
	this.add(txtCompanyName);
	txtCompanyName.setColumns(10);
			
	// password
	JLabel label = new JLabel("Password:");
	label.setBounds(24, 182, 154, 14);
	this.add(label);
	
	txtPassword = new JTextField();
	txtPassword.setColumns(10);
	txtPassword.setBounds(199, 182, 86, 20);
	this.add(txtPassword);
	
	//email
	lblCompanyEmail = new JLabel("Company Email:");
	lblCompanyEmail.setBounds(24, 210, 154, 14);
	this.add(lblCompanyEmail);
	
	txtEmail = new JTextField();
	txtEmail.setColumns(10);
	txtEmail.setBounds(199, 210, 86, 20);
	this.add(txtEmail);
	
	// Search section		
	lblSearchCompany = new JLabel("Search company by id:");
	lblSearchCompany.setBounds(24, 55, 170, 25);
	this.add(lblSearchCompany);
	
	textSearchCompanyById.setBounds(199, 57, 86, 20);		
	this.add(textSearchCompanyById);
	textSearchCompanyById.setColumns(10);
			
	btnSearch.addActionListener(this);
	btnSearch.setBounds(295, 56, 134, 23);
	this.add(btnSearch);
	
	
	btnDeleteCompany.setBounds(549, 445, 109, 23);
	btnDeleteCompany.addActionListener(this);
	this.add(btnDeleteCompany);
			
	btnUpdateCompany.setBounds(743, 445, 118, 23);
	btnUpdateCompany.addActionListener(this);
	this.add(btnUpdateCompany);
	
	//Combobox to swap between company and customer setup by admin
	cmbCompanyOrCustomer.setModel(new DefaultComboBoxModel<String>(new String[] {"company", "customer"}));
	cmbCompanyOrCustomer.setBounds(24, 23, 200, 20);
	cmbCompanyOrCustomer.addActionListener(this);
	this.add(cmbCompanyOrCustomer);
	

	// Table area
	
	scrollPane = new JScrollPane();
	scrollPane.setBounds(482, 7, 452, 427);
	this.add(scrollPane);
			
	scrollPane.setRowHeaderView(tblCompanies);
	
	tblCompanies = new JTable();
	tblCompanies.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	
	tblCompanies.setModel(new DefaultTableModel(
		new Object[][] {
		},
		new String[] {
			"ID", "Company Name", "Password", "Email"
		}) {

		private static final long serialVersionUID = 1L;

		public boolean isCellEditable(int row, int column)
		{
			return (column == 2 || column == 3);
		}
	}
	);
	
	tblCompanies.getModel().addTableModelListener(new TableModelListener() {
		
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
		
        if (column == 2 || column == 3) 
        {
        	rowChangedCompanyTable[row] = 1;
        }				
		}			
	});
	
	scrollPane.setViewportView(tblCompanies);	
	
	scrollPane_1 = new JScrollPane();
	scrollPane_1.setBounds(-1, 518, 452, 427);
	this.add(scrollPane_1);
	
	scrollPane_1.setRowHeaderView(tblCustomer);
	
	tblCustomer = new JTable();
	tblCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	
	tblCustomer.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Customer Name", "Password"
			}) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				return (column == 2);
			}
		}
		);
	
	tblCustomer.getModel().addTableModelListener(new TableModelListener() {
		
	@Override
	public void tableChanged(TableModelEvent e) {
		int row = e.getFirstRow();
        int column = e.getColumn();
		
        if (column == 2) 
        {
        	rowChangedCustomerTable[row] = 1;
        }				
		}			
	});
	
	scrollPane_1.setViewportView(tblCustomer);
	

	scrollPane_1.setVisible(false);
	scrollPane_1.revalidate();
	scrollPane_1.repaint();
	
	// Init tables models
	this.dataModel = (DefaultTableModel)this.tblCompanies.getModel();
	this.dataModelcustomer = (DefaultTableModel)this.tblCustomer.getModel();
	btnCreateCompany.setBounds(24, 267, 169, 23);
	add(btnCreateCompany);
	btnCreateCompany.addActionListener(this);
}
	
	
	public void actionPerformed(ActionEvent e) 
	{
		// once create new company button pressed
		// create new company for admin
		if (e.getSource() == btnCreateCompany)
		{
			if (cmbCompanyOrCustomer.getSelectedItem().toString().equals("company"))
			{
				Company company = new Company(txtCompanyName.getText(), txtPassword.getText(), txtEmail.getText());
				
				try 
				{
					adminUser.createCompany(company);
					JOptionPane.showMessageDialog(getRootPane(), "Comapny was created, all update prior to creation are reset");
					Object[] rowData = new Object[] {company.getId(), company.getCompName(), 
							company.getPassword(),company.getEmail() };	
					
					dataModel.addRow(rowData);
					rowChangedCompanyTable = new byte[dataModel.getRowCount()];
				} 
				catch (FacadeException e1) 
				{	
					JOptionPane.showMessageDialog(getRootPane(), e1.getMessage());
				}
				
				catch (NullPointerException eNull)
				{
					JOptionPane.showMessageDialog(getRootPane(), "User is not login");
				}
			}
			else if (cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
			{
				Customer customer = new Customer(txtCompanyName.getText(), txtPassword.getText());
				
				try
				{
					adminUser.createCustomer(customer);
					JOptionPane.showMessageDialog(getRootPane(), "Customer was created, all update prior to creation are reset");
					Object[] rowData = new Object[] {customer.getId(), customer.getCustName(), 
							customer.getPassword() };	
					
					dataModelcustomer.addRow(rowData);
					rowChangedCustomerTable = new byte[dataModelcustomer.getRowCount()];
				}
				catch (FacadeException e1) 
				{	
					JOptionPane.showMessageDialog(getRootPane(), e1.getMessage());
				}
				
				catch (NullPointerException eNull)
				{
					JOptionPane.showMessageDialog(getRootPane(), "User is not login");
				}
				
			}
		}
		
		// once delete company button pressed
		// remove company for admin
		else if (e.getSource() == btnDeleteCompany)
		{
			if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("company"))
			{
				try 
				{			
					long id = (long)tblCompanies.getValueAt(tblCompanies.getSelectedRow(), 0);
					adminUser.removeCompany(adminUser.getCompany(id));
					((DefaultTableModel)tblCompanies.getModel()).removeRow(tblCompanies.getSelectedRow());
				} 
				
				catch (FacadeException eF) 
				{
					if (eF.getDAOExceptionType() == DAOExceptionErrorType.REMOVE_COMPANY_FAILED)
					{
						JOptionPane.showMessageDialog(getRootPane(), "Failed to delete company");
					}
				}
			}
			else if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
			{
				try 
				{				
					long id = (long)tblCustomer.getValueAt(tblCustomer.getSelectedRow(), 0);
					adminUser.removeCustomer(adminUser.getCustomer(id));
					((DefaultTableModel)tblCustomer.getModel()).removeRow(tblCustomer.getSelectedRow());
				} 
				
				catch (FacadeException eF) 
				{
					if (eF.getDAOExceptionType() == DAOExceptionErrorType.REMOVE_CUSTOMER_FAILED)
					{
						JOptionPane.showMessageDialog(getRootPane(), "Failed to delete customer");
					}
				}
			}
		}
		
		// update company for admin
		else if (e.getSource() == btnUpdateCompany)
		{
			try
			{
			if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("company"))
			{	
				for (int index = 0; index < rowChangedCompanyTable.length; index++)
				{
	
					if (rowChangedCompanyTable[index] == 1)
					{
						Company tempCompany = new Company();
						tempCompany.setId(Long.parseLong(tblCompanies.getValueAt(index, 0).toString()));
						tempCompany.setCompName(tblCompanies.getValueAt(index, 1).toString());
						tempCompany.setPassword((tblCompanies.getValueAt(index, 2).toString()));
						tempCompany.setEmail(((tblCompanies.getValueAt(index, 3).toString())));
						adminUser.updateCompany(tempCompany);
					}
				}
			}
			else if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
			{
				for (int index = 0; index < rowChangedCustomerTable.length; index++)
				{
					if (rowChangedCustomerTable[index] == 1)
					{
						Customer tempCustomer = new Customer();
						tempCustomer.setId(Long.parseLong(tblCustomer.getValueAt(index, 0).toString()));
						tempCustomer.setCustName(tblCustomer.getValueAt(index, 1).toString());
						tempCustomer.setPassword((tblCustomer.getValueAt(index, 2).toString()));
						adminUser.updateCustomer(tempCustomer);
					}
				}
			}
			
			}
			catch (FacadeException eFacde)
			{
				JOptionPane.showMessageDialog(getRootPane(), eFacde.getMessage());
			}
			
			
			
		}
		
		// search company and customer for admin
		else if (e.getSource() == btnSearch)
		{
			try
			{
				if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("company"))
				{
					fillCompanyTable();
				}
				else if (!textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("company"))
				{
					fillCompanyTableById(Long.parseLong(textSearchCompanyById.getText()));
				}
				
				else if (textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
				{
					fillCustomerTable();
				}
				else if (!textSearchCompanyById.getText().isEmpty() && cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
				{
					fillCustomerTableById(Long.parseLong(textSearchCompanyById.getText()));
				}
				
			}
			catch (NumberFormatException eNumber)
			{
				JOptionPane.showMessageDialog(getRootPane(), "Id must be a number");
			}
		}
		
		else if (e.getSource() == cmbCompanyOrCustomer)
		{
			// Change the table location and visibility
			
			if (cmbCompanyOrCustomer.getSelectedItem().toString().equals("customer"))
			{
				scrollPane_1.setVisible(true);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
				
				scrollPane.setVisible(false);
				scrollPane.revalidate();
				scrollPane.repaint();
				
				scrollPane_1.setLocation(468, 11);
				
				lblSearchCompany.setText("Search customer by id:");
				
				lblCompanyName.setText("Customer name:");
				lblCompanyEmail.setVisible(false);
				txtEmail.setVisible(false);
				
				rowChangedCustomerTable = new byte[dataModelcustomer.getRowCount()];
			
			}
			
			else
				
			{
				scrollPane_1.setVisible(false);
				scrollPane_1.revalidate();
				scrollPane_1.repaint();
				
				scrollPane.setVisible(true);
				scrollPane.revalidate();
				scrollPane.repaint();
				
				lblSearchCompany.setText("Search company by id:");

				lblCompanyName.setText("Company name:");
				lblCompanyEmail.setVisible(true);
				txtEmail.setVisible(true);
				
				rowChangedCompanyTable = new byte[dataModel.getRowCount()];
			}

		}
	}

	// method to support actions in the main frame
	
	private void resetTable()
	{
		//DefaultTableModel dataModel = (DefaultTableModel)tblCompanies.getModel();
		dataModel.setRowCount(0);
	}
	
	private void resetTableCustomer()
	{
		//DefaultTableModel dataModel = (DefaultTableModel)tblCompanies.getModel();
		dataModelcustomer.setRowCount(0);
	}
	
	private void fillCompanyTable()
	{				
		resetTable();		
		Collection<Company> companies;
		try 
		{
			companies = adminUser.getAllCompanies();
	
			for (Company tempCompany : companies)
			{
				Object[] rowData = new Object[] {tempCompany.getId(), tempCompany.getCompName(), 
						tempCompany.getPassword(),tempCompany.getEmail() };	
				
				dataModel.addRow(rowData);
			}
			
			rowChangedCompanyTable = new byte[dataModel.getRowCount()];
		} 
		
		catch (FacadeException e) 
		{
			if (e.getDAOExceptionType() == DAOExceptionErrorType.COMPANY_DETAILS_FAILED_TO_RETRIEVE)
			{
				JOptionPane.showMessageDialog(getRootPane(), "Failed to fill company table");
			}
		}
		
	}
	
	private void fillCompanyTableById(long id)
	{
		resetTable();
		Company company;
		
		try 
		{
			company = adminUser.getCompany(id);
			if (company != null)
			{
				Object[] rowData = new Object[] {company.getId(), company.getCompName(), company.getPassword(), company.getEmail()};
				dataModel.addRow(rowData);
				rowChangedCompanyTable = new byte[0];
			}
			else
			{
				JOptionPane.showMessageDialog(getRootPane(), "Company not found");
			}
		} 
		catch (FacadeException e) 
		{
			JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
		}

	}
	
	private void fillCustomerTable()
	{				
		resetTableCustomer();		
		Collection<Customer> customers;
		try 
		{
			customers = adminUser.getAllCustomer();
	
			for (Customer tempCustomer : customers)
			{
				Object[] rowData = new Object[] {tempCustomer.getId(), tempCustomer.getCustName(), 
						tempCustomer.getPassword() };	
				
				dataModelcustomer.addRow(rowData);
			}
			
			rowChangedCustomerTable = new byte[dataModelcustomer.getRowCount()];
		} 
		
		catch (FacadeException e) 
		{
			if (e.getDAOExceptionType() == DAOExceptionErrorType.CUSTOMER_DETAILS_FAILED_TO_RETRIEVE)
			{
				JOptionPane.showMessageDialog(getRootPane(), "Failed to fill customer table");
			}
		}
		
	}
	
	private void fillCustomerTableById(long id)
	{
		resetTableCustomer();
		Customer customer;
		
		try 
		{
			customer = adminUser.getCustomer(id);
			if (customer != null)
			{
				Object[] rowData = new Object[] {customer.getId(), customer.getCustName(), customer.getPassword()};
				dataModelcustomer.addRow(rowData);
				rowChangedCustomerTable = new byte[0];
			}
			else
			{
				JOptionPane.showMessageDialog(getRootPane(), "Customer not found");
			}
		} 
		catch (FacadeException e) 
		{
			JOptionPane.showMessageDialog(getRootPane(), e.getMessage());
		}

	}
}
