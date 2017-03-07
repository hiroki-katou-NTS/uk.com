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
 * The Class WageTableHistoryAddCommandHandler.
 */
@Stateless
public class WageTableHistoryAddCommandHandler extends CommandHandler<WageTableHistoryAddCommand> {

	/** The certify group repository. */
	@Inject
	private WageTableHistoryRepository certifyGroupRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<WageTableHistoryAddCommand> context) {

		WageTableHistoryAddCommand command = context.getCommand();

		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		WageTableHistory certifyGroup = command.toDomain(companyCode);

		this.certifyGroupRepository.add(certifyGroup);
	}

}
