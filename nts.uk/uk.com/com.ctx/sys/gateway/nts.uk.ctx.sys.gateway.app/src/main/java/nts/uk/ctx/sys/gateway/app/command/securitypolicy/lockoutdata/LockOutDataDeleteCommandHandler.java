/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.gateway.app.command.securitypolicy.lockoutdata;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.lockoutdata.LockOutDataRepository;

@Stateless
public class LockOutDataDeleteCommandHandler  extends CommandHandler<LockOutDataDeleteCommand>  {
	
	@Inject
	private LockOutDataRepository lockOutDataRepository;

	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<LockOutDataDeleteCommand> context) {
		// Get new domain
		LockOutDataDeleteCommand command = context.getCommand();
		List<String> lstUserId = command.getLstUserId();
		this.lockOutDataRepository.remove(lstUserId);
	}

}
