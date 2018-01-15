/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternDeleteCommandHandler.
 */
@Stateless
public class MonthlyPatternDeleteCommandHandler extends CommandHandler<MonthlyPatternDeleteCommand>{

	/** The repository. */
	@Inject
	private MonthlyPatternRepository repository;
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<MonthlyPatternDeleteCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id by login
		String companyId = loginUserContext.companyId();
		
		// get command
		MonthlyPatternDeleteCommand command = context.getCommand();
		
		// check exist data delete
		Optional<MonthlyPattern> monthlyPattern = this.repository.findById(companyId,
				command.getMonthlyPattnernCode());
		
		// show message 
		if(!monthlyPattern.isPresent()){
			throw new BusinessException("Msg_XXX");
		}
		// call repository remove domain
		this.repository.remove(companyId, command.getMonthlyPattnernCode());
	}

}
