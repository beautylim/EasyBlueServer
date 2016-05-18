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
 * Servlet implementation class GetFMDetailServlet
 */
@WebServlet("/GetFMDetailServlet")
public class GetFMDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFMDetailServlet() {
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
	      String accessToken = request.getParameter("accessToken").toString().trim();
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
						String memberTel = request.getParameter("memberTel").toString().trim();
						String sqlFamily = "select * from UserInfo where tel = ?";
						Object[] parFamily = new Object[]{memberTel};
						ResultSet rsFamily = db.ExecQuery(sqlFamily, parFamily);
						Map<String,String> cellMap = new HashMap<String,String>();
						while(rsFamily.next()){
							cellMap.put("uuid", rsFamily.getString("uuid").toString().trim());
							cellMap.put("gender",rsFamily.getString("gender").toString().trim());
							cellMap.put("bornDate",rsFamily.getString("bornDate").toString().trim() );
							cellMap.put("name",rsFamily.getString("name").toString().trim() );
							cellMap.put("portraitUrl",rsFamily.getString("portraitUrl").toString().trim() );
							cellMap.put("tel",rsFamily.getString("tel") );
							cellMap.put("placeId",rsFamily.getString("placeId") );
							cellMap.put("updateTime",rsFamily.getString("updateTime") );
						}
						JSONObject jsonCellMap = JSONObject.fromObject(cellMap);
						Map<String,Object> memberListMap = new HashMap<String,Object>();
						memberListMap.put("result", "1");
						memberListMap.put("MemberInfo", jsonCellMap);
						JSONObject json = JSONObject.fromObject(memberListMap);
					     PrintWriter out = response.getWriter();
					     out.println(json);
						 out.close();
					}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}

}
