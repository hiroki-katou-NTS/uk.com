/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.emplrole;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.emplrole.EmployeeRole;
import nts.uk.ctx.at.record.dom.workrecord.emplrole.EmployeeRoleRepoInterface;
import nts.uk.ctx.at.record.infra.entity.workrecord.emplrole.EmpRole;
import nts.uk.ctx.at.record.infra.entity.workrecord.emplrole.EmpRolePk;
import nts.uk.ctx.at.shared.dom.common.CompanyId;

/**
 * @author danpv
 *
 */
@Stateless
public class EmployeeRoleRepository extends JpaRepository implements EmployeeRoleRepoInterface {

	@Override
	public EmployeeRole getEmployeeRole(CompanyId companyId, String roleId) {
		Optional<EmpRole> employeeRoleOp = this.queryProxy().find(new EmpRolePk(companyId.toString(), roleId),
				EmpRole.class);
		if (employeeRoleOp.isPresent()) {
			EmpRole entity = employeeRoleOp.get();
			return new EmployeeRole(companyId, roleId, entity.roleName);
		}
		return null;
	}

}
