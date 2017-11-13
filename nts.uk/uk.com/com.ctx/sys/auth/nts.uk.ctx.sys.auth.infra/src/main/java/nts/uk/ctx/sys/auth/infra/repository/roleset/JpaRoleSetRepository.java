/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.roleset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.roleset.ApprovalAuthority;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetName;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetRepository;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetPK;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetWebMenu;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetWebMenuPK;

/**
 * Class JpaRoleSetRepository implement of RoleSetRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaRoleSetRepository extends JpaRepository implements RoleSetRepository {

	private static final String SELECT_All_ROLE_SET_BY_COMPANY_ID = "SELECT rs FROM SacmtRoleSet rs"
			+ " WHERE rs.roleSetPK.companyId = :companyId ";
			
	private RoleSet toDomain(SacmtRoleSet entity) {
		return new RoleSet(new RoleSetCode(entity.roleSetPK.roleSetCd)
				, entity.roleSetPK.companyId
				, new RoleSetName(entity.roleSetName)
				, EnumAdaptor.valueOf(entity.approvalAuthority, ApprovalAuthority.class)
				, entity.officeHelperRole
				, entity.myNumberRole
				, entity.hRRole
				, entity.personInfRole
				, entity.employmentRole
				, entity.salaryRole
				);
	}

	private SacmtRoleSet toEntity(RoleSet domain) {
		SacmtRoleSetPK key = new SacmtRoleSetPK(domain.getRoleSetCd().v(), domain.getCompanyId());
		return new SacmtRoleSet(key
				, domain.getRoleSetName().v()
				, domain.getApprovalAuthority().value
				, domain.getOfficeHelperRole()
				, domain.getMyNumberRole()
				, domain.getHRRole()
				, domain.getPersonInfRole()
				, domain.getEmploymentRole()
				, domain.getSalaryRole()
				);

	}
	
	private SacmtRoleSet toEntiryForUpdate(RoleSet domain, SacmtRoleSet upEntity) {
		upEntity.BuildEntity(upEntity.roleSetPK
				, domain.getRoleSetName().v()
				, domain.getApprovalAuthority().value
				, domain.getOfficeHelperRole()
				, domain.getMyNumberRole()
				, domain.getHRRole()
				, domain.getPersonInfRole()
				, domain.getEmploymentRole()
				, domain.getSalaryRole());
		return upEntity;
	}

	
	@Override
	public Optional<RoleSet> findByRoleSetCdAndCompanyId(String roleSetCd, String companyId) {
		SacmtRoleSetPK pk = new SacmtRoleSetPK(roleSetCd, companyId);
		return this.queryProxy().find(pk, SacmtRoleSet.class).map(c -> toDomain(c));
	}

	@Override
	public List<RoleSet> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_ROLE_SET_BY_COMPANY_ID, SacmtRoleSet.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public void Insert(RoleSet domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void Update(RoleSet domain) {
		Optional<SacmtRoleSet> upEntity = this.queryProxy().find(
				 new SacmtRoleSetPK(domain.getRoleSetCd().v(), domain.getCompanyId()),
				 SacmtRoleSet.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntiryForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void Delete(String roleSetCd, String companyId) {
		SacmtRoleSetPK pk = new SacmtRoleSetPK(roleSetCd, companyId);
		this.commandProxy().remove(SacmtRoleSet.class, pk);
	}

}
