package nts.uk.ctx.sys.assist.dom.role;

import java.util.Optional;

import nts.uk.ctx.sys.assist.dom.datarestoration.LoginPersonInCharge;

public interface RoleImportAdapter {

	Optional<RoleImport> findByRoleId(String roleId);
	LoginPersonInCharge getInChargeInfo();
}
