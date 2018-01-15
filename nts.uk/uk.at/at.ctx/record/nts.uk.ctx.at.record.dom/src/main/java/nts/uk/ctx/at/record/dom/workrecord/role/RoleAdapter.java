package nts.uk.ctx.at.record.dom.workrecord.role;

import java.util.List;

public interface RoleAdapter {
	
	List<Role> getRolesById(String companyId, List<String> roleIds);

}
