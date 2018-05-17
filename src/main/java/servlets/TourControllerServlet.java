package servlets;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.impl.TourDatabaseDao;
import model.Tour;

/**
 * Servlet implementation class TourControllerServlet
 */
@WebServlet("TourControllerServlet")
public class TourControllerServlet extends HttpServlet {
	private static Logger log = Logger.getLogger(TourControllerServlet.class.getName());
	private static final long serialVersionUID = 1L;
		public TourDatabaseDao tourDatabaseDao;
       
       public void init() {
	    String jdbcURL = getServletContext().getInitParameter("jdbc:mysql://localhost:3306/travel_agency");
		String jdbcUsername = getServletContext().getInitParameter("root");
		String jdbcPassword = getServletContext().getInitParameter("root");
		
		tourDatabaseDao = new TourDatabaseDao(jdbcURL, jdbcUsername, jdbcPassword);
   }
       /**
   	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
   	 */
   	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

   		doGet(request, response);
   	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String action = request.getServletPath();
			try {
				switch(action) {
				case"/new":
					showNewForm(request, response);
					break;
				case"/insert":
					insertTour(request, response);
					break;
				case"/remove":
					removeTour(request, response);
					break;
				case"/update":
					updateTour(request, response);
					break;
				case"/edit":
					showEditForm(request, response);
					break;
				default:
					listTours(request, response);
					break;
				}
			} catch(SQLException e) {
				log.warning("doGet action problem");
				throw new ServletException();
			} catch (ParseException e) {
				log.warning("can't parse sql date from date");
				throw new ServletException();
			}
	}

	private void showEditForm(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Tour existingTour = tourDatabaseDao.getById(id);
		RequestDispatcher dispatcher = request.getRequestDispatcher("TourForm.jsp");
		request.setAttribute("tour", existingTour);
		dispatcher.forward(request, response);
		
	}
	private void updateTour(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		int price = Integer.parseInt(request.getParameter("price"));
		String strDate = request.getParameter("date");
		DateFormat format = new SimpleDateFormat("YYYY-MM-DD");
		Date date = new java.sql.Date((format.parse(strDate).getTime()));
		String countries = request.getParameter("countries");
		Tour tour = new Tour(id, name, description, price, date, countries);
		tourDatabaseDao.update(tour);
		response.sendRedirect("tours");
		
	}
	private void removeTour(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		Tour tour = new Tour(id);
		tourDatabaseDao.remove(tour);
		response.sendRedirect("tours");
		
	}
	private void insertTour(HttpServletRequest request, HttpServletResponse response) throws ParseException, SQLException, IOException {
		String name = request.getParameter("name");
				String description = request.getParameter("description");
				int price = Integer.parseInt(request.getParameter("price"));
				String strDate = request.getParameter("date");
				DateFormat format = new SimpleDateFormat("YYYY-MM-DD");
				Date date = new java.sql.Date((format.parse(strDate).getTime()));
				String countries = request.getParameter("countries");
				Tour tour = new Tour(name, description, price, date, countries);
				tourDatabaseDao.add(tour);
				response.sendRedirect("tours");
		
	}
	private void showNewForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher dispatcher = request.getRequestDispatcher("TourForm.jsp");
		dispatcher.forward(request, response);
		
	}
	protected void listTours(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		List<Tour> listTours = tourDatabaseDao.getAll();
		request.setAttribute("tours", listTours);
		RequestDispatcher dispatcher = request.getRequestDispatcher("ToursList.jsp");
		dispatcher.forward(request, response);
	}
}
