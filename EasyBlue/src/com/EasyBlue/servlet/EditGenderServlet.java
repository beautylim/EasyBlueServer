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
 * Servlet implementation class EditGenderServlet
 */
@WebServlet("/EditGenderServlet")
public class EditGenderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditGenderServlet() {
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
	      String accessToken = request.getParameter("accessToken").trim();
			DBConnection db = new DBConnection();
			String sqlQueryUserInfo = "select * from Token where accessToken = ?";
			Object[] parAccessToken = new Object[]{accessToken};
			StringBuilder msgAccessToken = new StringBuilder();
			ResultSet rsAccessToken = db.ExecQuery(sqlQueryUserInfo, parAccessToken);
			try {
				    while(rsAccessToken.next()){
				    	msgAccessToken.append(rsAccessToken.getString("userId"));
				    }
					if(msgAccessToken.toString().trim().length() == 0){
						Map<String,String> map = new HashMap<String,String>();
						map.put("result", "000000");
						JSONObject json = JSONObject.fromObject(map);	
					     PrintWriter out = response.getWriter();
					     out.println(json);	
						 out.close();
					}else{
						    String gender = request.getParameter("gender").toString().trim();
							String updateTime = request.getParameter("updateTime").toString().trim();
							String sqlUser = "update UserInfo set gender = ?,updateTime = ? where uuid = ?";
							Object[] parUSer = new Object[]{gender,updateTime,msgAccessToken.toString().trim()};
							if(db.ExecOthers(sqlUser, parUSer) > 0){
								Map<String,Object> memberListMap = new HashMap<String,Object>();
								memberListMap.put("result", "1");
								JSONObject json = JSONObject.fromObject(memberListMap);
							     PrintWriter out = response.getWriter();
							     out.println(json);
								 out.close();
							}
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
