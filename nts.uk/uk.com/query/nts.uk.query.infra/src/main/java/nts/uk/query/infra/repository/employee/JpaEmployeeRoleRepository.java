/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.query.infra.repository.employee;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.infra.entity.role.SacmtRole;
import nts.uk.query.model.employee.EmployeeReferenceRange;
import nts.uk.query.model.employee.EmployeeRoleImported;
import nts.uk.query.model.employee.EmployeeRoleRepository;

/**
 * The Class JpaEmployeeRoleRepository.
 */
@Stateless
public class JpaEmployeeRoleRepository extends JpaRepository implements EmployeeRoleRepository {

	/* (non-Javadoc)
	 * @see nts.uk.query.model.employee.EmployeeRoleRepository#findRoleById(java.lang.String)
	 */
	@Override
	public EmployeeRoleImported findRoleById(String roleId) {
		String query ="SELECT e FROM SacmtRole e WHERE e.roleId = :roleId ";
		return this.queryProxy().query(query, SacmtRole.class).setParameter("roleId", roleId).getList().stream()
				.map(role -> new EmployeeRoleImported(role.getRoleId(),
						EmployeeReferenceRange.valueOf(role.getReferenceRange())))
				.findFirst().get();
	}

}
