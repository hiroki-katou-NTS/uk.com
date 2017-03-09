/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;

/**
 * The Class WageTableHistoryDeleteCommandHandler.
 */
@Stateless
@Transactional
public class WageTableHistoryDeleteCommandHandler
		extends CommandHandler<WageTableHistoryDeleteCommand> {

	/** The wage table history repo. */
	@Inject
	private WageTableHistoryRepository wageTableHistoryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WageTableHistoryDeleteCommand> context) {

		WageTableHistoryDeleteCommand command = context.getCommand();

		this.wageTableHistoryRepo.deleteHistory(command.getHistoryId());
	}

}
