package com.CouponSystem.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.CopyOption;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.Collection;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

import com.CouponSystem.Beans.Company;
import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.Facade.CompanyFacade;
import com.CouponSystem.FacadeException.FacadeException;
import com.mchange.io.FileUtils;

import DAOException.DAOExceptionErrorType;

public class CompanyPanel extends JPanel implements ActionListener
{

	private static final long serialVersionUID = 1L;

	private CompanyFacade companyUser = null;
	
	private JScrollPane scrollPane_3;
	private JRadioButton radioCouponTypeFilter;
	private JRadioButton radioCouponPriceFilter; 
	private JRadioButton radioCouponExpiryDateFilter; 
	private JComboBox<String> cmbCouponTypeForCompany;
	private JTextField txtMinPriceForCompany;
	private JTextField txtMaxPriceForCompany;
	private JTextField txtExpiryDateForCompany;
	private JButton btnFilterForCompany;
	private JButton btnAllForCompany;
	private JButton btnUploadImg;
	private JTable tblCouponForCompany;
	private DefaultTableModel dataModelcouponForCompany;
	private ButtonGroup filterGroup;
	private JLabel lblCompanyDetails;
	private JButton btnCreateCoupon = new JButton("Create");
	private JButton btnDeleteCoupon = new JButton("Delete");
	private JButton btnUpdateCoupon = new JButton("Update");
	
	private JTextField txtCouponName;
	private JTextField txtStartDate;
	private JTextField txtEndDate;
	private JTextField txtAmount;
	private JComboBox<String> cmbNewCouponType;
	private JTextField txtMessage;
	private JTextField txtPrice;
	private JTextField txtImageLocation;
	
	private byte[] rowChangedCoupon;
	
