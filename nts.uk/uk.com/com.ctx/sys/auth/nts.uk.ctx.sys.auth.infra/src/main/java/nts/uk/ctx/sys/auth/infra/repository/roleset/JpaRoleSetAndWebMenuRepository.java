/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.infra.repository.roleset;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetAndWebMenuRepository;
import nts.uk.ctx.sys.auth.dom.roleset.RoleSetCode;
import nts.uk.ctx.sys.auth.dom.roleset.WebMenuCode;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtDefaultRoleSetPK;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSet;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetPK;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetWebMenu;
import nts.uk.ctx.sys.auth.infra.entity.roleset.SacmtRoleSetWebMenuPK;

/**
 * Class JpaRoleSetAndWebMenuRepository implement of RoleSetAndWebMenuRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaRoleSetAndWebMenuRepository extends JpaRepository implements RoleSetAndWebMenuRepository {

	private static final String SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID = "SELECT rw FROM SacmtRoleSetWebMenu rw"
			+ " WHERE rw.roleSetWebMenuPK.companyId = :companyId ";
			
	private RoleSetAndWebMenu toDomain(SacmtRoleSetWebMenu entity) {
		return new RoleSetAndWebMenu(entity.roleSetWebMenuPK.companyId
				, new WebMenuCode(entity.roleSetWebMenuPK.webMenuCd)
				, new RoleSetCode(entity.roleSetWebMenuPK.roleSetCd)
				);
	}

	private SacmtRoleSetWebMenu toEntity(RoleSetAndWebMenu domain) {
		SacmtRoleSetWebMenuPK key = new SacmtRoleSetWebMenuPK(domain.getCompanyId()
				, domain.getWebMenuCd().v()
				, domain.getRoleSetCd().v());
		return new SacmtRoleSetWebMenu(key);

	}
	
	private SacmtRoleSetWebMenu toEntityForUpdate(RoleSetAndWebMenu domain, SacmtRoleSetWebMenu upEntity) {
		upEntity.BuildEntity(new SacmtRoleSetWebMenuPK(domain.getCompanyId()
				, domain.getWebMenuCd().v()
				, domain.getRoleSetCd().v()));
		return upEntity;
	}

	@Override
	public List<RoleSetAndWebMenu> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID, SacmtRoleSetWebMenu.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<RoleSetAndWebMenu> findByKey(String companyId, String webMenuCd, String roleSetCd) {
		SacmtRoleSetWebMenuPK pk = new SacmtRoleSetWebMenuPK(companyId, webMenuCd, roleSetCd);
		return this.queryProxy().find(pk, SacmtRoleSetWebMenu.class).map(c -> toDomain(c));
	}
	
	@Override
	public void Insert(RoleSetAndWebMenu domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void Update(RoleSetAndWebMenu domain) {
		 Optional<SacmtRoleSetWebMenu> upEntity = this.queryProxy().find(
				 new SacmtRoleSetWebMenuPK(domain.getCompanyId()
					, domain.getWebMenuCd().v()
					, domain.getRoleSetCd().v()),
				 SacmtRoleSetWebMenu.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void Delete(String companyId, String webMenuCd, String roleSetCd) {
		SacmtRoleSetWebMenuPK pk = new SacmtRoleSetWebMenuPK(companyId, webMenuCd, roleSetCd);
		this.commandProxy().remove(SacmtRoleSet.class, pk);
	}
}
