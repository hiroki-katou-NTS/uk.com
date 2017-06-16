/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.employment.statutory.worktime.command;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.employment.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class UsageUnitSettingSaveCommandHandler.
 */
@Stateless
public class UsageUnitSettingSaveCommandHandler
		extends CommandHandler<UsageUnitSettingSaveCommand> {
	
	/** The repository. */
	@Inject
	private UsageUnitSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<UsageUnitSettingSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		//get command
		UsageUnitSettingSaveCommand command= context.getCommand();
		
		//to domain
		UsageUnitSetting domain = command.toDomain(companyId);
		
		// validate domain
		domain.validate();
		
		// update
		this.repository.update(domain);
	}

}
