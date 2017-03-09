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
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableCode;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryUpdateCommandHandler.
 */
@Stateless
public class WageTableHistoryUpdateCommandHandler
		extends CommandHandler<WageTableHistoryUpdateCommand> {

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository wageTableHeadRepo;

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
	@Transactional
	protected void handle(CommandHandlerContext<WageTableHistoryUpdateCommand> context) {

		WageTableHistoryUpdateCommand command = context.getCommand();

		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		boolean isExistHeader = this.wageTableHeadRepo.isExistCode(companyCode,
				new WageTableCode(command.getWageTableHeadDto().getCode()));

		WageTableHead header = command.getWageTableHeadDto().toDomain(companyCode);

		if (isExistHeader) {
			this.wageTableHeadRepo.update(header);
		} else {
			this.wageTableHeadRepo.add(header);
		}

		WageTableHistory history = command.getWageTableHistoryDto().toDomain(companyCode,
				header.getCode());

		this.wageTableHistoryRepo.update(history);
	}

}
