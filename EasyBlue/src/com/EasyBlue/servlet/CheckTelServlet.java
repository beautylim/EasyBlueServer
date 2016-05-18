package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.EasyBlue.jdbc.DBConnection;

/**
 * Servlet implementation class CheckTelServlet
 */
@WebServlet("/CheckTelServlet")
public class CheckTelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckTelServlet() {
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
	      System.out.println(tel);
		  DBConnection db = new DBConnection();
	      //check tel
	      String sqlQueryTel = "select * from UserInfo where tel =?";
	      Object[] parQueryTel = new Object[]{tel};
	      ResultSet rsTel = db.ExecQuery(sqlQueryTel, parQueryTel);
	      StringBuilder msgTel = new StringBuilder();
	      try {
			while(rsTel.next()){
				msgTel.append(rsTel.getString("uuid"));
			  }
			System.out.println(msgTel.toString());
			Map<String,String> mapError = new HashMap<String,String>();
			if(msgTel.toString().trim().length() > 0){
				mapError.put("result", "2");//手机重复
				  System.out.println(1);
			}else{
				mapError.put("result", "1");//手机不重复
				  System.out.println(0);
			}
			JSONObject json = JSONObject.fromObject(mapError);
			System.out.println(json);
			PrintWriter out = response.getWriter();
			out.println(json);	
			out.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
