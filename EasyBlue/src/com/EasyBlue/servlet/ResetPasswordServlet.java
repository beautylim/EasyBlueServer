package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;
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
 * Servlet implementation class ResetPasswordServlet
 */
@WebServlet("/ResetPasswordServlet")
public class ResetPasswordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ResetPasswordServlet() {
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
		String type = request.getParameter("type").toString().trim();
		int typeInt = Integer.parseInt(type);
		DBConnection db = new DBConnection();
		if(typeInt == 0){//找回密码
			String tel = request.getParameter("tel").toString().trim();
			String passWord = request.getParameter("passWord").toString().trim();
			String sqlUser = "update UserInfo set passWord=? where tel=?";
			Object[] parUser = new Object[]{passWord,tel};
			if(db.ExecOthers(sqlUser, parUser)>0){
				 Map<String,String> map = new HashMap<String,String>();
				 map.put("result", "1");
				 JSONObject json = JSONObject.fromObject(map);
			     PrintWriter out = response.getWriter();
			     out.println(json);	
				 out.close();
			}else{
				 Map<String,String> map = new HashMap<String,String>();
				 map.put("result", "2");
				 JSONObject json = JSONObject.fromObject(map);
			     PrintWriter out = response.getWriter();
			     out.println(json);	
				 out.close();
			}
		}
	}

}
