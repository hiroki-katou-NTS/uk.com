/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHeadService;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHistoryService;
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

	/** The history service. */
	@Inject
	private WageTableHistoryService historyService;

	/** The head service. */
	@Inject
	private WageTableHeadService headService;

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

		String companyCode = AppContexts.user().companyCode();

		boolean isExistHeader = this.wageTableHeadRepo.isExistCode(companyCode,
				command.getWageTableHeadDto().getCode());

		WageTableHead header = command.getWageTableHeadDto().toDomain(companyCode);

		if (!isExistHeader) {
			// TODO
			throw new BusinessException("errorMessage");
		}

		WageTableHistory history = command.getWageTableHistoryDto().toDomain(companyCode,
				header.getCode().v());

		headService.validateRequiredItem(header);

		historyService.validateRequiredItem(history);
		historyService.validateDateRange(history);

		this.wageTableHeadRepo.update(header);
		this.wageTableHistoryRepo.updateHistory(history);
	}

}
