/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.emplrole;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
public interface EmployeeRoleRepoInterface {
	
	public List<EmployeeRole> getEmployeeRoles(CompanyId companyId);
	
	public EmployeeRole getEmployeeRole(CompanyId companyId, String roleId);

}
