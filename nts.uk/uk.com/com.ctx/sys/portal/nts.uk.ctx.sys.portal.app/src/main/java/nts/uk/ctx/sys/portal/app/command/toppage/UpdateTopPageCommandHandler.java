/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.enums.MenuAtr;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenu;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.TopPage;
import nts.uk.ctx.sys.portal.dom.toppage.TopPageRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateTopPageCommandHandler.
 */
@Stateless
public class UpdateTopPageCommandHandler extends CommandHandler<UpdateTopPageCommand>{
	
	/** The top page repository. */
	@Inject
	private TopPageRepository topPageRepository;
	
	@Inject
	private StandardMenuRepository standardMenuRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UpdateTopPageCommand> context) {
		UpdateTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Optional<TopPage> findTopPage = topPageRepository.findByCode(companyId,command.getTopPageCode());
		//if exist top page -> update 
		if(findTopPage.isPresent())
		{
			TopPage topPage = command.toDomain();
			topPageRepository.update(topPage);
			
			// add by thanhPV
			StandardMenu standardMenu = StandardMenu.createFromJavaType(companyId, topPage.getTopPageCode().v(),
					topPage.getTopPageName().v(), topPage.getTopPageName().v(), 0, MenuAtr.Menu.value,
					"/nts.uk.com.web/view/ccg/008/a/index.xhtml", System.COMMON.value, MenuClassification.TopPage.value,
					1, 0, "CCG008", "A", "toppagecode=" + topPage.getTopPageCode(), 1, 1, 1);
			standardMenuRepository.updateStandardMenu(standardMenu);
		}
	}

}
