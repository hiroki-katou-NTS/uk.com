package nts.uk.ctx.sys.portal.app.command.webmenu;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.webmenu.MenuBar;
import nts.uk.ctx.sys.portal.dom.webmenu.TitleMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.TreeMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenu;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddWebMenuCommandHandler extends CommandHandler<AddWebMenuCommand> {

	@Inject
	private WebMenuRepository webMenuRepository;
	
	@Override
	protected void handle(CommandHandlerContext<AddWebMenuCommand> context) {
		
		AddWebMenuCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		
		List<MenuBar> menuBars = command.getMenuBars().stream()
				.map(mn -> { 
					String menuBarId = MenuBar.createMenuBarId().toString();
					
					List<TitleMenu> titleMenu = mn.getTitleMenu().stream()
							.map(ti -> {
								String titleMenuId = TitleMenu.createTitleMenuId().toString();
								List<TreeMenu> treeMenu = ti.getTreeMenu().stream()
										.map(tr -> {									
											TreeMenu domainTreeMenu = TreeMenu.createFromJavaType(titleMenuId,tr.getCode(), tr.getDisplayOrder(), tr.getClassification(), tr.getSystem());
											return domainTreeMenu;
										}).collect(Collectors.toList());							
								TitleMenu domainTitleMenu = TitleMenu.createFromJavaType(menuBarId,titleMenuId, ti.getTitleMenuName(), ti.getBackgroundColor(),ti.getImageFile(),ti.getTextColor(),ti.getTitleMenuAtr(),ti.getTitleMenuCode(),ti.getDisplayOrder(), treeMenu);
								return domainTitleMenu;
							}).collect(Collectors.toList());
							
					MenuBar domainMenuBar = MenuBar.createFromJavaType(menuBarId, mn.getMenuBarName(), mn.getSelectedAtr(), mn.getSystem(), mn.getMenuCls(),mn.getCode(), mn.getBackgroundColor(), mn.getTextColor(),mn.getDisplayOrder(), titleMenu);
					
					return domainMenuBar;				
				}).collect(Collectors.toList());
		
		WebMenu domain =  WebMenu.createFromJavaType(companyId, command.getWebMenuCode(), command.getWebMenuName(), command.getDefaultMenu(), menuBars);
		
		
		webMenuRepository.add(domain);
		
	}

}
