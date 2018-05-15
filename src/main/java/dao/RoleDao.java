package dao;

import model.Role;

public interface RoleDao extends AbstractDao<Role>{

	public Role getByName(String name);
	
}
