package us.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement; 

public class TestJDBC {
	public String status="";
	private Connection connexion;
	
	public TestJDBC() {
		/* Chargement du driver JDBC pour MySQL */
		try {
		    Class.forName( "org.postgresql.Driver" );
		    
		    /* Connexion à la base de données */
		    //String url = "postgresql://localhost:5432/US";
		    String url = "jdbc:postgresql://localhost/US?user=ARNAUD&password=secret";
		    connexion = null;
		   		    
		    try {
		    	connexion = DriverManager.getConnection(url);
		        status="OK";

		        /* Ici, nous placerons nos requêtes vers la BDD */
		        /* ... */

		    } catch ( SQLException e ) {
		        /* Gérer les éventuelles erreurs ici */
		    	status=e.getMessage();
		    }// finally {
		    //    if ( connexion != null )
		    //        try {
		    //            /* Fermeture de la connexion */
		    //            connexion.close();
		    //        } catch ( SQLException ignore ) {
		    //            /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
		    //        }
		    //}
		} catch ( ClassNotFoundException e ) {
			status="Driver not found : " + e.getMessage();
		    /* Gérer les éventuelles erreurs ici. */
		}
	}

	public String getMessages() {
		// TODO Auto-generated method stub
		Statement statement = null;;
		ResultSet resultat = null;
		
		try {
			statement = connexion.createStatement();
			resultat = statement.executeQuery( "SELECT author FROM public.messages" );
			resultat.next();
			return resultat.getString("author");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			return e.getMessage();
		} finally {
		    if ( resultat != null ) {
		        try {
		            /* On commence par fermer le ResultSet */
		            resultat.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		    if ( statement != null ) {
		        try {
		            /* Puis on ferme le Statement */
		            statement.close();
		        } catch ( SQLException ignore ) {
		        }
		    }
		}
	}
	
	public String closeConnexion() {
		if ( connexion != null )
            try {
                /* Fermeture de la connexion */
                connexion.close();
            } catch ( SQLException e ) {
                /* Si une erreur survient lors de la fermeture, il suffit de l'ignorer. */
            	return e.getMessage();
            }
		return "Connexion Closed";
	}
}
