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
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHeadService;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryUpdateCommandHandler.
 */
@Stateless
public class WtHistoryUpdateCommandHandler
		extends CommandHandler<WtHistoryUpdateCommand> {

	/** The wage table head repo. */
	@Inject
	private WtHeadRepository wageTableHeadRepo;

	/** The wage table history repo. */
	@Inject
	private WtHistoryRepository wageTableHistoryRepo;

	/** The history service. */
	@Inject
	private WtHistoryService historyService;

	/** The head service. */
	@Inject
	private WtHeadService headService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<WtHistoryUpdateCommand> context) {

		WtHistoryUpdateCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();

		boolean isExistHeader = this.wageTableHeadRepo.isExistCode(companyCode,
				command.getWageTableHeadDto().getCode());

		WtHead header = command.getWageTableHeadDto().toDomain(companyCode);

		if (!isExistHeader) {
			// TODO
			throw new BusinessException("errorMessage");
		}

		WtHistory history = command.getWageTableHistoryDto().toDomain(companyCode,
				header.getCode().v());

		headService.validateRequiredItem(header);

		historyService.validateRequiredItem(history);
		historyService.validateDateRange(history);

		this.wageTableHeadRepo.update(header);
		this.wageTableHistoryRepo.updateHistory(history);
	}

}
