/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.app.rule.employment.unitprice.command;

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
 * The Class CreateUnitPriceHistoryCommandHandler.
 */
@Stateless
public class CreateUnitPriceHistoryCommandHandler extends CommandHandler<CreateUnitPriceHistoryCommand> {

	/** The unit price history repository. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepository;

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
	protected void handle(CommandHandlerContext<CreateUnitPriceHistoryCommand> context) {
		// Get command.
		CreateUnitPriceHistoryCommand command = context.getCommand();

		// Get the current company code.
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());

		// Transfer data
		UnitPriceHistory unitPriceHistory = command.toDomain(companyCode);
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
		unitPriceHistoryService.validateRequiredItem(unitPriceHistory);
		unitPriceHistoryService.validateDateRange(unitPriceHistory);

		// Insert into db.
		unitPriceHistoryRepository.add(unitPrice, unitPriceHistory);
	}

}
