/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.dom.toppage.service.internal;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.enums.WebMenuSetting;
import nts.uk.ctx.sys.portal.dom.layout.service.LayoutService;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuCode;
import nts.uk.ctx.sys.portal.dom.standardmenu.MenuDisplayName;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService;

/**
 * The Class TopPageServiceImpl.
 */
@Stateless
public class TopPageServiceImpl implements TopPageService {

	/** The top page repository. */
	@Inject
	private TopPageRepository topPageRepository;
	
	@Inject
	private LayoutService layoutService;
	

	@Inject
	private StandardMenuRepository standardMenuRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService#copyTopPage(nts.
	 * uk.ctx.sys.portal.dom.toppage.TopPage, java.lang.String)
	 */
	@Override
	public void copyTopPage(TopPage topPage, String companyId,boolean isCheckOverWrite,String copyCode) {
		Optional<TopPage> findTopPage = topPageRepository.findByCode(companyId, topPage.getTopPageCode().v());
		TopPage findCopyTopPage = topPageRepository.findByCode(companyId, copyCode).get();
		if (findTopPage.isPresent()) {
			if (isCheckOverWrite) {
				// overwrite
				String newLayoutId = layoutService.copyTopPageLayout(findCopyTopPage.getLayoutId());
				TopPage newTopPage = TopPage.createFromJavaType(topPage.getCompanyId(), topPage.getTopPageCode().v(),
						newLayoutId, topPage.getTopPageName().v(), topPage.getLanguageNumber());
				topPageRepository.update(newTopPage);
			} else {
				throw new BusinessException("Msg_3");
			}
		} else {
			// register new
			String newLayoutId = layoutService.copyTopPageLayout(findCopyTopPage.getLayoutId());
			TopPage newTopPage = TopPage.createFromJavaType(topPage.getCompanyId(), topPage.getTopPageCode().v(),
					newLayoutId, topPage.getTopPageName().v(), topPage.getLanguageNumber());
			topPageRepository.add(newTopPage);
		}
		addStandardMenu(companyId, System.COMMON.value, MenuClassification.TopPage.value, copyCode, topPage);
	}
	
	private void addStandardMenu(String cID, int system, int classification, String copyCode, TopPage topPage) {
		Optional<StandardMenu> standardMenubyCode = standardMenuRepo.getStandardMenubyCode(cID, topPage.getTopPageCode().v(), system, classification);
		if(standardMenubyCode.isPresent()) {
			StandardMenu standardMenuUpdate = standardMenubyCode.get();
			standardMenuUpdate.setTargetItems(topPage.getTopPageName().v());
			standardMenuUpdate.setDisplayName(new MenuDisplayName(topPage.getTopPageName().v()));
			standardMenuRepo.updateStandardMenu(standardMenuUpdate);
		}else {
			int maxOrder = standardMenuRepo.maxOrderStandardMenu(cID, system, classification);
			StandardMenu standardMenuUpdate = new StandardMenu(cID, new MenuCode(topPage.getTopPageCode().v()),
					topPage.getTopPageName().v(), new MenuDisplayName(topPage.getTopPageName().v()), maxOrder + 1,
					MenuAtr.Menu, "/nts.uk.com.web/view/ccg/008/a/index.xhtml", System.valueOf(system),
					MenuClassification.valueOf(classification), WebMenuSetting.Display, 0, "CCG008", "A",
					"toppagecode =" + topPage.getTopPageCode().v(), 1, 1, 1);
			standardMenuRepo.insertStandardMenu(standardMenuUpdate);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService#removeTopPage(java.lang.String, java.lang.String)
	 */
	@Override
	public void removeTopPage(String topPageCode, String companyId) {
		TopPage tp = topPageRepository.findByCode(companyId, topPageCode).get();
		topPageRepository.remove(companyId, topPageCode);
		if (tp.getLayoutId() != null) {
			if (!tp.getLayoutId().equals("")) {
				layoutService.deleteLayout(companyId, tp.getLayoutId());
			}
		}
	}
}
