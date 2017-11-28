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
						item.getCompanyId()
						, item.getRoleSetCd().v()
						, item.getWebMenuCd().v()
						)
					)
				.collect(Collectors.toList());
	}


	@Override
	public void addRoleSetLinkWebMenu(String companyId, String roleSetCd, String webMenuCd) {
		RoleSetLinkWebMenu domain = new RoleSetLinkWebMenu(companyId, roleSetCd, webMenuCd);
		roleSetAndWebMenuService.createRoleSetLinkWebMenu(domain);
	}

	@Override
	public void updateRoleSetLinkWebMenu(String companyId, String roleSetCd, String webMenuCd) {
		RoleSetLinkWebMenu domain = new RoleSetLinkWebMenu(companyId, roleSetCd, webMenuCd);
		roleSetAndWebMenuService.updateRoleSetLinkWebMenu(domain);
		
	}

	@Override
	public void deleteRoleSetLinkWebMenuByRoleSetCd(String roleSetCd) {
		roleSetAndWebMenuService.deleteRoleSetLinkWebMenuByRoleCd(roleSetCd);
	}


	@Override
	public void addAllRoleSetLinkWebMenu(List<RoleSetLinkWebMenuExport> listRoleSetLinkWebMenuExport) {
		List<RoleSetLinkWebMenu> listRoleSetLinkWebMenu = listRoleSetLinkWebMenuExport.stream()
				.map(item -> new RoleSetLinkWebMenu(item.getCompanyId(), item.getRoleSetCd(), 
						item.getWebMenuCd())).collect(Collectors.toList());
		
		roleSetAndWebMenuService.createAllRoleSetLinkWebMenu(listRoleSetLinkWebMenu);
		
	}
	
	
}
