/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PatternCalendarCommandHandler.
 */
@Stateless
public class SaveDailyPatternCommandHandler extends CommandHandler<DailyPatternCommand> {

	/** The pattern calendar repository. */
	@Inject
	private DailyPatternRepository dailyPatternRepo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
	 * .CommandHandlerContext)
	 */
	@Override
	protected void handle(CommandHandlerContext<DailyPatternCommand> context) {
		String companyId = AppContexts.user().companyId();
		DailyPatternCommand command = context.getCommand();
		String patternCd = command.getPatternCode();

		Optional<DailyPattern> result = this.dailyPatternRepo.findByCode(companyId, patternCd);

		DailyPattern dailyPattern = command.toDomain(companyId);

		if (!result.isPresent()) {
			this.dailyPatternRepo.add(dailyPattern);
		} else {
			this.dailyPatternRepo.update(dailyPattern);
		}
	}

}
