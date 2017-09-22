/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.info;

import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfo;
import nts.uk.ctx.bs.employee.dom.workplace.info.WorkplaceInfoRepository;

public class RegisterWorkplaceInfoCommandHandler extends CommandHandler<RegisterWorkplaceInfoCommand> {

	@Inject
	private WorkplaceInfoRepository workplaceInfoRepository;

	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceInfoCommand> context) {
		RegisterWorkplaceInfoCommand command = context.getCommand();
		
		//convert to domain
		WorkplaceInfo workplaceInfo = command.toDomain();
		
		//update
		workplaceInfoRepository.update(workplaceInfo);
	}
	
	
}
