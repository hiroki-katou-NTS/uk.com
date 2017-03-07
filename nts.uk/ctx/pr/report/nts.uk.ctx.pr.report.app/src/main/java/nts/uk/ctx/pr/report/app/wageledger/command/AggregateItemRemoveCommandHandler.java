/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.company.CompanyCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AggregateItemRemoveCommandHandler.
 */
@Stateless
public class AggregateItemRemoveCommandHandler extends CommandHandler<AggregateItemRemoveCommand> {
	
	/** The repository. */
	@Inject
	private WLAggregateItemRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	@Transactional
	protected void handle(CommandHandlerContext<AggregateItemRemoveCommand> context) {
		CompanyCode companyCode = new CompanyCode(AppContexts.user().companyCode());
		this.repository.remove(companyCode, new WLAggregateItemCode(context.getCommand().getCode()));
	}

}
