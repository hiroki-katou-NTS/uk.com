/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.ac.roleset.webmenu.webmenulinking;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuImport;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetLinkWebMenuAdapter;
import nts.uk.ctx.sys.portal.pub.webmenu.webmenulinking.RoleSetLinkWebMenuPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WebMenuAdapterImpl.
 * @author HieuNV
 */
@Stateless
public class AuthRoleSetLinkWebMenuAdapterImpl implements RoleSetLinkWebMenuAdapter {
	
	/** The web menu pub. */
	@Inject
	private RoleSetLinkWebMenuPub roleSetAndWebMenuPub;

	@Override
	public List<RoleSetLinkWebMenuImport> findAllWebMenuByRoleSetCd(String roleSetCd) {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			return this.roleSetAndWebMenuPub.findAllWebMenuByRoleSetCd(companyId, roleSetCd).stream()
					.map(item -> new RoleSetLinkWebMenuImport(item.getCompanyId(), item.getWebMenuCd(), item.getRoleSetCd())
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	@Override
	public void addRoleSetAndWebMenu(RoleSetLinkWebMenuImport roleSetAndWebMenu) {
		this.roleSetAndWebMenuPub.addRoleSetAndWebMenu(
				roleSetAndWebMenu.getRoleSetCd()
				, roleSetAndWebMenu.getWebMenuCd()
				, roleSetAndWebMenu.getCompanyId());
	}

	@Override
	public void updateRoleSetAndWebMenu(RoleSetLinkWebMenuImport roleSetAndWebMenu) {
		this.roleSetAndWebMenuPub.updateRoleSetAndWebMenu(
				roleSetAndWebMenu.getRoleSetCd()
				, roleSetAndWebMenu.getWebMenuCd()
				, roleSetAndWebMenu.getCompanyId());
	}

	@Override
	public void deleteAllRoleSetAndWebMenu(String roleSetCd) {
		//Get company Id
		this.roleSetAndWebMenuPub.deleteRoleSetAndWebMenuByRoleSetCd(roleSetCd);
	}
	
}
