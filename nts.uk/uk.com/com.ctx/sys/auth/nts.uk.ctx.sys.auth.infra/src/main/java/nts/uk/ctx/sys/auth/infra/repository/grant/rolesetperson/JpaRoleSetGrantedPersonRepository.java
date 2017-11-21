package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetperson;

import java.util.List;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPerson;
import nts.uk.ctx.sys.auth.dom.grant.rolesetperson.RoleSetGrantedPersonRepository;
import nts.uk.ctx.sys.auth.infra.entity.grant.rolesetperson.SaumtRoleSetGrantedPerson;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedPersonRepository extends JpaRepository implements RoleSetGrantedPersonRepository {

	//private final String CHECK_ROLESET_CODE_EXIST = "select r FROM  SaumtRoleSetGrantedPerson r Where r.companyId = :companyId and r.roleSetCd = :roleSetCd";

	private final String GET_ALL_BY_CID_AND_ROLESET_CODE = "select r FROM  SaumtRoleSetGrantedPerson r Where r.selectionItemId = :selectionItemId";

	private RoleSetGrantedPerson toDomain(SaumtRoleSetGrantedPerson entity) {
		return new RoleSetGrantedPerson(entity.roleSetCd, entity.companyId, entity.startDate, entity.endDate,
				entity.employeeId);
	}

	@Override
	public boolean checkRoleSetCdExist(String roleSetCd, String companyId) {
		return this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SaumtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getSingle().isPresent();
	}

	@Override
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId) {
		return this.queryProxy().query(GET_ALL_BY_CID_AND_ROLESET_CODE, SaumtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId).setParameter("roleSetCd", roleSetCd).getList(r -> toDomain(r));
	}

}
