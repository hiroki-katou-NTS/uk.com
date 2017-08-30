/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily;

import java.util.Objects;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class SaveDailyPatternCommandHandler.
 */
@Stateless
public class SaveDailyPatternCommandHandler extends CommandHandler<DailyPatternCommand> {

	/** The daily pattern repo. */
	@Inject
	private DailyPatternRepository dailyPatternRepo;

	/** The basic schedule service. */
	@Inject
	private BasicScheduleService basicScheduleService;

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

		// Check duplicate code in new mode.
		if (!command.getIsEditting() && result.isPresent()) {
			// validate eap and find messegeId.
			throw new BusinessException("Msg_3");
		}

		command.getDailyPatternVals().stream().filter(Objects::nonNull).forEach(t -> {
			// check pair work days
			if (!StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true)) {
				basicScheduleService.checkPairWorkTypeWorkTime(t.getWorkTypeSetCd(),
						t.getWorkingHoursCd());
			}
		});
		
		DailyPattern dailyPattern = command.toDomain(companyId);

		// check add or update
		if (!result.isPresent()) {
			this.dailyPatternRepo.add(dailyPattern);
		} else {
			this.dailyPatternRepo.update(dailyPattern);
		}
	}

}
