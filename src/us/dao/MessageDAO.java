package us.dao;

import java.util.List;

import us.beans.Message;

public interface MessageDAO {
	void create( Message message) throws DAOException;

	Message research( Integer id ) throws DAOException;
	
	void update(Message message) throws DAOException;
	
	void delete(Message message) throws DAOException;

	List <Message> list(Integer offset, Integer limit) throws DAOException;
}
