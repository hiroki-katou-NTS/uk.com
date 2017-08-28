/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.totaltimes;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimes;
import nts.uk.ctx.at.schedule.dom.shift.totaltimes.TotalTimesRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveTotalTimesCommandHandler.
 */
@Stateless
public class SaveTotalTimesCommandHandler extends CommandHandler<TotalTimesCommand> {

	/** The total times repo. */
	@Inject
	private TotalTimesRepository totalTimesRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<TotalTimesCommand> context) {
		// Get context info
		String companyId = AppContexts.user().companyId();

		// Get command
		TotalTimesCommand command = context.getCommand();

		// Find details
		Optional<TotalTimes> result = this.totalTimesRepo.getTotalTimesDetail(companyId,
				command.getTotalCountNo());

		// Convert to domain
		TotalTimes totalTimes = command.toDomain(companyId);

		// Check exist
		if (!result.isPresent()) {
			throw new BusinessException("Msg_3");
		}

		// Alway has 30 items and allow update only
		this.totalTimesRepo.update(totalTimes);
	}

}
