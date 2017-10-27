package nts.uk.ctx.at.auth.dom.employmentrole;

import java.util.List;

public interface EmploymentRoleRepository {
	
	List<EmploymentRole> getAllByCompanyId(String companyId);

}
