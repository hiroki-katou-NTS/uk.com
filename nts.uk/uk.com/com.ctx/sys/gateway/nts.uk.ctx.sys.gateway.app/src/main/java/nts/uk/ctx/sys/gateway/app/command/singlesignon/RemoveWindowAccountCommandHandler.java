/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.singlesignon;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccount;
import nts.uk.ctx.sys.gateway.dom.singlesignon.WindowAccountRepository;

/**
 * The Class RemoveWindowAccountCommandHandler.
 */
@Stateless
public class RemoveWindowAccountCommandHandler extends CommandHandler<RemoveWindowAccountCommand> {

	/** The window account repository. */
	@Inject
	private WindowAccountRepository windowAccountRepository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RemoveWindowAccountCommand> context) {

		// Get command
		RemoveWindowAccountCommand command = context.getCommand();

		List<WindowAccount> listWindowAcc = windowAccountRepository.findByUserId(command.getUserIdDelete());

		// remove
		for (WindowAccount wd : listWindowAcc) {
			windowAccountRepository.remove(wd.getUserId(), wd.getNo());
		}

	}

}
