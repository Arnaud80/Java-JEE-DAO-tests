package us.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.joda.time.DateTime;

import us.beans.Message;
import us.dao.DAOFactory;
import us.db.TestJDBC;

/**
 * Servlet implementation class US
 */
@WebServlet({"/listMessage", "/toto"})
public class ListMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private DAOFactory daoFactory=null;
		
    public void init() throws ServletException {
        /* Récupération d'une instance de notre DAO Utilisateur */
    	daoFactory=(DAOFactory)this.getServletContext().getAttribute("daofactory");
    }
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		List <Message> messageList;
		
		messageList = (List<Message>) session.getAttribute("messages");
				
		if(messageList == null) {
			response.getWriter().append("Liste de message vide\n");
			
			messageList = daoFactory.getMessageDAO().list(0,5);
		}
				
		session.setAttribute( "messages", messageList );
				
		this.getServletContext().getRequestDispatcher( "/WEB-INF/listMessage.jsp" ).forward( request, response );
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
