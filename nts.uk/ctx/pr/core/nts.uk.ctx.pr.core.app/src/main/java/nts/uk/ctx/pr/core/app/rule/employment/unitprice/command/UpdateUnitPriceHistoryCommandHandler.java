/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPrice;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceGetMemento;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistory;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceName;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.service.UnitPriceHistoryService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class UpdateUnitPriceHistoryCommandHandler.
 */
@Stateless
public class UpdateUnitPriceHistoryCommandHandler extends CommandHandler<UpdateUnitPriceHistoryCommand> {

	/** The unit price history repo. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepo;

	/** The unit price history service. */
	@Inject
	private UnitPriceHistoryService unitPriceHistoryService;

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
		Optional<UnitPriceHistory> optUnitPriceHistory = unitPriceHistoryRepo.findById(companyCode, command.getId());
		if (!optUnitPriceHistory.isPresent()) {
			return;
		}
		UnitPriceHistory unitPriceHistory = optUnitPriceHistory.get();

		// Transfer data
		UnitPriceHistory updatedHistory = command.toDomain(companyCode, unitPriceHistory.getId(),
				unitPriceHistory.getUnitPriceCode());
		UnitPrice unitPrice = new UnitPrice(new UnitPriceGetMemento() {

			@Override
			public UnitPriceName getName() {
				return new UnitPriceName(command.getUnitPriceName());
			}

			@Override
			public CompanyCode getCompanyCode() {
				return companyCode;
			}

			@Override
			public UnitPriceCode getCode() {
				return new UnitPriceCode(command.getUnitPriceCode());
			}
		});

		// Validate
		unitPriceHistoryService.validateRequiredItem(updatedHistory);

		// Update to db.
		unitPriceHistoryRepo.update(unitPrice, updatedHistory);
	}
}
