package us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import us.beans.Message;

import static us.dao.DAOUtils.*;

public class MessageDAOImpl implements MessageDAO {
	private static final String SQL_SELECT_BY_ID = "SELECT id, author, message, creationdate FROM messages WHERE id = ?";
	private static final String SQL_SELECT_ALL = "SELECT id, author, message, creationdate FROM messages OFFSET ? LIMIT ?";
	private static final String SQL_INSERT = "INSERT INTO messages (author, message, creationdate) VALUES (?, ?, NOW())";	
	private DAOFactory daoFactory;
	
	MessageDAOImpl(DAOFactory daoFactory) {
		this.daoFactory=daoFactory;
	}
	
	@Override
	public void create(Message message) throws IllegalArgumentException, DAOException {
		Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    ResultSet idAutoIncremented = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        preparedStatement = initializePreparedRequest(connection, SQL_INSERT, true, message.getAuthor(), message.getMessage() );
	        
	        int statut = preparedStatement.executeUpdate();
	        /* Analyse du statut retourné par la requête d'insertion */
	        if ( statut == 0 ) {
	            throw new DAOException( "Échec de la création de l'utilisateur, aucune ligne ajoutée dans la table." );
	        }
	        /* Récupération de l'id auto-généré par la requête d'insertion */
	        idAutoIncremented = preparedStatement.getGeneratedKeys();
	        if ( idAutoIncremented.next() ) {
	            /* Puis initialisation de la propriété id du bean Utilisateur avec sa valeur */
	            message.setId( idAutoIncremented.getLong( 1 ) );
	        } else {
	            throw new DAOException( "Échec de la création de l'utilisateur en base, aucun ID auto-généré retourné." );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        close( resultSet, preparedStatement, connection );
	    }
	}

	@Override
	public Message research(Integer id) throws DAOException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    Message message = null;

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        preparedStatement = initializePreparedRequest(connection, SQL_SELECT_BY_ID, false, id );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        if ( resultSet.next() ) {
	            message = map( resultSet );
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        close( resultSet, preparedStatement, connection );
	    }

	    return message;
	}
	
	@Override
	public List <Message> list(Integer offset, Integer limit) throws DAOException {
	    Connection connection = null;
	    PreparedStatement preparedStatement = null;
	    ResultSet resultSet = null;
	    List <Message> messages=new ArrayList<Message>();

	    try {
	        /* Récupération d'une connexion depuis la Factory */
	        connection = daoFactory.getConnection();
	        preparedStatement = initializePreparedRequest(connection, SQL_SELECT_ALL, false, offset, limit );
	        resultSet = preparedStatement.executeQuery();
	        /* Parcours de la ligne de données de l'éventuel ResulSet retourné */
	        while( resultSet.next() ) {
	        	messages.add(map( resultSet ));
	        }
	    } catch ( SQLException e ) {
	        throw new DAOException( e );
	    } finally {
	        close( resultSet, preparedStatement, connection );
	    }

	    return messages;
	}

	@Override
	public void update(Message message) throws DAOException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(Message message) throws DAOException {
		// TODO Auto-generated method stub
		
	}
	
	/*
	 * Simple méthode utilitaire permettant de faire la correspondance (le
	 * mapping) entre une ligne issue de la table des utilisateurs (un
	 * ResultSet) et un bean Utilisateur.
	 */
	private static Message map( ResultSet resultSet ) throws SQLException {
	    Message message = new Message();
	    message.setId( resultSet.getLong( "id" ) );
	    message.setAuthor( resultSet.getString( "author" ) );
	    message.setMessage( resultSet.getString( "message" ) );
	    return message;
	}
}
