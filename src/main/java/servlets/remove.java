package servlets;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.TourDatabaseDao;
import model.Tour;

/**
 * Servlet implementation class remove
 */
@WebServlet("/remove")
public class remove extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public TourDatabaseDao tourDatabaseDao = new TourDatabaseDao();;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public remove() {
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			removeTour(request, response);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	private void removeTour(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Tour tour = new Tour(id);
		tourDatabaseDao.remove(tour);
		response.sendRedirect("TourControllerServlet");
		
	}
}
