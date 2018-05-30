package servlets;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.impl.UserDatabaseDao;
import model.User;

/**
 * Servlet implementation class LoginRegister
 */
@WebServlet("/LoginRegister")
public class LoginRegister extends HttpServlet {
	UserDao user = new UserDatabaseDao();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginRegister() {

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		User newUser = new User();
		String login = request.getParameter("login");
		String password = request.getParameter("password");
		String submitType = request.getParameter("submit");
		if (submitType.equals("login")) {

			newUser = user.getUser(login, password);
			if (newUser.getLogin().equals(null)) {
				System.out.println("user login failed");

				request.getRequestDispatcher("errorpage.jsp").forward(request, response);
			} else {
				System.out.println("redirect falied");
			// request.setAttribute("message", newUser.getName());
			// request.getRequestDispatcher("LogInWelcome.jsp").forward(request, response);

			request.setAttribute("successMessage", "reristration done, pls log in to continue");
			request.getRequestDispatcher("LogInWelcome.jsp").forward(request, response);
				
			}
		}
		String name = request.getParameter("name");
		String lastName = request.getParameter("lastname");
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		java.sql.Date sql;
		try {
			sql = new java.sql.Date((format.parse(request.getParameter("dob"))).getTime());
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String email = request.getParameter("email");
		String password2 = request.getParameter("password2");

		if (submitType.equals("register")) {
			try {
				newUser.setName(request.getParameter("name"));
				newUser.setLastName(request.getParameter("lastname"));
				newUser.setDob(new java.sql.Date((format.parse(request.getParameter("dob"))).getTime()));
				newUser.setEmail(request.getParameter("email"));
				newUser.setLogin(request.getParameter("login"));
				newUser.setPassword(request.getParameter("password"));
				newUser.setRole("user");
			} catch (Exception e2) {
				System.out.println(e2);
			}
			try {
				user.add(newUser);
			} catch (SQLException e) {
				System.out.println(e);
			}
			request.setAttribute("user", newUser);
			request.getRequestDispatcher("LogInWelcome.jsp").forward(request, response);
		}
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);
	}

}
