/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.roleset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtRoleSetPK;

/**
 * Class JpaRoleSetRepository implement of RoleSetRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaRoleSetRepository extends JpaRepository implements RoleSetRepository {

	private static final String SELECT_All_ROLE_SET_BY_COMPANY_ID = "SELECT rs FROM SacmtRoleSet rs"
			+ " WHERE rs.roleSetPK.companyId = :companyId ";
	private static final String SELECT_All_ROLE_SET_BY_COMPANY_ID_AND_PERSON_ROLE = "SELECT rs FROM SacmtRoleSet rs"
			+ " WHERE rs.roleSetPK.companyId = :companyId AND rs.personInfRole = :personRoleId ";
	
	private RoleSet toDomain(SaumtRoleSet entity) {
	
		return new RoleSet(entity.roleSetPK.roleSetCd
				, entity.roleSetPK.companyId
				, entity.roleSetName
				, EnumAdaptor.valueOf(entity.approvalAuthority, ApprovalAuthority.class)
				, entity.officeHelperRole
				, entity.myNumberRole
				, entity.hRRole
				, entity.personInfRole
				, entity.employmentRole
				, entity.salaryRole
				);
	}

	private SaumtRoleSet toEntity(RoleSet domain) {
		SaumtRoleSetPK key = new SaumtRoleSetPK(domain.getRoleSetCd().v(), domain.getCompanyId());
		return new SaumtRoleSet(key
				, domain.getRoleSetName().v()
				, domain.getApprovalAuthority().value
				, domain.getOfficeHelperRoleId()
				, domain.getMyNumberRoleId()
				, domain.getHRRoleId()
				, domain.getPersonInfRoleId()
				, domain.getEmploymentRoleId()
				, domain.getSalaryRoleId()
				);

	}
	
	private SaumtRoleSet toEntiryForUpdate(RoleSet domain, SaumtRoleSet upEntity) {
		upEntity.buildEntity(upEntity.roleSetPK
				, domain.getRoleSetName().v()
				, domain.getApprovalAuthority().value
				, domain.getOfficeHelperRoleId()
				, domain.getMyNumberRoleId()
				, domain.getHRRoleId()
				, domain.getPersonInfRoleId()
				, domain.getEmploymentRoleId()
				, domain.getSalaryRoleId());
		return upEntity;
	}

	
	
	@Override
	public Optional<RoleSet> findByRoleSetCdAndCompanyId(String roleSetCd, String companyId) {
		SaumtRoleSetPK pk = new SaumtRoleSetPK(roleSetCd, companyId);
		return this.queryProxy().find(pk, SaumtRoleSet.class).map(c -> toDomain(c));
	}

	@Override
	public List<RoleSet> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_ROLE_SET_BY_COMPANY_ID, SaumtRoleSet.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public void insert(RoleSet domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(RoleSet domain) {
		Optional<SaumtRoleSet> upEntity = this.queryProxy().find(
				 new SaumtRoleSetPK(domain.getRoleSetCd().v(), domain.getCompanyId()),
				 SaumtRoleSet.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntiryForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void delete(String roleSetCd, String companyId) {
		SaumtRoleSetPK pk = new SaumtRoleSetPK(roleSetCd, companyId);
		this.commandProxy().remove(SaumtRoleSet.class, pk);
	}

	@Override
	public boolean isDuplicateRoleSetCd(String roleSetCd, String companyId) {
		SaumtRoleSetPK pk = new SaumtRoleSetPK(roleSetCd, companyId);
		return this.queryProxy().find(pk, SaumtRoleSet.class).isPresent();
		
	}
	
	public List<RoleSet> findByCompanyIdAndPersonRole(String companyId, String personRoleId) {
		List<RoleSet> result = new ArrayList<RoleSet>();
		List<SaumtRoleSet> entities = this.queryProxy()
				.query(SELECT_All_ROLE_SET_BY_COMPANY_ID_AND_PERSON_ROLE, SaumtRoleSet.class)
				.setParameter("companyId", companyId).setParameter("personRoleId", personRoleId).getList();
		if(entities !=null && !entities.isEmpty()){
			result = entities.stream().map(e ->toDomain(e)).collect(Collectors.toList());
		}
		return result;
	}
}
