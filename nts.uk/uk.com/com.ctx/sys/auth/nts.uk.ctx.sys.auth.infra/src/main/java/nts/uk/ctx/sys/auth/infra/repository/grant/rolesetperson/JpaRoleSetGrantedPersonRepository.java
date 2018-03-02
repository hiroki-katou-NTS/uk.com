package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetperson;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

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
public class JpaRoleSetGrantedPersonRepository extends JpaRepository implements RoleSetGrantedPersonRepository {

	private final String GET_ALL_BY_CID_AND_ROLESET_CODE = "select r FROM  SacmtRoleSetGrantedPerson r Where r.companyId = :companyId And r.roleSetCd = :roleSetCd";

	@Override
	public boolean checkRoleSetCdExist(String roleSetCd, String companyId) {
		return !this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getList().isEmpty();
	}

	@Override
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId) {
		return this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getList(r -> r.toDomain());
	}

	@Override
	public void insert(RoleSetGrantedPerson domain) {
		this.commandProxy().insert(SacmtRoleSetGrantedPerson.toEntity(domain));
	}

	@Override
	public void update(RoleSetGrantedPerson domain) {
		this.commandProxy().update(SacmtRoleSetGrantedPerson.toEntity(domain));
	}

	@Override
	public void delete(String employeeId) {
		this.commandProxy().remove(SacmtRoleSetGrantedPerson.class, employeeId);
	}

	@Override
	public Optional<RoleSetGrantedPerson> getByEmployeeId(String employeeId) {
		return this.queryProxy().find(employeeId, SacmtRoleSetGrantedPerson.class).map(r -> r.toDomain());
	}

}
