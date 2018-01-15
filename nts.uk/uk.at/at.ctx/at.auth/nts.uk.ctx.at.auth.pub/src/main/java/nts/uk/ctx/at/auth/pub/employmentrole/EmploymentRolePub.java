package nts.uk.ctx.at.auth.pub.employmentrole;

import java.util.List;

public interface EmploymentRolePub {

	List<EmploymentRolePubDto> getAllByCompanyId(String companyId);
	
}