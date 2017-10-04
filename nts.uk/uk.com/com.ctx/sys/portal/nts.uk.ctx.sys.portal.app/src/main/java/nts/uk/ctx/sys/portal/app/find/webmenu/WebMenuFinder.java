package nts.uk.ctx.sys.portal.app.find.webmenu;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.i18n.custom.IInternationalization;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.portal.app.find.standardmenu.StandardMenuDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.detail.MenuBarDetailDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.detail.TitleBarDetailDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.detail.WebMenuDetailDto;
import nts.uk.ctx.sys.portal.app.find.webmenu.detail.TreeMenuDetailDto;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.WebMenuSetting;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.JobPosition;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSelfSetRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuBar;
import nts.uk.ctx.sys.portal.dom.webmenu.SelectedAtr;
import nts.uk.ctx.sys.portal.dom.webmenu.TitleBar;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTying;
import nts.uk.ctx.sys.portal.dom.webmenu.jobtitletying.JobTitleTyingRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTying;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTyingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

@Stateless
public class WebMenuFinder {

	@Inject
	private WebMenuRepository webMenuRepository;
	
	@Inject
	private StandardMenuRepository standardMenuRepository;
	
	@Inject
	private IInternationalization internationalization;
	
	@Inject
	private PersonalTyingRepository personalTyingRepository;
	
	@Inject
	private JobTitleTyingRepository jobTitleTyingRepository;
	
	@Inject
	private TopPageSelfSetRepository topPageRepository;

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
	 * Find menu list.
	 * @param codes codes
	 * @return menu list
	 */
	public List<WebMenuDetailDto> find(List<String> codes) {
		String companyId = AppContexts.user().companyId();
		List<WebMenu> webMenus = webMenuRepository.find(companyId, codes);
		return webMenus.stream().map(m -> toMenuDetails(m, companyId)).collect(Collectors.toList());
	}
	
	/**
	 * Find all item web menu
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
	 * Find all menu details.
	 * @return menu details
	 */
	public List<WebMenuDetailDto> findAllDetails() {
		LoginUserContext userCtx = AppContexts.user();
		String companyId = userCtx.companyId();
		String employeeId = userCtx.employeeId();
		List<PersonalTying> personTyings = null;
		List<JobTitleTying> jobTitleTyings = null;
		WebMenuDetailDto defaultMenu = null;
		// TODO: Change later, get menu by companyId and userId
		List<WebMenu> menus = webMenuRepository.findAll(companyId);
		if (userCtx.isEmployee()) {
			personTyings = personalTyingRepository.findAll(companyId, employeeId);
			Optional<JobPosition> jobPositionOpt = topPageRepository.getJobPosition(employeeId, GeneralDate.today());
			if (jobPositionOpt.isPresent()) {
				 jobTitleTyings = jobTitleTyingRepository.findWebMenuCode(companyId, Arrays.asList(jobPositionOpt.get().getJobId()));
			}
			if (!jobPositionOpt.isPresent() || jobTitleTyings == null || jobTitleTyings.size() == 0) {
				 defaultMenu = findDefault();
			}
		}
		
		Set<String> resultSet = new HashSet<>();
		resultSet = menus.stream().map(m -> m.getWebMenuCode().v()).collect(Collectors.toSet());
		if (personTyings != null) {
			resultSet.addAll(personTyings.stream().map(p -> p.getWebMenuCode()).collect(Collectors.toSet()));
		}
		if (jobTitleTyings != null) {
			resultSet.addAll(jobTitleTyings.stream().map(j -> j.getWebMenuCode()).collect(Collectors.toSet()));
		}
		if (defaultMenu != null) {
			resultSet.add(defaultMenu.getWebMenuCode());
		}
		
		return find(resultSet.stream().collect(Collectors.toList()));
//		return menus.stream().map(m -> {
//			return toMenuDetails(m, companyId);
//		}).collect(Collectors.toList());
	}
	
	/**
	 * Find default menu.
	 * @return default menu
	 */
	public WebMenuDetailDto findDefault() {
		String companyId = AppContexts.user().companyId();
		Optional<WebMenu> menuOpt = webMenuRepository.findDefault(companyId);
		if (!menuOpt.isPresent()) return null;
		WebMenu menu = menuOpt.get();
		return toMenuDetails(menu, companyId);
	}
	
