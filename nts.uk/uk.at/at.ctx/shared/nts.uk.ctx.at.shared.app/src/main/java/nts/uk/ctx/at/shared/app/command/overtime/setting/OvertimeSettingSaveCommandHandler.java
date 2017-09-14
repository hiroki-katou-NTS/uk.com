/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.overtime.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSetting;
import nts.uk.ctx.at.shared.dom.overtime.setting.OvertimeSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeSettingSaveCommandHandler.
 */
@Stateless
public class OvertimeSettingSaveCommandHandler extends CommandHandler<OvertimeSettingSaveCommand>{
	
	/** The repository. */
	@Inject
	private OvertimeSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OvertimeSettingSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OvertimeSettingSaveCommand command = context.getCommand();
		
		// to domain
		OvertimeSetting domain = command.toDomain(companyId);
		
		// save domain
		this.repository.save(domain);
	}

}
