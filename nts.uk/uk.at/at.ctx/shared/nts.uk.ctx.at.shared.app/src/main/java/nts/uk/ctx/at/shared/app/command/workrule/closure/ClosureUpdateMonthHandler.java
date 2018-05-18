/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class ClosureUpdateMonthHandler.
 */
@Stateless
public class ClosureUpdateMonthHandler extends CommandHandler<ClosureUpdateMonthCommand> {
	
	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

	/* 
	 * @see nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command.CommandHandlerContext)
	 */
	// 当月を次月へ更新する
	@Override
	protected void handle(CommandHandlerContext<ClosureUpdateMonthCommand> context) {
		
		String companyId = AppContexts.user().companyId();
		ClosureUpdateMonthCommand command = context.getCommand();
		
		// find Closure by ID
		Optional<Closure> optClosure = closureRepository.findById(companyId, command.getClosureId());
		
		// check exist
		if(!optClosure.isPresent()) {
			return;
		}
		
		// update CurrentMonth
		Closure closure = optClosure.get();
		closure.updateCurrentMonth();
		closureRepository.update(closure);
	}

}
