package us.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DAOFactory {
	private static HikariConfig config = new HikariConfig();
    private static HikariDataSource ds;
    
    private static final String PROPERTY_FILE       = "/us/dao/dao.properties";
    private static final String PROPERTY_URL        = "url";
    private static final String PROPERTY_DRIVER     = "driver";
    private static final String PROPERTY_USERNAME	= "username";
    private static final String PROPERTY_PASSWORD	= "password";
    private static final String PROPERTY_MAXPOOLSIZE= "maxPoolSize";

    /*private String url;
    private String username;
    private String password;*/

    DAOFactory() {
        /*config.setJdbcUrl(url);
    	config.setUsername(username);
    	config.setPassword(password);*/
    	
    	ds = new HikariDataSource(config);
    }

    /*
     * Méthode chargée de récupérer les informations de connexion à la base de
     * données, charger le driver JDBC et retourner une instance de la Factory
     */
    public static DAOFactory getInstance() throws DAOConfigurationException {
        Properties properties = new Properties();

        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream fichierProperties = classLoader.getResourceAsStream( PROPERTY_FILE );

        if ( fichierProperties == null ) {
            throw new DAOConfigurationException( "Le fichier properties " + PROPERTY_FILE + " est introuvable." );
        }

        try {
            properties.load( fichierProperties );
            config.setJdbcUrl(properties.getProperty( PROPERTY_URL ));
            config.setUsername(properties.getProperty( PROPERTY_USERNAME ));
            config.setPassword(properties.getProperty( PROPERTY_PASSWORD ));
            config.setMaximumPoolSize(Integer.parseInt(properties.getProperty( PROPERTY_MAXPOOLSIZE )));
        } catch ( IOException e ) {
            throw new DAOConfigurationException( "DAO property file unreachable " + PROPERTY_FILE, e );
        }

        try {
            Class.forName(properties.getProperty( PROPERTY_DRIVER ));
            config.setDriverClassName(properties.getProperty( PROPERTY_DRIVER ));
        } catch ( ClassNotFoundException e ) {
            throw new DAOConfigurationException( "Driver not found in the ClassPath.", e );
        }

        DAOFactory instance = new DAOFactory();
        return instance;
    }

    /* Méthode chargée de fournir une connexion à la base de données */
     /* package */ Connection getConnection() throws SQLException {
        //return DriverManager.getConnection( url, username, password );
    	 return ds.getConnection();
    }

    /*
     * Méthodes de récupération de l'implémentation des différents DAO (un seul
     * pour le moment)
     */
    public MessageDAO getMessageDAO() {
        return new MessageDAOImpl( this );
    }
}