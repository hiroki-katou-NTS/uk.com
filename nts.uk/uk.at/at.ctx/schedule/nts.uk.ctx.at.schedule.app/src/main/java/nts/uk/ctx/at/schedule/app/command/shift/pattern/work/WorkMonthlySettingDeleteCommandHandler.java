/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.work;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class WorkMonthlySettingDeleteCommandHandler.
 */
@Stateless
public class WorkMonthlySettingDeleteCommandHandler
		extends CommandHandler<WorkMonthlySettingDeleteCommand> {
	
	 /** The Constant ADD. */
 	public static final int ADD = 1;
	 
 	/** The Constant UPDATE. */
 	public static final int UPDATE = 2;
	
	/** The Constant INDEX_FIRST. */
	public static final int INDEX_FIRST = 0;
	
	/** The repository. */
	@Inject
	private WorkMonthlySettingRepository repository;
		

	/**
	 * Handle.
	 *
	 * @param context the context
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<WorkMonthlySettingDeleteCommand> context) {
		
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		WorkMonthlySettingDeleteCommand command = context.getCommand();
		
		// call repository remove
		this.repository.remove(companyId, command.getMonthlyPattnernCode());
	}

}
