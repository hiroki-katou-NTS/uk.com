/**
 * 
 */
package nts.uk.ctx.at.record.dom.workrecord.emplrole;

import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
@Getter
public class EmployeeRole extends AggregateRoot {

	private CompanyId companyId;

	private String roleId;

	private String roleName;

	public EmployeeRole(CompanyId companyId, String roleId, String roleName) {
		this.companyId = companyId;
		this.roleId = roleId;
		this.roleName = roleName;
	}

}
