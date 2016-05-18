package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.EasyBlue.jdbc.DBConnection;

/**
 * Servlet implementation class GetStepWeekServlet
 */
@WebServlet("/GetStepWeekServlet")
public class GetStepWeekServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetStepWeekServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request, response);
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
						String userId = request.getParameter("userId").toString().trim();
						System.out.println(userId);
						String sqlStep = "select * from HealthData where uuid in ( select max(uuid) from HealthData where userId = ? group by updateTime ) limit 7";
						Object[] parStep = new Object[]{userId};
						ResultSet rsStep = db.ExecQuery(sqlStep, parStep);
						List<JSONObject> stepList = new ArrayList<JSONObject>();
						while(rsStep.next()){
							Map<String,String> cellMap = new HashMap<String,String>();
							cellMap.put("uuid", rsStep.getString("uuid").toString().trim());
							cellMap.put("step",rsStep.getString("step").toString().trim());
							cellMap.put("updateTime",rsStep.getString("updateTime").toString().trim() );
							JSONObject jsonCellMap = JSONObject.fromObject(cellMap);
							stepList.add(jsonCellMap);
						}
						String sqlMaxStep = "select max(step) as MaxStep from HealthData where uuid in ( select max(uuid) from HealthData where userId = ? group by updateTime ) limit 7";
						ResultSet rsMaxStep = db.ExecQuery(sqlMaxStep, parStep);
						StringBuilder msgMaxStep = new StringBuilder();
						
						while(rsMaxStep.next()){
							msgMaxStep.append(rsMaxStep.getString("MaxStep"));
						}
						String masMax = msgMaxStep.toString().trim();
						if(masMax.length() == 0){
							masMax = "0";
						}
						System.out.println(masMax);
						Map<String,Object> memberListMap = new HashMap<String,Object>();
						memberListMap.put("result", "1");
						memberListMap.put("StepList", stepList);
						memberListMap.put("MaxStep", masMax);
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
