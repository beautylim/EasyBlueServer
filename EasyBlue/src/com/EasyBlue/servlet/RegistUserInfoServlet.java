package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.EasyBlue.entity.RandomGUID;
import com.EasyBlue.jdbc.DBConnection;

import java.sql.ResultSet;

import net.sf.json.JSONObject;

/**
 * Servlet implementation class RegistUserInfoServlet
 */
@WebServlet("/RegistUserInfoServlet")
public class RegistUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegistUserInfoServlet() {
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
	      String name = request.getParameter("name").toString().trim();
	      String passWord = request.getParameter("passWord").toString().trim();
	      String updateTime = request.getParameter("updateTime").toString().trim();
	      System.out.println(name);
	      System.out.println(tel);
	      System.out.println(passWord);
	      DBConnection db = new DBConnection();
		  String sqlUser = "insert into UserInfo values(null,?,0,'1970年1月1日','placeholder.png','1',?,?,?)";
		  Object[] parUser = new Object[]{name,tel,passWord,updateTime};
          if(db.ExecOthers(sqlUser, parUser)>0){
			    String sqlQueryUser = "select uuid from UserInfo where tel = ?";
			    Object[] parQueryUser = new Object[]{tel};
			    ResultSet rs = db.ExecQuery(sqlQueryUser, parQueryUser);
			    StringBuilder msg = new StringBuilder();
			    try {
					while(rs.next()){
						 msg.append(rs.getString("uuid"));
				     }
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		    	  //生成token
			    RandomGUID myGUID = new RandomGUID();
			    String accessToken = myGUID.toString();
				System.out.println(accessToken);
				String sqlToken = "insert into Token values(null,?,?,null)";
				String sqlUpdate = "update UserInfo set portraitUrl = ? where uuid = ?";
				Object[] parToken = new Object[]{accessToken,msg.toString().trim()};
				Object[] parTUpdate = new Object[]{msg.toString().trim()+".png",msg.toString().trim()};
				if(db.ExecOthers(sqlToken, parToken)>0 & db.ExecOthers(sqlUpdate, parTUpdate)>0 ){
					 Map<String,String> map = new HashMap<String,String>();
					 map.put("result", accessToken);
					 map.put("uuid", msg.toString().trim());
					 JSONObject json = JSONObject.fromObject(map);
				     PrintWriter out = response.getWriter();
				     out.println(json);	
					 out.close();
				 }
			  }
		  }
 } 
