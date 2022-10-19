package xenonstack;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uemail=request.getParameter("username");
		String upwd=request.getParameter("password");
		HttpSession session=request.getSession();
		RequestDispatcher dispatcher=null;
		try {
			PrintWriter out=response.getWriter();
			Class.forName("org.sqlite.JDBC");
			Connection conn = DriverManager.getConnection("jdbc:sqlite:\\D:\\xenonstack.db");
			
			PreparedStatement pst=conn.prepareStatement("select * from registerInfo where email=? and password=?");
			pst.setString(1, uemail);
			pst.setString(2, upwd);
			
			ResultSet rs= pst.executeQuery();
			if(rs.next())
			{
				session.setAttribute("name", rs.getString("name"));
				dispatcher=request.getRequestDispatcher("index.jsp");
				
			}else{
				request.setAttribute("status", "failed");
				dispatcher = request.getRequestDispatcher("login.jsp");
			}
			dispatcher.forward(request, response);
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
