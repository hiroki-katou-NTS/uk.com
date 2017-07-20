/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.basicworkregister;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.basicworkregister.ClassifiBasicWorkRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ClassifiBWRemoveCommandHandler.
 */
@Stateless
public class ClassifiBWRemoveCommandHandler extends CommandHandler<ClassifiBWRemoveCommand> {

	
	/** The repository. */
	@Inject
	private ClassifiBasicWorkRepository repository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ClassifiBWRemoveCommand> context) {
		// Get Company Id
		String companyId = AppContexts.user().companyId();

		// Get Command
		ClassifiBWRemoveCommand command = context.getCommand();

		// Remove
		this.repository.remove(companyId, command.getClassificationCode());
	}

}
