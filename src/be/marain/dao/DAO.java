package be.marain.dao;

import java.sql.Connection;
import java.util.List;

public abstract class DAO<T> {
	protected Connection connect = null;
	
	public DAO(Connection conn) {
		connect = conn;
	}
	
	public abstract boolean create(T obj);
	
	public abstract boolean update(T obj);
	
	public abstract boolean delete(T obj);
	
	public abstract T find(int id);
	
	public abstract List<T> findAll();
}