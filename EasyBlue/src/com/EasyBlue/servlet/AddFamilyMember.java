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
 * Servlet implementation class AddFamilyMember
 */
@WebServlet("/AddFamilyMember")
public class AddFamilyMember extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddFamilyMember() {
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
		String tel= request.getParameter("tel").toString().trim();//家庭成员的tel
		String accessToken = request.getParameter("accessToken").toString().trim();//user token
		DBConnection db = new DBConnection();
		String sqlQueryUserInfo = "select * from Token where accessToken = ?";//check token is valid
		Object[] parAccessToken = new Object[]{accessToken};
		StringBuilder msgAccessToken = new StringBuilder();
		ResultSet rsAccessToken = db.ExecQuery(sqlQueryUserInfo, parAccessToken);
		try {
			while(rsAccessToken.next()){
				msgAccessToken.append(rsAccessToken.getString("userId"));
			}
			 Map<String,String> map = new HashMap<String,String>();
			if(msgAccessToken.toString().trim().length() == 0){//accessToken invalid
				map.put("result", "000000");
			}else{
				String sqlMemberInfo = "select * from UserInfo where tel = ?";//check family member is exist or not
				Object[] parMember = new Object[]{tel};
				StringBuilder msgMember = new StringBuilder();
				ResultSet rsMember = db.ExecQuery(sqlMemberInfo, parMember);
				while (rsMember.next()){
					msgMember.append(rsMember.getString("uuid"));// msgMember is family member's userid
				}
				if(msgMember.toString().trim().length() == 0){
					map.put("result", "000001");// 该家庭成员没有注册；
				}else{
					System.out.println(msgMember.toString().trim());
					System.out.println(msgAccessToken.toString().trim());
					if(msgMember.toString().trim().equals(msgAccessToken.toString().trim())){
						map.put("result", "000002");//家庭成员是用户自己
					}else{
						String sqlUser = "select * from UserInfo where uuid = ?";// select user's tel
						Object[] parUser = new Object[]{msgAccessToken.toString().trim()};
						StringBuilder msgTel = new StringBuilder();
						ResultSet rsUser = db.ExecQuery(sqlUser, parUser);
						while(rsUser.next()){
							msgTel.append(rsUser.getString("tel"));// msgTel is user's tel;
						}
						String sqlSelectTel = "select * from FamilyMember where userId = ? and tel = ?";//select relationship is exist or not
						Object[] parSelectTel = new Object[]{msgAccessToken.toString().trim(),tel};
						ResultSet rsSelectTel = db.ExecQuery(sqlSelectTel, parSelectTel);
						StringBuilder msgSelectTel = new StringBuilder();
						while(rsSelectTel.next()){
							msgSelectTel.append("1");
						}
						if(msgSelectTel.toString().trim().length() > 0){
							map.put("result", "000003");// 该家庭成员已经注册过；
						}else{
							String sqlFamily = "insert into FamilyMember values(null,?,?)";// insert user and family member
							Object[] parFamily = new Object[]{msgAccessToken.toString().trim(),tel};
							Object[] parFamily2 = new Object[]{msgMember.toString().trim(),msgTel.toString().trim()};//insert member and user
							if(db.ExecOthers(sqlFamily, parFamily) > 0 & db.ExecOthers(sqlFamily, parFamily2)>0){
								map.put("result", "1");//添加成功
							}else{
								map.put("result", "2");//添加失败
							}
						}
					}
					
				}
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
