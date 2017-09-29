/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.monthly;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class MonthlyPatternUpdateCommandHandler.
 */
@Stateless
public class MonthlyPatternUpdateCommandHandler extends CommandHandler<MonthlyPatternUpdateCommand>{

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
	protected void handle(CommandHandlerContext<MonthlyPatternUpdateCommand> context) {
		// get login user
		LoginUserContext loginUserContext = AppContexts.user();
		
		// get company id by login
		String companyId = loginUserContext.companyId();
		
		// get command
		MonthlyPatternUpdateCommand command = context.getCommand();
		
		// command to domain
		MonthlyPattern domain = command.toDomain(companyId);
		
		// validate domain
		domain.validate();
		
		// check exist data add
		Optional<MonthlyPattern> monthlyPattern = this.repository.findById(companyId,
				domain.getMonthlyPatternCode().v());
		
		// add
		if (!monthlyPattern.isPresent()) {
			this.repository.add(domain);
			return;
		}
		
		// call repository update domain
		this.repository.update(domain);
	}

}
