package nts.uk.ctx.sys.portal.app.find.standardmenu;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class StandardMenuFinder.
 */
@Stateless
public class StandardMenuFinder {

	@Inject
	private StandardMenuRepository standardMenuRepository;

	/**
	 * find all StandardMenu by companyID
	 * 
	 * @param conpanyID
	 * @return List
	 */
	public List<StandardMenuDto> findAll() {
		String companyID = AppContexts.user().companyId();
		return this.standardMenuRepository.findAll(companyID).stream().map(item -> StandardMenuDto.fromDomain(item))
				.collect(Collectors.toList());
	}

	/**
	 * find all StandardMenu by companyID and AfterLoginDisplayIndicator
	 * 
	 * 
	 * @param conpanyID
	 * @return List
	 */
	public List<StandardMenuDto> findByAfterLoginDisplay(int afterLoginDisplay) {
		String companyID = AppContexts.user().companyId();
		return this.standardMenuRepository.findByAfterLoginDisplay(companyID, afterLoginDisplay).stream()
				.map(item -> StandardMenuDto.fromDomain(item)).collect(Collectors.toList());
	}

	/**
	 * find by companyId, afterLoginDisplay = 0, system = common,
	 * menuClassification = toppage
	 * 
	 * @return List StandardMenuDto
	 */
	public List<StandardMenuDto> findByAfterLgDisSysMenuCls() {
		String companyId = AppContexts.user().companyId();
		return this.standardMenuRepository
				.findByAfterLgDisSysMenuCls(companyId, 0, System.COMMON.value, MenuClassification.TopPage.value)
				.stream().map(x -> StandardMenuDto.fromDomain(x)).collect(Collectors.toList());
	}
}