	public CompanyPanel(CompanyFacade user) 
	{
		companyUser = user;
									
		radioCouponTypeFilter = new JRadioButton("Coupon type");
		radioCouponTypeFilter.setBounds(10, 41, 117, 23);
		radioCouponTypeFilter.setSelected(true);
		
		
		radioCouponPriceFilter = new JRadioButton("Price");
		radioCouponPriceFilter.setBounds(10, 72, 104, 23);
		radioCouponPriceFilter.setSelected(true);
		
		radioCouponExpiryDateFilter = new JRadioButton("Expiry Date");
		radioCouponExpiryDateFilter.setBounds(10, 99, 104, 23);
		radioCouponExpiryDateFilter.setSelected(true);
		
		txtExpiryDateForCompany = new JTextField();
		txtExpiryDateForCompany.setBounds(133, 104, 166, 20);
		
		filterGroup = new ButtonGroup();
		filterGroup.add(radioCouponTypeFilter);
		filterGroup.add(radioCouponPriceFilter);
		filterGroup.add(radioCouponExpiryDateFilter);
		setLayout(null);
		
		lblCompanyDetails = new JLabel();
		lblCompanyDetails.setBounds(10, 446, 250, 45);
		try 
		{
			// Write company name and email on the panel
			Company companyTitle = companyUser.getCompanyDetails();
			lblCompanyDetails.setText("<html>Company: " + companyTitle.getCompName()  +  "<br/> Email: " + companyTitle.getEmail() + "</html>");
		} 
		catch (FacadeException e1) 
		{
			System.out.println("Fail to retreive company data");
		}			
		
		this.add(lblCompanyDetails);
		
		this.add(radioCouponTypeFilter);
		
		cmbCouponTypeForCompany = new JComboBox<String>();
		cmbCouponTypeForCompany.setBounds(133, 42, 166, 20);
		cmbCouponTypeForCompany.setModel(new DefaultComboBoxModel<String>(new String[] {"", "RESTURANS", "ELECTRICTY", "FOOD", "HEALTH", "SPORTS", "CAMPING", "TRAVELLING", "OTHER"}));
		
		this.add(cmbCouponTypeForCompany);
		this.add(radioCouponPriceFilter);
		
		txtMinPriceForCompany = new JTextField();
		txtMinPriceForCompany.setBounds(133, 73, 69, 20);
		this.add(txtMinPriceForCompany);
		
		txtMaxPriceForCompany = new JTextField();
		txtMaxPriceForCompany.setBounds(230, 73, 69, 20);
		this.add(txtMaxPriceForCompany);
		this.add(radioCouponExpiryDateFilter);
		
		this.add(txtExpiryDateForCompany);
		
		// Build coupon table
		tblCouponForCompany = new JTable();
		tblCouponForCompany.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tblCouponForCompany.setModel(new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "TITLE", "START_DATE", "END_DATE", "Amount", "TYPE", "MESSAGE", "PRICE", "IMAGE"
				}) {

			private static final long serialVersionUID = 1L;

			public boolean isCellEditable(int row, int column)
			{
				// allow END_TIME and PRICE columns editing only
				return (column == 3 || column == 7);
			}
			});
			
		tblCouponForCompany.getModel().addTableModelListener(new TableModelListener() {
			
			// When cell is edited, update the array which row was changed (only for END_DATE and PRICE)
			@Override
			public void tableChanged(TableModelEvent e) {
				int row = e.getFirstRow();
		        int column = e.getColumn();
				
		        if (column == 3 || column == 7) 
		        {
		        	rowChangedCoupon[row] = 1;
		        }				
				}			
			});
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(379, 5, 662, 427);
		this.add(scrollPane_3);
		
		scrollPane_3.setRowHeaderView(tblCouponForCompany);
				
		scrollPane_3.setViewportView(tblCouponForCompany);
		
		scrollPane_3.setVisible(true);
		scrollPane_3.revalidate();
		scrollPane_3.repaint();
		
		btnFilterForCompany = new JButton("Filter");
		btnFilterForCompany.setBounds(68, 141, 124, 23);
		btnFilterForCompany.addActionListener(this);
		this.add(btnFilterForCompany);
		
		btnAllForCompany = new JButton("Show all");
		btnAllForCompany.setBounds(214, 141, 144, 23);
		btnAllForCompany.addActionListener(this);
		this.add(btnAllForCompany);
		
		// Create button
		btnCreateCoupon.setBounds(68, 412, 136, 23);
		btnCreateCoupon.addActionListener(this);
		this.add(btnCreateCoupon);
		
		btnUploadImg = new JButton("Upload image");
		btnUploadImg.setBounds(220,412,136,23);
		btnUploadImg.addActionListener(this);
		this.add(btnUploadImg);
		
		JLabel lblCouponName = new JLabel("Title:");
		lblCouponName.setBounds(10, 175, 136, 14);
		this.add(lblCouponName);
		
		txtCouponName = new JTextField();
		txtCouponName.setBounds(156, 175, 104, 20);
		this.add(txtCouponName);
		
		JLabel lblStartDate = new JLabel("Start date:");
		lblStartDate.setBounds(10, 203, 136, 14);
		this.add(lblStartDate);
		
		txtStartDate = new JTextField(LocalDate.now().toString());
		txtStartDate.setBounds(158, 206, 102, 20);
		this.add(txtStartDate);
		
		JLabel lblEndDate = new JLabel("End date:");
		lblEndDate.setBounds(10, 231, 136, 14);
		this.add(lblEndDate);
		
		txtEndDate = new JTextField(LocalDate.now().plusMonths(3).toString());
		txtEndDate.setBounds(156, 237, 104, 20);
		this.add(txtEndDate);
		
		JLabel lblAmount = new JLabel("Amount:");
		lblAmount.setBounds(10, 265, 136, 14);
		this.add(lblAmount);
		
		txtAmount = new JTextField();
		txtAmount.setBounds(158, 268, 102, 20);
		this.add(txtAmount);
		
		JLabel lblCouponType = new JLabel("Type:");
		lblCouponType.setBounds(10, 299, 136, 14);
		this.add(lblCouponType);
		
		cmbNewCouponType = new JComboBox<String>();
		cmbNewCouponType.setBounds(158, 299, 102, 20);
		cmbNewCouponType.setModel(new DefaultComboBoxModel<String>(new String[] {"RESTURANS", "ELECTRICTY", "FOOD", "HEALTH", "SPORTS", "CAMPING", "TRAVELLING", "OTHER"}));
		this.add(cmbNewCouponType);
		
		JLabel lblMessage = new JLabel("Message:");
		lblMessage.setBounds(10, 327, 136, 14);
		this.add(lblMessage);
		
		txtMessage = new JTextField();
		txtMessage.setBounds(156, 327, 104, 20);
		this.add(txtMessage);
		
		JLabel lblPrice = new JLabel("Price:");
		lblPrice.setBounds(10, 358, 136, 14);
		this.add(lblPrice);
		
		txtPrice = new JTextField();
		txtPrice.setBounds(158, 358, 102, 20);
		this.add(txtPrice);
		
		JLabel lblImage = new JLabel("Image:");
		lblImage.setBounds(10, 386, 136, 14);
		this.add(lblImage);
		
		txtImageLocation = new JTextField();
		txtImageLocation.setBounds(158, 386, 102, 20);
		txtImageLocation.setEditable(false);
		this.add(txtImageLocation);
		
		btnDeleteCoupon.setBounds(502, 435, 144, 23);
		btnDeleteCoupon.addActionListener(this);
		this.add(btnDeleteCoupon);
						
		btnUpdateCoupon.setBounds(768, 435, 144, 23);
		btnUpdateCoupon.addActionListener(this);
		this.add(btnUpdateCoupon);
		
		this.dataModelcouponForCompany = (DefaultTableModel)this.tblCouponForCompany.getModel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// Show all company's coupons in the table
		if (e.getSource() == btnAllForCompany)
		{
			dataModelcouponForCompany.setRowCount(0);
			
			
			try 
			{
				Collection<Coupon> coupons;
				coupons = companyUser.getAllCoupons();
		
				fillCouponTableForCompanyUser(coupons);
				
				rowChangedCoupon = new byte[dataModelcouponForCompany.getRowCount()];
								
			} 
			
			catch (FacadeException eCouponsCustomer) 
			{
				if (eCouponsCustomer.getDAOExceptionType() == DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE)
				{
					JOptionPane.showMessageDialog(getRootPane(), "Failed to fill coupon table");
				}
			}
		}
		
		else if (e.getSource() == btnFilterForCompany)
		{
			// Filter the table according to the filter which was chosen from the radio buttons
			dataModelcouponForCompany.setRowCount(0);
						
			try
			{
				
				Collection<Coupon> coupons;
				coupons = null;
								
				switch (RadioButtonItenNumber(filterGroup))
				{
				// By coupon type
					case 0:
						coupons = companyUser.getCouponByType(CouponType.valueOf(cmbCouponTypeForCompany.getSelectedItem().toString()));
						break;
					// By price
					case 1:
						if (txtMinPriceForCompany.getText().isEmpty() || txtMaxPriceForCompany.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(getRootPane(), "Both min and max price must to be filled");
							break;
						}
						
						coupons = companyUser.getCouponByPrice(Double.parseDouble(txtMinPriceForCompany.getText()), Double.parseDouble(txtMaxPriceForCompany.getText()));
						break;
					// By expiration date	
					case 2:		
						coupons = companyUser.getCouponByEndDate(LocalDateTime.parse(txtExpiryDateForCompany.getText() + "T00:00"));
						break;
				
				}
				

				fillCouponTableForCompanyUser(coupons);
				
				rowChangedCoupon = new byte[dataModelcouponForCompany.getRowCount()];

				
			}
			catch (FacadeException eCouponsCustomer) 
			{
				if (eCouponsCustomer.getDAOExceptionType() == DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE)
				{
					JOptionPane.showMessageDialog(getRootPane(), "Failed to fill coupon table");
				}
			}
			
		}
		
		// Create new coupon for companies
				else if (e.getSource() == btnCreateCoupon)
				{
					try 
					{
						Coupon newCoupon = new Coupon();
						
						if (txtCouponName.getText().isEmpty() || txtMessage.getText().isEmpty() || txtImageLocation.getText().isEmpty())
						{
							JOptionPane.showMessageDialog(getRootPane(), "All fileds must to be filled");
							return;
						}
						
						newCoupon.setTitle(txtCouponName.getText());
						newCoupon.setStartDate(LocalDateTime.parse(txtStartDate.getText() + "T00:00"));
						newCoupon.setEndDate(LocalDateTime.parse(txtEndDate.getText() + "T00:00"));
						newCoupon.setAmount(Integer.parseInt(txtAmount.getText()));
						CouponType newType = CouponType.valueOf(cmbNewCouponType.getSelectedItem().toString());
						newCoupon.setType(newType);
						newCoupon.setMessage(txtMessage.getText());
						newCoupon.setPrice(Double.parseDouble(txtPrice.getText()));
						newCoupon.setImage(txtImageLocation.getText());
						
			
						companyUser.createCoupon(newCoupon);
						
						Object[] rowData = new Object[] {newCoupon.getId(), newCoupon.getTitle(), newCoupon.getStartDate(), newCoupon.getEndDate(), newCoupon.getAmount(), 
								newCoupon.getType(), newCoupon.getMessage(), newCoupon.getPrice(), newCoupon.getImage() };	
						
						dataModelcouponForCompany.addRow(rowData);
						rowChangedCoupon = new byte[dataModelcouponForCompany.getRowCount()];
						JOptionPane.showMessageDialog(getRootPane(), "New coupon was created, any update made without press update buton are reset");
					} 
					catch (FacadeException e1) 
					{
						JOptionPane.showMessageDialog(getRootPane(), e1.getMessage());
					}
					catch (DateTimeParseException eDateTime)
					{
						JOptionPane.showMessageDialog(getRootPane(), "Wrong date format, should be yyyy-MM-dd");
					}
					catch (NumberFormatException eNumberFormat)
					{
						JOptionPane.showMessageDialog(getRootPane(), "Amount and Price should be numbers");
					}
				}
		
		// update coupon for companies
		else if (e.getSource() == btnUpdateCoupon)
		{
			try
			{	
				for (int index = 0; index < rowChangedCoupon.length; index++)
				{
	
					if (rowChangedCoupon[index] == 1)
					{
						Coupon tempCoupon = new Coupon();	
						tempCoupon.setId((long)tblCouponForCompany.getValueAt(index, 0));
						tempCoupon.setTitle(tblCouponForCompany.getValueAt(index, 1).toString());
						tempCoupon.setStartDate(LocalDateTime.parse(tblCouponForCompany.getValueAt(index, 2).toString()));
						tempCoupon.setEndDate(LocalDateTime.parse(tblCouponForCompany.getValueAt(index, 3).toString()));
						tempCoupon.setAmount(Integer.parseInt(tblCouponForCompany.getValueAt(index, 4).toString()));
						tempCoupon.setType(CouponType.valueOf(tblCouponForCompany.getValueAt(index, 5).toString()));
						tempCoupon.setMessage(tblCouponForCompany.getValueAt(index, 6).toString());
						tempCoupon.setPrice(Double.parseDouble(tblCouponForCompany.getValueAt(index, 7).toString()));
						tempCoupon.setImage(tblCouponForCompany.getValueAt(index, 8).toString());

						companyUser.updateCoupon(tempCoupon);
					}
				}
		
			}
			catch (FacadeException eFacde)
			{
				JOptionPane.showMessageDialog(getRootPane(), eFacde.getMessage());
			}
					
		}
		// Delete coupon
		else if (e.getSource() == btnDeleteCoupon)
		{
				try 
				{				
					long id = (long)tblCouponForCompany.getValueAt(tblCouponForCompany.getSelectedRow(), 0);
					companyUser.removeCoupon(companyUser.getCoupon(id));
					((DefaultTableModel)tblCouponForCompany.getModel()).removeRow(tblCouponForCompany.getSelectedRow());
				} 
				
				catch (FacadeException eF) 
				{
					if (eF.getDAOExceptionType() == DAOExceptionErrorType.REMOVE_COMPANY_FAILED)
					{
						JOptionPane.showMessageDialog(getRootPane(), "Failed to delete coupon");
					}
				}
		}
		// open dialog window in order to upload new image for coupon
		else if (e.getSource() == btnUploadImg)
		{
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(getRootPane());
			if (returnVal == JFileChooser.APPROVE_OPTION)
			{
				File selectedFile = fc.getSelectedFile();
				
				
				try 
				{
					String path = new File(".").getCanonicalPath();
					File destFile = new File(path + File.separatorChar + "ImagesForCoupon" + 
							File.separatorChar + selectedFile.getName());
				    Files.copy(selectedFile.toPath(), destFile.toPath(), StandardCopyOption.COPY_ATTRIBUTES);
				    this.txtImageLocation.setText(selectedFile.getName());
				    JOptionPane.showMessageDialog(getRootPane(), "You have uploaded the file successfully");
				} 
				catch (FileAlreadyExistsException eExist)
				{
					JOptionPane.showMessageDialog(getRootPane(), "File already been uploaded");
				}
				catch (IOException eIO) 
				{
					JOptionPane.showMessageDialog(getRootPane(), "General file uploading error");
				}
			}
		}
		
	}
	// Fill the table with coupons list
	private void fillCouponTableForCompanyUser(Collection<Coupon> coupons)
	{
		if (coupons != null)
		{
			for (Coupon tempCoupon : coupons)
			{
				Object[] rowData = new Object[] {tempCoupon.getId(), tempCoupon.getTitle(), 
						tempCoupon.getStartDate(), tempCoupon.getEndDate(), tempCoupon.getAmount(), tempCoupon.getType(), tempCoupon.getMessage(),
						tempCoupon.getPrice(), tempCoupon.getImage() };	
				
				dataModelcouponForCompany.addRow(rowData);
			}
		}
	}
	// control the filters, return which filter was chosen
	private int RadioButtonItenNumber(ButtonGroup btnGroup)
	{
		int counter = 0;
	    for (Enumeration<AbstractButton> buttons = btnGroup.getElements(); buttons.hasMoreElements();)
	    {
	          AbstractButton button = buttons.nextElement();
	          if (button.isSelected()) 
	          {
	              return counter;
	          }
	          counter++;
	     }
	    return -1;
	}

}
