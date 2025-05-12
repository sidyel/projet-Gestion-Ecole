package bean;

import javax.faces.bean.ManagedBean;

import entitee.Role;
import entitee.RoleHome;

@ManagedBean
public class RoleBean {
	
	Role role=new Role();
	RoleHome roleHome=new RoleHome();
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	
	public void addRole()
	{
		roleHome.persist(role);
	}

}