	/**
	 * To menu details.
	 * @param menu menu
	 * @param companyId company Id
	 * @return web menu details
	 */
	private WebMenuDetailDto toMenuDetails(WebMenu menu, String companyId) {
		List<MenuBarDetailDto> menuBar = menu.getMenuBars().stream().map(m -> {
			List<TitleBarDetailDto> titleBars = m.getTitleMenu().stream().map(t -> {
				List<TreeMenuDetailDto> treeMenus = t.getTreeMenu().stream().map(tm -> {
					String menuCode = tm.getCode().v();
					int system = tm.getSystem().value;
					int classification = tm.getClassification().value;
					Optional<StandardMenu> standardMenus = standardMenuRepository.getStandardMenubyCode(companyId, menuCode, system, classification);
					StandardMenu stdMenu = standardMenus.orElseThrow(() -> new RuntimeException("Menu not found."));
					return new TreeMenuDetailDto(menuCode, stdMenu.getTargetItems(), stdMenu.getDisplayName().v(),
							stdMenu.getUrl(), tm.getDisplayOrder(), system, stdMenu.getMenuAtr().value,
							classification, stdMenu.getAfterLoginDisplay());
				}).sorted((tm1, tm2) -> tm1.getDisplayOrder() - tm2.getDisplayOrder()).collect(Collectors.toList());
				
				return new TitleBarDetailDto(t.getTitleMenuId().toString(), 
						t.getTitleMenuName().v(), t.getBackgroundColor().v(), t.getImageFile(),
						t.getTextColor().v(), t.getTitleMenuAtr().value, t.getTitleMenuCode().v(),
						t.getDisplayOrder(), treeMenus);
			}).sorted((t1, t2) -> t1.getDisplayOrder() - t2.getDisplayOrder()).collect(Collectors.toList());
			
			return new MenuBarDetailDto(m.getMenuBarId().toString(),
					m.getCode().v(), m.getMenuBarName().v(), m.getSelectedAtr().value,
					m.getSystem().value, m.getMenuCls().value, m.getBackgroundColor().v(),
					m.getTextColor().v(), m.getDisplayOrder().intValue(), titleBars);
		}).sorted((m1, m2) -> m1.getDisplayOrder() - m2.getDisplayOrder()).collect(Collectors.toList());
		return new WebMenuDetailDto(companyId, menu.getWebMenuCode().v(),
				menu.getWebMenuName().v(), menu.getDefaultMenu().value, menuBar);
	}
	
	
	/**
	 * 
	 * @return
	 */
	public EditMenuBarDto getEditMenuBarDto() {
		List<EnumConstant> listSelectedAtr = EnumAdaptor.convertToValueNameList(SelectedAtr.class, internationalization);
		List<EnumConstant> listSystem = EnumAdaptor.convertToValueNameList(nts.uk.ctx.sys.portal.dom.enums.System.class, internationalization);
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
	private static List<TreeMenuDto> toTreeMenu(WebMenu domain, TitleBar tm) {
		List<TreeMenuDto> treeMenus = tm.getTreeMenu().stream().map(trm -> {
			return new TreeMenuDto(trm.getCode().v(), trm.getDisplayOrder(), trm.getClassification().value,
					trm.getSystem().value);
		}).collect(Collectors.toList());
		return treeMenus;
	}
	
	/**
	 * Find all Person
	 * @param employeeId
	 * @return
	 */
	public List<PersonTypeDto> findAllPerson(String employeeId) {
		String companyId = AppContexts.user().companyId();
		return personalTyingRepository.findAll(companyId, employeeId).stream().map(e -> {
			return convertToDbType(e);
		}).collect(Collectors.toList()); 
	}

	/**
	 * Convert to Database
	 * @param personalTying
	 * @return
	 */
	private PersonTypeDto convertToDbType(PersonalTying personalTying) {
		PersonTypeDto personTypeDto = new PersonTypeDto();
		personTypeDto.setWebMenuCode(personalTying.getWebMenuCode());
		personTypeDto.setEmployeeId(personalTying.getEmployeeId());
		
		return personTypeDto;
	}

}
