/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.app.command.workrecord.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workrecord.closure.Closure;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureSaveCommandHandler.
 */
@Stateless
public class ClosureHistoryAddCommandHandler extends CommandHandler<ClosureHistoryAddCommand> {
	
	/** The repository history. */
	@Inject
	private ClosureHistoryRepository repositoryHistory;
	
	/** The repository. */
	@Inject
	private ClosureRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ClosureHistoryAddCommand> context) {

		// get user login
		LoginUserContext loginUserContext = AppContexts.user();

		// get company id
		String companyId = loginUserContext.companyId();

		// get command
		ClosureHistoryAddCommand command = context.getCommand();

		// get last closure history
		Optional<ClosureHistory> closureHistoryLast = this.repositoryHistory
				.findByHistoryLast(companyId, command.getClosureHistoryAdd().getClosureId());

		// get closure
		Optional<Closure> closure = this.repository.getClosureById(companyId,
				command.getClosureHistoryAdd().getClosureId());

		// check exist closure and closure history
		if (closure.isPresent() && closureHistoryLast.isPresent()
				&& closure.get().getMonth().getProcessingDate().v() >= closureHistoryLast.get()
						.getStartDate().v()
				&& command.getClosureHistoryAdd().getStartDate() <= closure.get().getMonth()
						.getProcessingDate().v()) {
			throw new BusinessException("Msg_180");
		}

		if (closure.isPresent() && closureHistoryLast.isPresent()
				&& closure.get().getMonth().getProcessingDate().v() < closureHistoryLast.get()
						.getStartDate().v()
				&& command.getClosureHistoryAdd().getStartDate() <= closureHistoryLast.get()
						.getStartDate().v()) {
			throw new BusinessException("Msg_180");
		}
		
		// to domain
		ClosureHistory domain = command.toDomain(companyId);

		closureHistoryLast.get().setEndDate(
				YearMonth.of(command.getClosureHistoryAdd().getStartDate()).previousMonth());
		
		// update domain
		this.repositoryHistory.update(closureHistoryLast.get());
		
		// add domain
		this.repositoryHistory.add(domain);
	}

}
