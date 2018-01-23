/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.workrule.closure;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureSaveCommandHandler.
 */
@Stateless
public class ClosureHistoryAddCommandHandler extends CommandHandler<ClosureHistoryAddCommand> {

	/** The closure repository. */
	@Inject
	private ClosureRepository closureRepository;

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
		Optional<ClosureHistory> closureHistoryLast = this.closureRepository
				.findByHistoryLast(companyId, command.getClosureHistoryAdd().getClosureId());

		// get closure
		Optional<Closure> closure = this.closureRepository.findById(companyId,
				command.getClosureHistoryAdd().getClosureId());

		// check exist closure and closure history
		if (closure.isPresent() && closureHistoryLast.isPresent()
				&& closure.get().getClosureMonth().getProcessingYm().v() >= closureHistoryLast.get()
						.getStartYearMonth().v()
				&& command.getClosureHistoryAdd().getStartDate() <= closure.get().getClosureMonth()
						.getProcessingYm().v()) {
			throw new BusinessException("Msg_180");
		}

		if (closure.isPresent() && closureHistoryLast.isPresent()
				&& closure.get().getClosureMonth().getProcessingYm().v() < closureHistoryLast.get()
						.getStartYearMonth().v()
				&& command.getClosureHistoryAdd().getStartDate() <= closureHistoryLast.get()
						.getStartYearMonth().v()) {
			throw new BusinessException("Msg_102");
		}

		// to domain
		ClosureHistory domain = command.toDomain(companyId);

		closureHistoryLast.get().setEndYearMonth(
				YearMonth.of(command.getClosureHistoryAdd().getStartDate()).previousMonth());

		// update domain
		this.closureRepository.updateHistory(closureHistoryLast.get());

		// add domain
		this.closureRepository.addHistory(domain);
	}

}
