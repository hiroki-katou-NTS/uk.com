package nts.uk.ctx.at.auth.pub.employmentrole;

import java.util.List;
import java.util.Optional;


public interface EmploymentRolePub {

	List<EmploymentRolePubDto> getAllByCompanyId(String companyId);
	
	List<EmploymentRoleExport> getListEmploymentRole(String companyId);
	
	Optional<EmploymentRoleExport> getEmploymentRoleById(String companyId,String roleId);
	
	void addEmploymentRole(EmploymentRoleExport employmentRole );
	
	void updateEmploymentRole(EmploymentRoleExport employmentRole );
	
	void deleteEmploymentRole(String companyId,String roleId );
}
