/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.report.app.wageledger.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemCode;
import nts.uk.ctx.pr.report.dom.wageledger.aggregate.WLAggregateItemRepository;

/**
 * The Class AggregateItemRemoveCommandHandler.
 */
@Stateless
public class AggregateItemRemoveCommandHandler extends CommandHandler<AggregateItemRemoveCommand>{
	
	/** The repository. */
	@Inject
	private WLAggregateItemRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<AggregateItemRemoveCommand> context) {
		this.repository.remove(new WLAggregateItemCode(context.getCommand().getCode()));
	}

}
