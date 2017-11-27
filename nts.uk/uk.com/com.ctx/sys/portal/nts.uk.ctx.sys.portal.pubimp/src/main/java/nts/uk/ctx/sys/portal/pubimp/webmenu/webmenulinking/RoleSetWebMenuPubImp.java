/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.pubimp.webmenu.webmenulinking;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetLinkWebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.RoleSetLinkWebMenuRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service.RoleSetAndWebMenuService;
import nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking.RoleSetLinkWebMenuPub;
import nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking.RoleSetLinkWebMenuExport;

/**
 * The Class WebmenuPubImp.
 */
@Stateless
public class RoleSetWebMenuPubImp implements RoleSetLinkWebMenuPub {

	/** The role set and web menu link repository. */
	@Inject
	private RoleSetLinkWebMenuRepository roleSetAndWebMenuRepository;
	
	@Inject RoleSetAndWebMenuService roleSetAndWebMenuService;

	@Override
	public List<RoleSetLinkWebMenuExport> findAllWebMenuByRoleSetCd(String companyId, String roleSetCd) {
		return roleSetAndWebMenuRepository.findByRoleSetCd(companyId, roleSetCd).stream()
				.map(item -> new RoleSetLinkWebMenuExport(
						item.getRoleSetCd().v()
						, item.getWebMenuCd().v()
						, item.getCompanyId())
						)
				.collect(Collectors.toList());
	}


	@Override
	public void addRoleSetAndWebMenu(String roleSetCd, String webMenuCd, String companyId) {
		RoleSetLinkWebMenu domain = new RoleSetLinkWebMenu(roleSetCd, webMenuCd, companyId);
		roleSetAndWebMenuService.createRoleSetWebMenuLink(domain);
	}

	@Override
	public void updateRoleSetAndWebMenu(String roleSetCd, String webMenuCd, String companyId) {
		RoleSetLinkWebMenu domain = new RoleSetLinkWebMenu(roleSetCd, webMenuCd, companyId);
		roleSetAndWebMenuService.updateRoleSetWebMenuLink(domain);
		
	}

	@Override
	public void deleteRoleSetAndWebMenuByRoleSetCd(String roleSetCd) {
		roleSetAndWebMenuService.deleteRoleSetWebMenuLinkByRoleCd(roleSetCd);
	}
	
	
}
