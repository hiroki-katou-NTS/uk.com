package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetperson;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetperson.SacmtRoleSetGrantedPerson;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedPersonRepository extends JpaRepository implements RoleSetGrantedPersonRepository {

	// private final String CHECK_ROLESET_CODE_EXIST = "select r FROM
	// SaumtRoleSetGrantedPerson r Where r.companyId = :companyId and
	// r.roleSetCd = :roleSetCd";

	private final String GET_ALL_BY_CID_AND_ROLESET_CODE = "select r FROM  SacmtRoleSetGrantedPerson r Where r.companyId = :companyId And r.roleSetCd = :roleSetCd";

	@Override
	public boolean checkRoleSetCdExist(String roleSetCd, String companyId) {
		return this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getSingle().isPresent();
	}

	@Override
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId) {
		return this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getList(r -> SacmtRoleSetGrantedPerson.toDomain(r));
	}

	@Override
	public void insert(RoleSetGrantedPerson domain) {
		this.commandProxy().insert(SacmtRoleSetGrantedPerson.toEntity(domain));
	}

	@Override
	public void update(RoleSetGrantedPerson domain) {
		SacmtRoleSetGrantedPerson entity = this.queryProxy()
				.find(domain.getEmployeeID(), SacmtRoleSetGrantedPerson.class).get();
		entity.roleSetCd = domain.getRoleSetCd().v();
		entity.startDate = domain.getValidPeriod().start();
		entity.endDate = domain.getValidPeriod().end();
		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String employeeId) {
		this.commandProxy().remove(SacmtRoleSetGrantedPerson.class, employeeId);
	}

	@Override
	public Optional<RoleSetGrantedPerson> getByEmployeeId(String employeeId) {
		return this.queryProxy().find(employeeId, SacmtRoleSetGrantedPerson.class).map(r -> SacmtRoleSetGrantedPerson.toDomain(r));
	}

}
