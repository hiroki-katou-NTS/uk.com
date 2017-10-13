/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.optitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;

/**
 * The Class OptionalItemSaveCommandHandler.
 */
@Stateless
@Transactional
public class OptionalItemSaveCommandHandler extends CommandHandler<OptionalItemSaveCommand> {

	@Inject
	private OptionalItemRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OptionalItemSaveCommand> context) {
		OptionalItem dom = new OptionalItem(context.getCommand());

		this.repository.update(dom);

	}

}
