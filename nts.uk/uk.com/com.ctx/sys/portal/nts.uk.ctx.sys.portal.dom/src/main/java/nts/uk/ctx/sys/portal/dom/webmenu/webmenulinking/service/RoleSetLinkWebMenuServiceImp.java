/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetLinkWebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetLinkWebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * Class JpaRoleSetAndWebMenuRepository implement of RoleSetAndWebMenuRepository
 * @author Hieu.NV
 *
 */
@Stateless
public class RoleSetLinkWebMenuServiceImp extends JpaRepository implements RoleSetLinkWebMenuService {

	@Inject
	private RoleSetLinkWebMenuRepository roleSetAndWebMenuRepository;
	
	@Override
	public void createRoleSetLinkWebMenu(RoleSetLinkWebMenu domain) {
		roleSetAndWebMenuRepository.insert(domain);
	}

	@Override
	public void updateRoleSetLinkWebMenu(RoleSetLinkWebMenu domain) {
		roleSetAndWebMenuRepository.update(domain);
	}

	@Override
	public void deleteRoleSetLinkWebMenuByRoleCd(String roleSetCd) {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)) {
			return;
		}
		roleSetAndWebMenuRepository.deleteAllByRoleCd(companyId, roleSetCd);
	}

	@Override
	public void createAllRoleSetLinkWebMenu(List<RoleSetLinkWebMenu> listRoleSetLinkWebMenu) {
		roleSetAndWebMenuRepository.insert(listRoleSetLinkWebMenu);
		
	}
}
