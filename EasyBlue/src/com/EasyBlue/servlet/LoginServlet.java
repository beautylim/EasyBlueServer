package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.EasyBlue.jdbc.DBConnection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/json");
		String tel = request.getParameter("tel").toString().trim();
		String passWord = request.getParameter("passWord").toString().trim();
		DBConnection db = new DBConnection();
		String sqlQuery = "select * from UserInfo where tel = ? and passWord = ?";
		Object[] par = new Object[]{tel,passWord};
		ResultSet rs = db.ExecQuery(sqlQuery, par);
		StringBuilder msg = new StringBuilder();
		try {
			while(rs.next()){
				msg.append(rs.getString("uuid"));
			}
			Map<String,String> map = new HashMap<String,String>();
			if(msg.toString().trim().length()>0){
				String sqlToken = "select * from Token where userId = ?";
				Object[] parToken = new Object[]{msg.toString().trim()};
				ResultSet rsToken = db.ExecQuery(sqlToken, parToken);
				StringBuilder msgToken = new StringBuilder();
				while(rsToken.next()){
					msgToken.append(rsToken.getString("accessToken"));
				}
				 map.put("result", msgToken.toString().trim());
				 map.put("uuid",msg.toString().trim());
			}else{
				map.put("result","000000");
			}
			JSONObject json = JSONObject.fromObject(map);
		     PrintWriter out = response.getWriter();
		     out.println(json);	
			 out.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
