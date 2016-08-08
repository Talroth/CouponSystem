package com.CouponSystem.testState;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.CouponSystem.DBDAO.DBDAO;




public class testState 
{
	public static void main(String[] args) throws SQLException
	{
			DBDAO pool = DBDAO.getInstance();
			
			String sql = "SELECT ID FROM couponsystem.company WHERE COMP_NAME = ? AND PASSWORD = ?";
			String compName = "t";
			String password = "t";
				
			pool.CreatePool();
			Connection con = pool.OpenConnection(); 
			PreparedStatement preparedStatement = con.prepareStatement(sql);
				
				// query command						
				preparedStatement.setString(1, compName);
				preparedStatement.setString(2, password);
				
				// query execution

				ResultSet rs = preparedStatement.executeQuery();
				
				System.out.println("rs status: " + rs.isClosed());
				if (rs.next())
				{
					System.out.println("log-in was successfuly performed");
					System.out.println(rs.getLong(1));
					System.out.println("hjhjh");
				}
				else
				{
					System.out.println("-1");
				}
				
			rs.close();
			preparedStatement.close();
			con.close();
			pool.CloseConnection();
	}
}
