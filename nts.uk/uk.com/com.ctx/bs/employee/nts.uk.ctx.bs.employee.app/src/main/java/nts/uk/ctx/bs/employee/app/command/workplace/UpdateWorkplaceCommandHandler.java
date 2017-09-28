/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.Workplace;
import nts.uk.ctx.bs.employee.dom.workplace.WorkplaceRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
public class UpdateWorkplaceCommandHandler extends CommandHandler<RegisterWorkplaceCommand>{

	@Inject
	private WorkplaceRepository workplaceRepository;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceCommand> context) {
		//Update history of latest history
		RegisterWorkplaceCommand command = context.getCommand();
		String companyId = AppContexts.user().companyId();
		Workplace wkp = command.toDomain(companyId);
		workplaceRepository.update(wkp);
	}

}
