/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryUpdateCommandHandler.
 */
@Stateless
public class WageTableHistoryUpdateCommandHandler
		extends CommandHandler<WageTableHistoryUpdateCommand> {

	/** The certify group repository. */
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
	@Transactional
	protected void handle(CommandHandlerContext<WageTableHistoryUpdateCommand> context) {

		WageTableHistoryUpdateCommand command = context.getCommand();

		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		WageTableHistory wageTableHistory = command.toDomain(companyCode);

		this.wageTableHistoryRepo.update(wageTableHistory);
	}

}
