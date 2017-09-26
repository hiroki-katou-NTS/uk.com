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
import nts.uk.ctx.bs.employee.dom.workplace.info.service.WorkplaceInfoService;

@Stateless
public class RegisterWorkplaceCommandHandler extends CommandHandler<RegisterWorkplaceCommand>  {

	@Inject
	private WorkplaceRepository workplaceRepository;
	
	@Inject
	private WorkplaceInfoService workplaceInfoService;
	
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceCommand> context) {

		RegisterWorkplaceCommand command = context.getCommand();
		Workplace wkp = command.toDomain();
		String companyId = wkp.getCompanyId();
		String workplaceId = wkp.getWorkplaceId().v();
		
		if(command.getWorkplaceHistory().isEmpty())
		{
			return;
		}
		//add workplace 
		workplaceRepository.add(wkp);
		
		String historyIdLatest = wkp.getWkpHistoryLatest().getHistoryId().v();
		//copy wkp hist
		workplaceInfoService.copyWorkplaceHistory(companyId, workplaceId, historyIdLatest);
	}

}
