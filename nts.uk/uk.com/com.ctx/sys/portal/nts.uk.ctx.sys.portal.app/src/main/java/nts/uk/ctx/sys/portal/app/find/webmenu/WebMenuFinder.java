package nts.uk.ctx.sys.portal.app.find.webmenu;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.portal.dom.webmenu.MenuBar;
import nts.uk.ctx.sys.portal.dom.webmenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WebMenuFinder {

	@Inject
	private WebMenuRepository webMenuRepository;

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

	/**
	 * convert to  MenuBar
	 * 
	 * @param domain
	 * @return
	 */
	private static List<MenuBarDto> toMenuBar(WebMenu domain) {
		List<MenuBarDto> menuBars = domain.getMenuBars().stream().map(mn -> {
			List<TitleMenuDto> titleMenus = toTitleMenu(domain, mn);

			return new MenuBarDto(mn.getCode().v(), mn.getMenuBarName().v(),
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
	private static List<TitleMenuDto> toTitleMenu(WebMenu domain, MenuBar mn) {
		List<TitleMenuDto> titleMenus = mn.getTitleMenu().stream().map(tm -> {
			List<TreeMenuDto> treeMenus = toTreeMenu(domain, tm);

			return new TitleMenuDto(tm.getTitleMenuName().v(), tm.getBackgroundColor().v(),
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
