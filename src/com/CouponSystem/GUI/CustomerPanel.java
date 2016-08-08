package com.CouponSystem.GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import com.CouponSystem.Beans.Coupon;
import com.CouponSystem.Beans.CouponType;
import com.CouponSystem.Facade.CustomerFacade;
import com.CouponSystem.FacadeException.FacadeException;

import DAOException.DAOExceptionErrorType;

public class CustomerPanel extends JPanel implements ActionListener 
{

	private static final long serialVersionUID = 1L;

	private JScrollPane scrollPane_2;
	private JTable tblCustomerForCustomer;
	private JComboBox<String> cmbCouponType;
	private JTextField txtMaxPrice;
	private JButton btnFilter;
	private JButton btnAll;
	private DefaultTableModel dataModelcustomerForCustomer; 
	private JButton btnShowCouponsForPurchase;
	private JButton btnPurchase;
	
	private CustomerFacade customerUser = null;
	
	public CustomerPanel(CustomerFacade user) 
	{
		customerUser = user;
		setLayout(null);
		
		JLabel lblCouponTypeFilter = new JLabel("Coupon type");
		lblCouponTypeFilter.setBounds(10, 36, 97, 14);
		this.add(lblCouponTypeFilter);
		
		cmbCouponType = new JComboBox<>();
		cmbCouponType.setBounds(117, 33, 101, 20);
		cmbCouponType.setModel(new DefaultComboBoxModel<String>(new String[] {"", "RESTURANS", "ELECTRICTY", "FOOD", "HEALTH", "SPORTS", "CAMPING", "TRAVELLING", "OTHER"}));
		cmbCouponType.addActionListener(this);
		this.add(cmbCouponType);
		
		JLabel lblMaxPrice = new JLabel("Max price");
		lblMaxPrice.setBounds(10, 75, 97, 14);
		this.add(lblMaxPrice);
		
		txtMaxPrice = new JTextField();
		txtMaxPrice.setBounds(117, 72, 101, 20);
		txtMaxPrice.addActionListener(this);
		this.add(txtMaxPrice);
			
		btnFilter = new JButton("Filter");
		btnFilter.setBounds(10, 113, 97, 23);
		btnFilter.addActionListener(this);
		this.add(btnFilter);
		
		btnAll = new JButton("Show all");
		btnAll.setBounds(127, 113, 91, 23);
		btnAll.addActionListener(this);
		this.add(btnAll);
		
		btnShowCouponsForPurchase = new JButton("Show all coupons for purchasing");
		btnShowCouponsForPurchase.setBounds(352, 471, 187, 23);
		btnShowCouponsForPurchase.addActionListener(this);
		this.add(btnShowCouponsForPurchase);
		
		btnPurchase = new JButton("Purchase chosen coupon");
		btnPurchase.setBounds(662, 471, 187, 23);
		btnPurchase.addActionListener(this);
		this.add(btnPurchase);
		
		tblCustomerForCustomer = new JTable();
		tblCustomerForCustomer.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		tblCustomerForCustomer.setModel(new DefaultTableModel(
				new Object[][] { 
				},
				new String[] {
					"ID", "TITLE", "START_DATE", "END_DATE", "Amount", "TYPE", "MESSAGE", "PRICE", "IMAGE"
				}) {

				private static final long serialVersionUID = 1L;

				public boolean isCellEditable(int row, int column)
				{
					return false;
				}
				
				   public Class<?> getColumnClass(int c) {
			            return getValueAt(0, c).getClass();
			        }
			}
			);
			
		tblCustomerForCustomer.setRowHeight(50);
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(266, 33, 688, 427);
		this.add(scrollPane_2);
		
		scrollPane_2.setRowHeaderView(tblCustomerForCustomer);
				
		scrollPane_2.setViewportView(tblCustomerForCustomer);
		
		scrollPane_2.setVisible(true);
		scrollPane_2.revalidate();
		scrollPane_2.repaint();
		
		this.dataModelcustomerForCustomer = (DefaultTableModel)this.tblCustomerForCustomer.getModel();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if (e.getSource() == btnAll)
		{
			dataModelcustomerForCustomer.setRowCount(0);
			
			try 
			{
				Collection<Coupon> coupons;
				coupons = customerUser.getAllPurchasedCoupons();
		
				fillCustomerTableForCustomerUser(coupons);
								
			} 
			
			catch (FacadeException eCouponsCustomer) 
			{
				if (eCouponsCustomer.getDAOExceptionType() == DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE)
				{
					JOptionPane.showMessageDialog(getRootPane(), "Failed to fill coupon table");
				}
			}
		}
		
		else if (e.getSource() == btnFilter)
		{
			dataModelcustomerForCustomer.setRowCount(0);
						
			try
			{
				Collection<Coupon> coupons;
				coupons = null;
				if (!cmbCouponType.getSelectedItem().equals(""))
				{
					coupons = customerUser.getAllPurchasedCouponsByType(CouponType.valueOf(cmbCouponType.getSelectedItem().toString()));
				}
				
				if (!txtMaxPrice.getText().equals(""))
				{
					coupons = customerUser.getAllPurchasedCouponsByPrice(Double.parseDouble(txtMaxPrice.getText()));
				}
				
				fillCustomerTableForCustomerUser(coupons);
				
				
			}
			catch (FacadeException eCouponsCustomer) 
			{
				if (eCouponsCustomer.getDAOExceptionType() == DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE)
				{
					JOptionPane.showMessageDialog(getRootPane(), "Failed to fill coupon table");
				}
			}
			
		}
		
		else if (e.getSource() == btnShowCouponsForPurchase)
		{
			dataModelcustomerForCustomer.setRowCount(0);
			
			try 
			{
				Collection<Coupon> coupons;
				coupons = customerUser.getAllCoupons();
		
				fillCustomerTableForCustomerUser(coupons);
								
			} 
			
			catch (FacadeException eCouponsCustomer) 
			{
				if (eCouponsCustomer.getDAOExceptionType() == DAOExceptionErrorType.COUPON_DETAILS_FAILED_TO_RETRIEVE)
				{
					JOptionPane.showMessageDialog(getRootPane(), "Failed to fill coupon table");
				}
			}
		}
		
		else if (e.getSource() == btnPurchase)
		{
			try 
			{				
				long id = (long)tblCustomerForCustomer.getValueAt(tblCustomerForCustomer.getSelectedRow(), 0);
				if (id == -1)
				{
					JOptionPane.showMessageDialog(getRootPane(),"You should choose coupon first");
					return;
				}
				customerUser.purchaseCoupon(customerUser.getCoupon(id));
				JOptionPane.showMessageDialog(getRootPane(), "Coupon was purchased sucessfully");
			} 
			
			catch (FacadeException eF) 
			{
				JOptionPane.showMessageDialog(getRootPane(),eF.getMessage());
			}
		}
	}
	
	private void fillCustomerTableForCustomerUser(Collection<Coupon> coupons)
	{
		ImageIcon ico = null;
		if (coupons != null)
		{
			for (Coupon tempCoupon : coupons)
			{
				ico = new ImageIcon("ImagesForCoupon/" + tempCoupon.getImage());
				if (ico.getIconHeight() == -1)
				{
					ico = new ImageIcon("ImagesForCoupon/couponPng.png");
				}
				Object[] rowData = new Object[] {tempCoupon.getId(), tempCoupon.getTitle(), 
						tempCoupon.getStartDate(), tempCoupon.getEndDate(), tempCoupon.getAmount(), tempCoupon.getType(), tempCoupon.getMessage(),
						tempCoupon.getPrice(), ico };	
				
				
				dataModelcustomerForCustomer.addRow(rowData);
			}
		}
	}
	


}
