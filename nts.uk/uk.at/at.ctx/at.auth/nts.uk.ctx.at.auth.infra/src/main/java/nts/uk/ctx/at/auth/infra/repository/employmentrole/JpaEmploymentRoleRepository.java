package nts.uk.ctx.at.auth.infra.repository.employmentrole;

import java.util.List;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRole;
import nts.uk.ctx.at.auth.dom.employmentrole.EmploymentRoleRepository;
import nts.uk.ctx.at.auth.infra.entity.employmentrole.KacmtEmploymentRole;

@Stateless
public class JpaEmploymentRoleRepository extends JpaRepository implements EmploymentRoleRepository {

	private static final String GET_ALL_BY_COMPANY_ID = "SELECT e"
			+ " FROM KacmtEmploymentRole e"
			+ " WHERE e.kacmtEmploymentRolePK.companyID = :companyID";

	@Override
	public List<EmploymentRole> getAllByCompanyId(String companyId) {
		return this.queryProxy().query(GET_ALL_BY_COMPANY_ID, KacmtEmploymentRole.class)
		.setParameter("companyID", companyId)
		.getList(c -> c.toDomain());
	}

}
