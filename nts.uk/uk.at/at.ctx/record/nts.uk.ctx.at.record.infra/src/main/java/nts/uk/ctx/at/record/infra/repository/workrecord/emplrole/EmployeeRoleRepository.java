/**
 * 
 */
package nts.uk.ctx.at.record.infra.repository.workrecord.emplrole;

import java.util.ArrayList;
import java.util.List;
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

	private final String GET_EMPL_ROLES = "SELECT e FROM EmpRole e WHERE e.key.cid = :companyId ORDER BY e.key.roleId";

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

	@Override
	public List<EmployeeRole> getEmployeeRoles(CompanyId companyId) {
		List<EmpRole> employeeRoles = this.queryProxy().query(GET_EMPL_ROLES, EmpRole.class)
				.setParameter("companyId", companyId.toString()).getList();
		List<EmployeeRole> results = new ArrayList<>();
		for (EmpRole ent : employeeRoles) {
			results.add(new EmployeeRole(companyId, ent.key.roleId, ent.roleName));
		}
		return results;
	}

}
