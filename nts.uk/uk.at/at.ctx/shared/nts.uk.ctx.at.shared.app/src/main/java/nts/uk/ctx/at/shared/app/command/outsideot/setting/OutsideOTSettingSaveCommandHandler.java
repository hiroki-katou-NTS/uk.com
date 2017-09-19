/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.outsideot.setting;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.outsideot.OutsideOTSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class OvertimeSettingSaveCommandHandler.
 */
@Stateless
public class OutsideOTSettingSaveCommandHandler extends CommandHandler<OutsideOTSettingSaveCommand>{
	
	/** The repository. */
	@Inject
	private OutsideOTSettingRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<OutsideOTSettingSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		OutsideOTSettingSaveCommand command = context.getCommand();
		
		// to domain
		OutsideOTSetting domain = command.toDomain(companyId);
		
		// save domain
		this.repository.save(domain);
	}

}
