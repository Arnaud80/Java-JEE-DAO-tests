package us.servlets;

import java.io.IOException;
import java.sql.Timestamp;
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

/**
 * Servlet implementation class US
 */
@WebServlet("/addMessage")
public class AddMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DAOFactory daoFactory=(DAOFactory)this.getServletContext().getAttribute("daofactory");
		
		String newAuthor = request.getParameter( "author" );
        String newMessage = request.getParameter( "message" );
        
        HttpSession session = request.getSession();
		List<Message> messageList;
		
		messageList = (List<Message>) session.getAttribute( "messages");
        
		if ( newAuthor.trim().isEmpty() || newMessage.trim().isEmpty() ) {
				response.getWriter().append("Erreur - Vous n'avez pas rempli tous les champs obligatoires. <br> <a href=\\\"listMessage.jsp\\\">Cliquez ici</a> pour accéder au formulaire de création d'un client.\"");
	       } else {
	    	    Message message = new Message();
				message.setAuthor(newAuthor);
				message.setMessage(newMessage);
				
				daoFactory.getMessageDAO().create(message);
				
				messageList.add(message);
	       }
		
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
