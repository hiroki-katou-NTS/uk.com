/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config.info;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfo;
import nts.uk.ctx.bs.employee.dom.workplace.configinfo.WorkplaceConfigInfoRepository;

/**
 * The Class RegisterWorkplaceConfigInfoCommandHandler.
 */
@Stateless
public class RegisterWorkplaceConfigInfoCommandHandler extends CommandHandler<RegisterWorkplaceConfigInfoCommand>{

	/** The workplace config info repository. */
	@Inject WorkplaceConfigInfoRepository workplaceConfigInfoRepository;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceConfigInfoCommand> context) {
		RegisterWorkplaceConfigInfoCommand command = context.getCommand();
		WorkplaceConfigInfo workplaceConfigInfo = command.toDomain();
		//add new 
		workplaceConfigInfoRepository.add(workplaceConfigInfo);
	}

}
