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
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSetPK;

/**
 * Class JpaDefaultRoleSetRepository implement of DefaultRoleSetRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaDefaultRoleSetRepository extends JpaRepository implements DefaultRoleSetRepository {
		
	private DefaultRoleSet toDomain(SacmtDefaultRoleSet entity) {
		return new DefaultRoleSet(entity.defaultRoleSetPK.companyId, new RoleSetCode(entity.roleSetCd));
	}

	private SacmtDefaultRoleSet toEntity(DefaultRoleSet domain) {
		return new SacmtDefaultRoleSet(new SacmtDefaultRoleSetPK(domain.getCompanyId()), domain.getRoleSetCd().v());
	}

	private SacmtDefaultRoleSet toEntityForUpdate(DefaultRoleSet domain, SacmtDefaultRoleSet upEntity) {
		upEntity.BuildEntity(upEntity.defaultRoleSetPK, domain.getRoleSetCd().v());
		return upEntity;
	}	
	
	@Override
	public Optional<DefaultRoleSet> findByCompanyId(String companyId) {
		SacmtDefaultRoleSetPK pk = new SacmtDefaultRoleSetPK(companyId);
		return this.queryProxy().find(pk, SacmtDefaultRoleSet.class).map(c -> toDomain(c));
	}

	@Override
	public void Insert(DefaultRoleSet domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void Update(DefaultRoleSet domain) {
		 Optional<SacmtDefaultRoleSet> upEntity = this.queryProxy().find(new SacmtDefaultRoleSetPK(domain.getCompanyId()),
				 SacmtDefaultRoleSet.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void Delete(String companyId) {
		this.commandProxy().remove(SacmtDefaultRoleSet.class, new SacmtDefaultRoleSetPK(companyId));
	}

}
