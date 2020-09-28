package nts.uk.ctx.sys.assist.dom.role;

import java.util.Optional;

public interface RoleImportAdapter {

	Optional<RoleImport> findByRoleId(String roleId);
	
}
