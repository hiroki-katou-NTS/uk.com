/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.emplrole;

import lombok.Getter;

/**
 * @author danpv
 *
 */
@Getter
public class EmployeeRole {

	private String companyId;

	private String roleId;

	public EmployeeRole(String companyId, String roleId) {
		this.companyId = companyId;
		this.roleId = roleId;
	}

}
