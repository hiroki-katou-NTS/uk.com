/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySetting;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkMonthlySettingBatchSaveCommandHandler.
 */
@Stateless
public class WorkMonthlySettingBatchSaveCommandHandler
		extends CommandHandler<WorkMonthlySettingBatchSaveCommand> {
	
	/** The repositoy. */
	@Inject
	private WorkMonthlySettingRepository repositoy; 
	

	@Override
	protected void handle(CommandHandlerContext<WorkMonthlySettingBatchSaveCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		WorkMonthlySettingBatchSaveCommand command = context.getCommand();
		
		// to list domain
		List<WorkMonthlySetting> lstDomain = command.toDomainMonth(companyId);
		
		// update all list domain
		this.repositoy.updateAll(lstDomain);
	}

}
