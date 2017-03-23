/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.wagetable.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.layer.app.command.CommandHandlerWithResult;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.pr.core.app.wagetable.command.dto.WageTableHistoryDto;
import nts.uk.ctx.pr.core.dom.wagetable.WtHead;
import nts.uk.ctx.pr.core.dom.wagetable.WtHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WtHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHeadService;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WtHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryAddCommandHandler.
 */
@Stateless
public class WtHistoryAddCommandHandler
		extends CommandHandlerWithResult<WtHistoryAddCommand, WtHistory> {

	/** The wage table head repo. */
	@Inject
	private WtHeadRepository headRepo;

	/** The wage table history repo. */
	@Inject
	private WtHistoryRepository historyRepo;

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
	protected WtHistory handle(CommandHandlerContext<WtHistoryAddCommand> context) {

		WtHistoryAddCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();

		WtHead header = command.getWageTableHeadDto().toDomain(companyCode);

		WageTableHistoryDto historyDto = command.getWageTableHistoryDto();
		historyDto.setHistoryId(IdentifierUtil.randomUniqueId());

		WtHistory history = historyDto.toDomain(companyCode, header.getCode().v());

		headService.validateRequiredItem(header);

		if (command.isCreateHeader()) {
			headService.checkDuplicateCode(header);
			this.headRepo.add(header);
		} else {
			this.headRepo.update(header);
		}

		historyService.validateRequiredItem(history);
		historyService.validateDateRange(history);

		this.historyRepo.addHistory(history);

		return history;
	}
}
