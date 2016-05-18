package com.EasyBlue.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import net.sf.json.JSONObject;

import com.jspsmart.upload.SmartUpload;
import com.jspsmart.upload.SmartUploadException;

/**
 * Servlet implementation class UplodImageServlet
 */
@WebServlet("/UplodImageServlet")
public class UplodImageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private ServletConfig config;
	@Override
	public void init(ServletConfig config) throws ServletException {
		this.config=config;
	}
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UplodImageServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		this.doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html");
		SmartUpload su = new SmartUpload();
		su.initialize(config, request, response);
		su.setTotalMaxFileSize(4096*1024);
		try {
			su.upload();
			System.out.println(su.getFiles().getCount());
			com.jspsmart.upload.File file = su.getFiles().getFile(0);
			String fileName = file.getFieldName();
			System.out.println(file);
			Map<String,String> map = new HashMap<String,String>();
			file.saveAs("images/"+fileName+".png",su.SAVE_VIRTUAL);
			map.put("result", "1");
			JSONObject json = JSONObject.fromObject(map);
		    PrintWriter out = response.getWriter();
		    out.println(json);	
			out.close();
		} catch (SmartUploadException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
