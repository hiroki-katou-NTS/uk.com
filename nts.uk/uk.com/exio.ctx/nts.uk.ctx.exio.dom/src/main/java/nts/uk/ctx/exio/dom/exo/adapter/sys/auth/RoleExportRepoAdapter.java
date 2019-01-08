package nts.uk.ctx.exio.dom.exo.adapter.sys.auth;

import java.util.Optional;

public interface RoleExportRepoAdapter {
	Optional<RoleImport> findByRoleId(String roleId);
}
