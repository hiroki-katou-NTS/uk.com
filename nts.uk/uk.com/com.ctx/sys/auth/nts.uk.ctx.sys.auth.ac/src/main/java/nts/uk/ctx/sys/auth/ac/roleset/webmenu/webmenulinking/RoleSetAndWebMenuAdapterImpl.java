/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.ac.roleset.webmenu.webmenulinking;

import java.util.ArrayList;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.webmenulinking.RoleSetAndWebMenuAdapter;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WebMenuAdapterImpl.
 * @author HieuNV
 */
@Stateless
public class RoleSetAndWebMenuAdapterImpl implements RoleSetAndWebMenuAdapter {
	
	/** The web menu pub. */
	@Inject
	private RoleSetAndWebMenuPub roleSetAndWebMenuPub;

	@Override
	public List<RoleSetAndWebMenu> findAllWebMenuByRoleSetCd(String roleSetCd) {
		//Get company Id
				String companyId = AppContexts.user().companyId();
				if (!StringUtils.isNoneEmpty(companyId)) {
					
					//return this.roleSetAndWebMenuPub.findByCompanyId(companyId).stream().map(item -> WebMenu.build(item))
					//	.collect(Collectors.toList());
				}
				return new ArrayList<>();
	}

	@Override
	public void addListOfRoleSetAndWebMenu(List<RoleSetAndWebMenu> roleSetAndWebMenus) {
		// TODO Auto-generated method stub
		//RoleSetWebMenuPubDto
	}

	@Override
	public void updateListOfRoleSetAndWebMenu(List<RoleSetAndWebMenu> roleSetAndWebMenus) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteListOfRoleSetAndWebMenu(String roleSetCd, String companyId) {
		// TODO Auto-generated method stub
		
	}
	
}
