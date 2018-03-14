/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.command.systemresource;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.screen.com.app.systemresource.dto.SystemResourceDto;
import nts.uk.shr.com.i18n.resource.I18NResourceCustomizer;

/**
 * The Class SystemResourceSaveCommandHandler.
 */
@Stateless

public class SystemResourceSaveCommandHandler extends CommandHandler<SystemResourceSaveCommand> {
	
	@Inject
	private I18NResourceCustomizer i18NResourceCustomizer;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<SystemResourceSaveCommand> context) {
		// Get command
		SystemResourceSaveCommand command = context.getCommand();
		
		for (SystemResourceDto item : command.getListData()) {
			//save resource
			this.i18NResourceCustomizer.replaceSystemClass(item.getResourceId(),item.getResourceContent());
		}
	}

}
