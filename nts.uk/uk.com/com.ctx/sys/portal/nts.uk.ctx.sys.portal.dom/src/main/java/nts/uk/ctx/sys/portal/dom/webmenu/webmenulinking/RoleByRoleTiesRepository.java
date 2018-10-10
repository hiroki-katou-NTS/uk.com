package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking;

import java.util.List;
import java.util.Optional;

public interface RoleByRoleTiesRepository {

	Optional<RoleByRoleTies> getRoleByRoleTiesById(String roleId);

	void insertRoleByRoleTies(RoleByRoleTies roleByRoleTies);

	void updateRoleByRoleTies(RoleByRoleTies roleByRoleTies);

	void deleteRoleByRoleTies(String roleId);

	void insertAll(List<RoleByRoleTies> roleTies);

}

