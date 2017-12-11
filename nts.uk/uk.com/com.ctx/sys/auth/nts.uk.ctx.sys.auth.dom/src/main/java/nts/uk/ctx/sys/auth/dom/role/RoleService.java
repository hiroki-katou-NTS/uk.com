package nts.uk.ctx.sys.auth.dom.role;

import java.util.List;

public interface RoleService {
	List<Role> getAllByType(RoleType roleType);
	void insertRole(Role role);
	void updateRole(Role role);
	void removeRole (String roleId);
}
