/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.bs.employee.app.command.workplace.config;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfig;
import nts.uk.ctx.bs.employee.dom.workplace.config.WorkplaceConfigRepository;
import nts.uk.ctx.bs.employee.dom.workplace.config.info.service.WkpConfigInfoService;
import nts.uk.ctx.bs.employee.dom.workplace.config.service.WkpConfigService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RegisterWorkplaceConfigCommandHandler.
 */
@Stateless
@Transactional
public class RegisterWorkplaceConfigCommandHandler extends CommandHandler<RegisterWorkplaceConfigCommand> {

	/** The workplace config repository. */
	@Inject
	private WorkplaceConfigRepository workplaceConfigRepository;

	/** The wkp config info service. */
	@Inject
	private WkpConfigInfoService wkpConfigInfoService;

	@Inject
	private WkpConfigService wkpConfigService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<RegisterWorkplaceConfigCommand> context) {
		String companyId = AppContexts.user().companyId();
		RegisterWorkplaceConfigCommand command = context.getCommand();
		// convert command to domain
		WorkplaceConfig workplaceConfig = command.toDomain(companyId);

		// get start date of add new hist
		GeneralDate addHistStart = command.getWkpConfigHistory().getPeriod().getStartDate();

		// validate add hist and return first histId
		String fisrtHistId = wkpConfigService.validateAddHistory(companyId, addHistStart);

		// add workplace config and return add new historyId
		String addNewHistId = workplaceConfigRepository.add(workplaceConfig);

		// copy latest ConfigInfoHist from fisrtHistId
		wkpConfigInfoService.copyWkpConfigInfoHist(companyId, fisrtHistId, addNewHistId);

		// update previous history
		wkpConfigService.updatePrevHistory(companyId,fisrtHistId,addHistStart);
		
		// update previous config info 
		wkpConfigInfoService.updatePrevious(fisrtHistId,addHistStart);
	}

}
