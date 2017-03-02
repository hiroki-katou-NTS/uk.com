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
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceCode;
import nts.uk.ctx.pr.core.dom.rule.employment.unitprice.UnitPriceHistoryRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class DeleteUnitPriceHistoryCommandHandler.
 */
@Stateless
public class DeleteUnitPriceHistoryCommandHandler extends CommandHandler<DeleteUnitPriceHistoryCommand> {

	/** The unit price history repository. */
	@Inject
	private UnitPriceHistoryRepository unitPriceHistoryRepository;

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
	}

}
