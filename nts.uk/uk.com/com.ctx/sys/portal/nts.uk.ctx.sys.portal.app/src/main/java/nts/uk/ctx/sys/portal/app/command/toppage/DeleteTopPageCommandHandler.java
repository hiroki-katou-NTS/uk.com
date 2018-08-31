/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.portal.app.command.toppage;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.portal.dom.enums.MenuClassification;
import nts.uk.ctx.sys.portal.dom.enums.System;
import nts.uk.ctx.sys.portal.dom.standardmenu.StandardMenuRepository;
import nts.uk.ctx.sys.portal.dom.toppage.service.TopPageService;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageJobSetRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.WebMenuRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteTopPageCommandHandler.
 */
@Transactional
@Stateless
public class DeleteTopPageCommandHandler extends CommandHandler<DeleteTopPageCommand> {
	
	@Inject
	private TopPageService topPageService; 
	
	@Inject
	private StandardMenuRepository standardMenuRepository;
	
	@Inject
	private WebMenuRepository webMenuRepository;
	
	@Inject
	private TopPageJobSetRepository topPageJobSetRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DeleteTopPageCommand> context) {
		DeleteTopPageCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		topPageService.removeTopPage(command.getTopPageCode(), companyId);
		
		// add by ThanhPV 
		standardMenuRepository.deleteStandardMenu(companyId, command.getTopPageCode(), System.COMMON.value, MenuClassification.TopPage.value);
		webMenuRepository.removeTreeMenu(companyId, MenuClassification.TopPage.value, command.getTopPageCode());
		// remove top page code selected in CCG018
		topPageJobSetRepository.removeTopPageCode(companyId, command.getTopPageCode());
	}

}
