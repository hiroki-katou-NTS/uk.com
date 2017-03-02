/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.ListUtil;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceHistoryFinder;
import nts.uk.ctx.pr.core.app.rule.employment.unitprice.find.UnitPriceItemDto;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteUnitPriceHistoryCommandHandler.
 */
@Stateless
public class DeleteUnitPriceHistoryCommandHandler extends CommandHandler<DeleteUnitPriceHistoryCommand> {

	/** The unit price history repository. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepository;

	/** The unit price repository. */
	@Inject
	private UnitPriceHistoryFinder unitPriceHistoryFinder;

	/** The unit price repository. */
	@Inject
	private UnitPriceRepository unitPriceRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<DeleteUnitPriceHistoryCommand> context) {
		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Get the command
		DeleteUnitPriceHistoryCommand command = context.getCommand();

		unitPriceHistoryRepository.remove(companyCode, new UnitPriceCode(command.getUnitPriceCode()), command.getId());

		// Get the listUnitPriceItemDto
		List<UnitPriceItemDto> listUnitPriceItemDto = unitPriceHistoryFinder.findAll(companyCode);

		// Remove unitPriceHistoryHeader if has no histories
		listUnitPriceItemDto.forEach(item -> {
			if (command.getUnitPriceCode().equals(item.unitPriceCode)) {
				if (ListUtil.isEmpty(item.histories)) {
					unitPriceRepository.remove(companyCode, new UnitPriceCode(command.getUnitPriceCode()));
				}
			}
		});
	}

}
