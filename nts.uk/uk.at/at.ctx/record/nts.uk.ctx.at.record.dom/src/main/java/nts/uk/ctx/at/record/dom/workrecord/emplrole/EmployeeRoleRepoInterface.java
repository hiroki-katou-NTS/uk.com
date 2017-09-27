/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.emplrole;

import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
public interface EmployeeRoleRepoInterface {
	
	public EmployeeRole getEmployeeRole(CompanyId companyId, String roleId);

}
