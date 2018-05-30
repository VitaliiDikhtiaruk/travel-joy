package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.impl.UserDatabaseDao;
import model.User;

/**
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	UserDao user = new UserDatabaseDao();
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {

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

			request.setAttribute("user", newUser);
			request.getRequestDispatcher("LogInWelcome.jsp").forward(request, response);
				
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
