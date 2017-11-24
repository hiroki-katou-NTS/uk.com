/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.webmenu.webmenulinking.service;

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
public class RoleSetAndWebMenuServiceImp extends JpaRepository implements RoleSetAndWebMenuService {

	@Inject
	private RoleSetLinkWebMenuRepository roleSetAndWebMenuRepository;
	
	@Override
	public void createRoleSetWebMenuLink(RoleSetLinkWebMenu domain) {
		roleSetAndWebMenuRepository.insert(domain);
	}

	@Override
	public void updateRoleSetWebMenuLink(RoleSetLinkWebMenu domain) {
		roleSetAndWebMenuRepository.update(domain);
	}

	@Override
	public void deleteRoleSetWebMenuLinkByRoleCd(String roleSetCd) {
		String companyId = AppContexts.user().companyId();
		if (StringUtils.isNoneEmpty(companyId)) {
			return;
		}
		roleSetAndWebMenuRepository.deleteAllByRoleCd(companyId, roleSetCd);
	}
}
