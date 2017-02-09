/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.wageledger.outputsetting.WLOutputSettingRepository;

/**
 * The Class OutputSettingRemoveCommandHandler.
 */
@Stateless
public class OutputSettingRemoveCommandHandler extends CommandHandler<OutputSettingRemoveCommand>{
	
	/** The repository. */
	@Inject
	private WLOutputSettingRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutputSettingRemoveCommand> context) {
		this.repository.remove(context.getCommand().code);
	}

}
