package com.EasyBlue.jdbc;

import java.sql.*;
public class DBConnection {
		private Connection conn=null;
		private PreparedStatement prep=null;
		private ResultSet rs=null;
		private void GetConnection(){		
			try {
				Class.forName("com.mysql.jdbc.Driver");
				String param="jdbc:mysql://127.0.0.1:3306/sys?characterEncoding=utf8";
				this.conn=DriverManager.getConnection(param, "root","201314");
			} catch (Exception e) {
				e.printStackTrace();
			} 
		}
	
		public void CloseConnection(){
			//ResultSet
			if(this.rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(this.prep!=null){
				try {
					prep.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(this.conn!=null){
				try {
					conn.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		public ResultSet ExecQuery(final String sql,Object[] params){
			this.GetConnection();
			try {
							
				this.prep=conn.prepareStatement(sql);
				
				if(params.length>0){
					
					for (int i = 0; i < params.length; i++) {
						prep.setObject(i+1, params[i]);
					}				
				}
				rs=prep.executeQuery();
				return rs;
			} catch (SQLException e) {
				e.printStackTrace();
				return null;
			}		
		}

		public int ExecOthers(final String sql,Object[] params){
			this.GetConnection();
			try {
				this.prep=conn.prepareStatement(sql);
				if(params.length>0){
					for (int i = 0; i < params.length; i++) {
						prep.setObject(i+1, params[i]);
						System.out.println(params[i]);
					}
				}
				return prep.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return -1;
			}		
		}
		/**
		 * @param args
		 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
//        DBConnection db=new DBConnection();
	}

}
