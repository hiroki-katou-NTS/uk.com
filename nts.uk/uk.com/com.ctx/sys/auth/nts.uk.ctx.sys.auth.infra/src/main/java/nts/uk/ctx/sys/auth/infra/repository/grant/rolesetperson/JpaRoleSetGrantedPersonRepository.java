package nts.uk.ctx.sys.auth.infra.repository.grant.rolesetperson;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
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

	private static final String GET_ALL_BY_CID_AND_ROLESET_CODE = "select r FROM  SacmtRoleSetGrantedPerson r Where r.companyId = :companyId And r.roleSetCd = :roleSetCd";

	private static final String SELECT_BY_ID_DATE = "SELECT c FROM SacmtRoleSetGrantedPerson c"
			+ " WHERE c.companyId = :companyId"
			+ " AND c.employeeId = :employeeId"
			+ " AND c.startDate <= :date AND c.endDate >= :date";
	
	private static final String FIND_BY_DETAIL = "SELECT c FROM SacmtRoleSetGrantedPerson c"
			+ " WHERE c.companyId = :companyId"
			+ " AND c.employeeId = :employeeId"
			+ " AND c.roleSetCd IN :roleCDLst"
			+ " AND c.startDate <= :date AND c.endDate >= :date";
	
	private static final String SELECT_BY_DATE = "SELECT c FROM SacmtRoleSetGrantedPerson c"
			+ " WHERE c.employeeId = :employeeId"
			+ " AND c.startDate <= :date AND c.endDate >= :date";
	
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
	
	@Override
											
	public Optional<RoleSetGrantedPerson> findByIDAndDate(String companyId, String employeeId, GeneralDate date) {
		
		return this.queryProxy().query(SELECT_BY_ID_DATE ,SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyId)
				.setParameter("employeeId", employeeId)
				.setParameter("date", date).getSingle( c  -> c.toDomain());
	}

	@Override
	public Optional<RoleSetGrantedPerson> findByDetail(String companyID, String employeeID, List<String> roleSetCDLst,
			GeneralDate date) {
		return this.queryProxy().query(FIND_BY_DETAIL ,SacmtRoleSetGrantedPerson.class)
				.setParameter("companyId", companyID)
				.setParameter("employeeId", employeeID)
				.setParameter("roleCDLst", roleSetCDLst)
				.setParameter("date", date).getSingle( c  -> c.toDomain());
	}

	@Override
	public Optional<RoleSetGrantedPerson> getByEmployeeDate(String employeeId, GeneralDate baseDate) {
		return this.queryProxy().query(SELECT_BY_DATE ,SacmtRoleSetGrantedPerson.class)
		.setParameter("employeeId", employeeId)
		.setParameter("date", baseDate).getSingle( c  -> c.toDomain());
	}

}
