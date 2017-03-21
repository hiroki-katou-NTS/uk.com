/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryAddCommandHandler.
 */
@Stateless
public class WtAddHistoryCommandHandler
		extends CommandHandlerWithResult<WtAddHistoryCommand, WtHistory> {

	/** The wage table head repo. */
	@Inject
	private WtHeadRepository headRepo;

	/** The wage table history repo. */
	@Inject
	private WtHistoryRepository historyRepo;

	/** The history service. */
	@Inject
	private WtHistoryService historyService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected WtHistory handle(CommandHandlerContext<WtAddHistoryCommand> context) {

		WtAddHistoryCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();

		Optional<WtHead> optHeader = this.headRepo.findByCode(companyCode, command.getCode());

		if (!optHeader.isPresent()) {
			// TODO
			throw new BusinessException("errorMessage");
		}

		WtHead header = optHeader.get();
		WtHistory history = WtHistory.initFromHead(header, new YearMonth(command.getStartMonth()));
		history.validate();
		historyService.validateRequiredItem(history);
		historyService.validateDateRange(history);

		this.historyRepo.addHistory(history);

		return history;
	}
}
