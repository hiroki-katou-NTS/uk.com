/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.dailypattern;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PatternCalendarCommandHandler.
 */
@Stateless
public class DailyPatternCommandHandler extends CommandHandler<DailyPatternCommand> {

	/** The pattern calendar repository. */
	@Inject
	private DailyPatternRepository patternCalendarRepository;

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
		
		List<DailyPattern> result = this.patternCalendarRepository.findByCompanyId(companyId,
				patternCd);

		DailyPattern dailyPattern = command.toDomain(companyId);

		if (CollectionUtil.isEmpty(result)) {
			this.patternCalendarRepository.add(dailyPattern);
		} else {
			this.patternCalendarRepository.update(dailyPattern);
		}
	}

}
