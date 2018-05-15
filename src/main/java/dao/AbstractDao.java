package dao;

import java.sql.SQLException;
import java.util.List;

import model.Model;



public interface AbstractDao<T extends Model> {

	public List<T> getAll() throws SQLException;

	public T getById(Long id) throws SQLException;

	public void add(T model) throws SQLException;

	public void update(T model) throws SQLException;

	public void remove(T model) throws SQLException;
}
