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
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHead;
import nts.uk.ctx.pr.core.dom.wagetable.WageTableHeadRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistory;
import nts.uk.ctx.pr.core.dom.wagetable.history.WageTableHistoryRepository;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHeadService;
import nts.uk.ctx.pr.core.dom.wagetable.history.service.WageTableHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class WageTableHistoryAddCommandHandler.
 */
@Stateless
public class WageTableHistoryAddCommandHandler
		extends CommandHandlerWithResult<WageTableHistoryAddCommand, WageTableHistory> {

	/** The wage table head repo. */
	@Inject
	private WageTableHeadRepository headRepo;

	/** The wage table history repo. */
	@Inject
	private WageTableHistoryRepository historyRepo;

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
	protected WageTableHistory handle(CommandHandlerContext<WageTableHistoryAddCommand> context) {

		WageTableHistoryAddCommand command = context.getCommand();

		String companyCode = AppContexts.user().companyCode();

		WageTableHead header = command.getWageTableHeadDto().toDomain(companyCode);

		WageTableHistoryDto historyDto = command.getWageTableHistoryDto();
		historyDto.setHistoryId(IdentifierUtil.randomUniqueId());

		WageTableHistory history = historyDto.toDomain(companyCode, header.getCode().v());

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
