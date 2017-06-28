package nts.uk.ctx.sys.portal.app.find.webmenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.WebMenuSetting;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuBar;
import nts.uk.ctx.sys.portal.dom.webmenu.SelectedAtr;
import nts.uk.ctx.sys.portal.dom.webmenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WebMenuFinder {

	@Inject
	private WebMenuRepository webMenuRepository;
	
	@Inject
	private StandardMenuRepository standardMenuRepository;

	/**
	 * Find a web menu by code
	 * @param webMenuCode
	 * @return
	 */
	public WebMenuDto find(String webMenuCode) {
		String companyId = AppContexts.user().companyId();
		Optional<WebMenu> webMenuOpt = webMenuRepository.find(companyId, webMenuCode);
		if (!webMenuOpt.isPresent()) {
			return null;
		}
		
		return webMenuOpt.map(webMenuItem -> {
			List<MenuBarDto> menuBars = toMenuBar(webMenuItem);
			
			return new WebMenuDto(
					companyId, 
					webMenuItem.getWebMenuCode().v(), 
					webMenuItem.getWebMenuName().v(), 
					webMenuItem.getDefaultMenu().value,
					menuBars);
		}).get();
	}
	
	/**
	 * 
	 * @return
	 */
	public List<WebMenuDto> findAll() {

		String companyId = AppContexts.user().companyId();

		List<WebMenu> webMenuList = webMenuRepository.findAll(companyId);

		List<WebMenuDto> result = webMenuList.stream().map(webMenuItem -> {
			List<MenuBarDto> menuBars = toMenuBar(webMenuItem);
			
			return new WebMenuDto(
					companyId, 
					webMenuItem.getWebMenuCode().v(), 
					webMenuItem.getWebMenuName().v(), 
					webMenuItem.getDefaultMenu().value,
					menuBars);
		}).collect(Collectors.toList());
		
		return result;
	}
	
	public EditMenuBarDto getEditMenuBarDto() {
		List<EnumConstant> listSelectedAtr = EnumAdaptor.convertToValueNameList(SelectedAtr.class);
		List<EnumConstant> listSystem = EnumAdaptor.convertToValueNameList(nts.uk.ctx.sys.portal.dom.enums.System.class);
		List<EnumConstant> listMenuClassification = EnumAdaptor.convertToValueNameList(MenuClassification.class);
		String companyID = AppContexts.user().companyId();
		List<StandardMenuDto> listStandardMenu = standardMenuRepository.findByAtr(companyID, WebMenuSetting.Display.value, MenuAtr.Menu.value)
				.stream().map(item -> StandardMenuDto.fromDomain(item))
				.collect(Collectors.toList());
		return new EditMenuBarDto(listSelectedAtr, listSystem, listMenuClassification, listStandardMenu);
	}

	/**
	 * convert to  MenuBar
	 * 
	 * @param domain
	 * @return
	 */
	private static List<MenuBarDto> toMenuBar(WebMenu domain) {
		List<MenuBarDto> menuBars = domain.getMenuBars().stream().map(mn -> {
			List<TitleBarDto> titleMenus = toTitleMenu(domain, mn);

			return new MenuBarDto(mn.getMenuBarId().toString(), mn.getCode().v(), mn.getMenuBarName().v(),
					mn.getSelectedAtr().value, mn.getSystem().value, mn.getMenuCls().value,
					mn.getBackgroundColor().v(), mn.getTextColor().v(), mn.getDisplayOrder(), titleMenus);
		}).collect(Collectors.toList());
		return menuBars;
	}

	/**
	 * convert to  TitleMenu
	 * 
	 * @param domain
	 * @param mn
	 * @return
	 */
	private static List<TitleBarDto> toTitleMenu(WebMenu domain, MenuBar mn) {
		List<TitleBarDto> titleMenus = mn.getTitleMenu().stream().map(tm -> {
			List<TreeMenuDto> treeMenus = toTreeMenu(domain, tm);

			return new TitleBarDto(tm.getTitleMenuId().toString(), tm.getTitleMenuName().v(), tm.getBackgroundColor().v(),
					tm.getImageFile(), tm.getTextColor().v(), tm.getTitleMenuAtr().value, tm.getTitleMenuCode().v(),
					tm.getDisplayOrder(), treeMenus);
		}).collect(Collectors.toList());
		
		return titleMenus;
	}

	/**
	 * convert to  TreeMenu
	 * 
	 * @param domain
	 * @param tm
	 * @return
	 */
	private static List<TreeMenuDto> toTreeMenu(WebMenu domain, TitleMenu tm) {
		List<TreeMenuDto> treeMenus = tm.getTreeMenu().stream().map(trm -> {
			return new TreeMenuDto(trm.getCode().v(), trm.getDisplayOrder(), trm.getClassification().value,
					trm.getSystem().value);
		}).collect(Collectors.toList());
		return treeMenus;
	}

}
