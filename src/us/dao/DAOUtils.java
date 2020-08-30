package us.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public final class DAOUtils {
	/*
	 * Initialise la requête préparée basée sur la connexion passée en argument,
	 * avec la requête SQL et les objets donnés.
	 */
	public static PreparedStatement initializePreparedRequest( Connection connection, String sql, boolean returnGeneratedKeys, Object... objets ) throws SQLException {
	    PreparedStatement preparedStatement = connection.prepareStatement( sql, returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS );
	    for ( int i = 0; i < objets.length; i++ ) {
	        preparedStatement.setObject( i + 1, objets[i] );
	    }
	    return preparedStatement;
	}

	/* Closing */
	public static void close(Object... objects) {
		for ( int i = 0; i < objects.length; i++ ) {
			if(objects[i]!=null) {
				switch(objects[i].getClass().getSimpleName()) {
					case "PgResultSet" :
						ResultSet resultSet = (ResultSet)objects[i];
						try {
							resultSet.close();
						} catch (SQLException e) {
							System.out.println( "closing of"+resultSet.getClass().getSimpleName()+"failed : " + e.getMessage() );
						}
						break;
					case "PgPreparedStatement" :
						Statement statement = (Statement)objects[i];
						try {
							statement.close();
						} catch (SQLException e) {
							System.out.println( "closing of"+statement.getClass().getSimpleName()+"failed : " + e.getMessage() );
						}
						break;
					case "PgConnection" :
						Connection connection = (Connection)objects[i];
						try {
							connection.close();
						} catch (SQLException e) {
							System.out.println( "closing of"+connection.getClass().getSimpleName()+"failed : " + e.getMessage() );
						}
						break;
					default:
						System.out.println("close() function not implemented for Object " + objects[i].getClass().getSimpleName());
						break;
				}
			}
	    }
	}
}
