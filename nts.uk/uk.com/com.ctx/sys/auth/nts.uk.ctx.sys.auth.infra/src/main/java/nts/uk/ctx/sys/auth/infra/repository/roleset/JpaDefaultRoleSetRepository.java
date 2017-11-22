/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.roleset;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSet;
import nts.uk.ctx.sys.auth.dom.roleset.DefaultRoleSetRepository;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtDefaultRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SaumtDefaultRoleSetPK;

/**
 * Class JpaDefaultRoleSetRepository implement of DefaultRoleSetRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaDefaultRoleSetRepository extends JpaRepository implements DefaultRoleSetRepository {
	
	private static final String SELECT_DEFAULT_ROLE_SET_BY_COMPANY_ID_ROLE_SET_CD = "SELECT drs FROM SacmtDefaultRoleSet drs"
			+ " WHERE drs.defaultRoleSetPK.companyId = :companyId "
			+ " 	  drs.defaultRoleSetPK.roleSetCd = :roleSetCd ";
	
	private DefaultRoleSet toDomain(SaumtDefaultRoleSet entity) {
		return new DefaultRoleSet(entity.defaultRoleSetPK.companyId, entity.roleSetCd);
	}

	private SaumtDefaultRoleSet toEntity(DefaultRoleSet domain) {
		return new SaumtDefaultRoleSet(new SaumtDefaultRoleSetPK(domain.getCompanyId()), domain.getRoleSetCd().v());
	}

	private SaumtDefaultRoleSet toEntityForUpdate(DefaultRoleSet domain, SaumtDefaultRoleSet upEntity) {
		upEntity.buildEntity(upEntity.defaultRoleSetPK, domain.getRoleSetCd().v());
		return upEntity;
	}	
	
	@Override
	public Optional<DefaultRoleSet> find(String companyId, String roleSetCd) {
		return this.queryProxy().query(SELECT_DEFAULT_ROLE_SET_BY_COMPANY_ID_ROLE_SET_CD, SaumtDefaultRoleSet.class)
				.setParameter("companyId", companyId)
				.setParameter("roleSetCd", roleSetCd)
				.getSingle(c -> toDomain(c));
	}
	
	@Override
	public Optional<DefaultRoleSet> findByCompanyId(String companyId) {
		SaumtDefaultRoleSetPK pk = new SaumtDefaultRoleSetPK(companyId);
		return this.queryProxy().find(pk, SaumtDefaultRoleSet.class).map(c -> toDomain(c));
	}

	@Override
	public void insert(DefaultRoleSet domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(DefaultRoleSet domain) {
		 Optional<SaumtDefaultRoleSet> upEntity = this.queryProxy().find(new SaumtDefaultRoleSetPK(domain.getCompanyId()),
				 SaumtDefaultRoleSet.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void delete(String companyId) {
		this.commandProxy().remove(SaumtDefaultRoleSet.class, new SaumtDefaultRoleSetPK(companyId));
	}

	@Override
	public void addOrUpdate(DefaultRoleSet domain) {
		Optional<SaumtDefaultRoleSet> upEntity = this.queryProxy().find(new SaumtDefaultRoleSetPK(domain.getCompanyId()),
				 SaumtDefaultRoleSet.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
		} else {
			this.commandProxy().insert(toEntity(domain));
		}
	}
}
