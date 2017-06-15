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
import nts.uk.ctx.at.record.app.find.workrecord.closure.dto.ClosureHistoryDto;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistory;
import nts.uk.ctx.at.record.dom.workrecord.closure.ClosureHistoryRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.context.LoginUserContext;

/**
 * The Class ClosureSaveCommandHandler.
 */
@Stateless
public class ClosureHistorySaveCommandHandler extends CommandHandler<ClosureHistorySaveCommand> {
	
	/** The repository. */
	@Inject
	private ClosureHistoryRepository repository;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<ClosureHistorySaveCommand> context) {
		
		// get user login
		LoginUserContext loginUserContext = AppContexts.user();
		
		//get company id
		String companyId = loginUserContext.companyId();
		
		// get command
		ClosureHistorySaveCommand command = context.getCommand();
		
		Optional<ClosureHistory> closureHistory = this.repository.findByHistoryId(companyId,
				command.getClosureHistory().getClosureId(),
				command.getClosureHistory().getClosureHistoryId());
		
		if(closureHistory.isPresent()){
			Optional<ClosureHistory> closureHistoryLast = this.repository
					.findByHistoryLast(companyId, command.getClosureHistory().getClosureId());
			
			// edit history not last 
			if (closureHistoryLast.isPresent()
					&& closureHistoryLast.get().getClosureHistoryId()
							.equals(command.getClosureHistory().getClosureHistoryId())
					&& command.getClosureHistory().getClosureDate() != closureHistoryLast.get()
							.toClosureDate()) {
				throw new BusinessException("Msg_154");
			}
			ClosureHistoryDto dto = command.getClosureHistory();
			dto.setStartDate(closureHistory.get().getStartDate().v());
			dto.setEndDate(closureHistory.get().getEndDate().v());
			command.setClosureHistory(dto);
		}
		
		// to domain
		ClosureHistory domain = command.toDomain(companyId);
		
		// update domain
		this.repository.update(domain);
	}

}
