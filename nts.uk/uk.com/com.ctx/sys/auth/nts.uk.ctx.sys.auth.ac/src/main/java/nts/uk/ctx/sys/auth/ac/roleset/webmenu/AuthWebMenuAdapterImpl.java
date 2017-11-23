/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.ac.roleset.webmenu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.uk.ctx.sys.auth.dom.roleset.webmenu.WebMenu;
import nts.uk.ctx.sys.auth.dom.roleset.webmenu.WebMenuAdapter;
import nts.uk.ctx.sys.portal.pub.webmenu.WebMenuPub;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WebMenuAdapterImpl.
 * @author HieuNV
 */
@Stateless
public class AuthWebMenuAdapterImpl implements WebMenuAdapter {
	
	/** The web menu pub. */
	@Inject
	private WebMenuPub webMenuPub;

	@Override
	public List<WebMenu> findByCompanyId() {
		//Get company Id
		String companyId = AppContexts.user().companyId();
		if (!StringUtils.isNoneEmpty(companyId)) {
			return new ArrayList<>();
		}
			
		return this.webMenuPub.findByCompanyId(companyId).stream().map(item ->
			new WebMenu(item.getWebMenuCd(), item.getWebMenuName(), item.getCompanyId(), item.isDefaultMenu())
			).collect(Collectors.toList());
	}
}
