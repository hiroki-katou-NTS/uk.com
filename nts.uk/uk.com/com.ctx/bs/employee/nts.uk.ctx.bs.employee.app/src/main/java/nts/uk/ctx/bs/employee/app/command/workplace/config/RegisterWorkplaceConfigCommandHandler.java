/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;

/**
 * The Class RegisterWorkplaceConfigCommandHandler.
 */
@Stateless
public class RegisterWorkplaceConfigCommandHandler extends CommandHandler<RegisterWorkplaceConfigCommand>{

	/** The workplace config repository. */
	@Inject
	private WorkplaceConfigRepository workplaceConfigRepository;
	
	/** The wkp config info service. */
	@Inject
	private WkpConfigInfoService wkpConfigInfoService;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceConfigCommand> context) {
		RegisterWorkplaceConfigCommand command = context.getCommand();
		WorkplaceConfig workplaceConfig = command.toDomain();
		String companyId = workplaceConfig.getCompanyId();
		
		//add workplace config
		String historyId = workplaceConfigRepository.add(workplaceConfig);
		
		//copy latest history
		wkpConfigInfoService.copyWorkplaceConfigHistory(companyId, historyId);
	}

}
