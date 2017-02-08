/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateUnitPriceHistoryCommandHandler.
 */
@Stateless
public class UpdateUnitPriceHistoryCommandHandler extends CommandHandler<UpdateUnitPriceHistoryCommand> {

	/** The unit price history repo. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<UpdateUnitPriceHistoryCommand> context) {
		// Get command.
		UpdateUnitPriceHistoryCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Get the history.
		UnitPriceHistory unitPriceHistory = unitPriceHistoryRepo.findById(command.getId()).get();

		// Transfer data
		UnitPriceHistory updatedHistory = command.toDomain(companyCode, unitPriceHistory.getId(),
				unitPriceHistory.getUnitPriceCode());

		// Validate
		updatedHistory.validate();

		// Update to db.
		unitPriceHistoryRepo.update(updatedHistory);
	}
}
