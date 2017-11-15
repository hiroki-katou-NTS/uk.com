/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.infra.repository.webmenu.webmenulinking;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenuRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.webmenulinking.SptmtRoleSetWebMenu;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.webmenulinking.SptmtRoleSetWebMenuPK;

/**
 * Class JpaRoleSetAndWebMenuRepository implement of RoleSetAndWebMenuRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class JpaRoleSetAndWebMenuRepository extends JpaRepository implements RoleSetAndWebMenuRepository {

	private static final String SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID = "SELECT rw FROM SacmtRoleSetWebMenu rw"
			+ " WHERE rw.roleSetWebMenuPK.companyId = :companyId ";

	private static final String SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID_AND_ROLE_SET_CD = "SELECT rw FROM SacmtRoleSetWebMenu rw"
			+ " WHERE rw.roleSetWebMenuPK.companyId = :companyId"
			+ " rw.roleSetWebMenuPK.roleSetCd = :roleSetCd ";
	
	private nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetAndWebMenu toDomain(SptmtRoleSetWebMenu entity) {
		return new RoleSetAndWebMenu(entity.roleSetWebMenuPK.roleSetCd
				, entity.roleSetWebMenuPK.webMenuCd
				, entity.roleSetWebMenuPK.companyId
				);
	}

	private SptmtRoleSetWebMenu toEntity(RoleSetAndWebMenu domain) {
		SptmtRoleSetWebMenuPK key = new SptmtRoleSetWebMenuPK(domain.getCompanyId()
				, domain.getWebMenuCd().v()
				, domain.getRoleSetCd().v());
		return new SptmtRoleSetWebMenu(key);

	}
	
	private SptmtRoleSetWebMenu toEntityForUpdate(RoleSetAndWebMenu domain, SptmtRoleSetWebMenu upEntity) {
		upEntity.buildEntity(new SptmtRoleSetWebMenuPK(domain.getCompanyId()
				, domain.getWebMenuCd().v()
				, domain.getRoleSetCd().v()));
		return upEntity;
	}

	@Override
	public List<RoleSetAndWebMenu> findByCompanyId(String companyId) {
		return this.queryProxy().query(SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID, SptmtRoleSetWebMenu.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<RoleSetAndWebMenu> findByRoleSetCd(String companyId, String roleSetCd) {
		return this.queryProxy().query(SELECT_All_ROLE_SET_AND_WEB_MENU_BY_COMPANY_ID_AND_ROLE_SET_CD, SptmtRoleSetWebMenu.class)
				.setParameter("companyId", companyId)
				.setParameter("roleSetCd", roleSetCd)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<RoleSetAndWebMenu> findByKey(String companyId, String webMenuCd, String roleSetCd) {
		SptmtRoleSetWebMenuPK pk = new SptmtRoleSetWebMenuPK(companyId, webMenuCd, roleSetCd);
		return this.queryProxy().find(pk, SptmtRoleSetWebMenu.class).map(c -> toDomain(c));
	}
	
	@Override
	public void insert(RoleSetAndWebMenu domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(RoleSetAndWebMenu domain) {
		 Optional<SptmtRoleSetWebMenu> upEntity = this.queryProxy().find(
				 new SptmtRoleSetWebMenuPK(domain.getCompanyId()
					, domain.getWebMenuCd().v()
					, domain.getRoleSetCd().v()),
				 SptmtRoleSetWebMenu.class);
		if (upEntity.isPresent()) {
			this.commandProxy().update(toEntityForUpdate(domain, upEntity.get()));
		}
	}

	@Override
	public void delete(RoleSetAndWebMenu domain) {
		SptmtRoleSetWebMenuPK pk = new SptmtRoleSetWebMenuPK(
				domain.getCompanyId()
				, domain.getWebMenuCd().v()
				, domain.getRoleSetCd().v());
		this.commandProxy().remove(SptmtRoleSetWebMenu.class, pk);
	}


}
