package nts.uk.ctx.at.auth.dom.adapter;

import java.util.Optional;

public interface RoleAdaptor {
	
	public Optional<RoleImport> findByRoleId(String roleId);
	
}
