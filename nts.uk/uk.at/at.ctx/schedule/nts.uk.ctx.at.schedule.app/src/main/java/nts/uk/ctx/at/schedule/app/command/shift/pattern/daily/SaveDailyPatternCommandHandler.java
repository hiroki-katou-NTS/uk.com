/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.command.shift.pattern.daily;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.schedule.app.command.shift.pattern.daily.dto.DailyPatternValDto;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class PatternCalendarCommandHandler.
 */
@Stateless
public class SaveDailyPatternCommandHandler extends CommandHandler<DailyPatternCommand> {

	/** The pattern calendar repository. */
	@Inject
	private DailyPatternRepository dailyPatternRepo;

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

		DailyPattern dailyPattern = command.toDomain(companyId);

		// Check duplicate code in new mode.
		if (!command.getIsEditting() && result.isPresent()) {
			// validate eap and find messegeId.
			throw new BusinessException("Msg_3");
		}

		// Check validate dailypattern all not setting
		List<DailyPatternValDto> checkNotSetting = command.getDailyPatternVals().stream().filter(Objects::nonNull)
				.collect(Collectors.toList());
		if (checkNotSetting.isEmpty()) {
			throw new BusinessException("Msg_31");
		}
		
		// Check validate 
		command.getDailyPatternVals().forEach(new Consumer<DailyPatternValDto>() {
			@Override
			public void accept(DailyPatternValDto t) {
				if(t != null){
					// check pair work days
					basicScheduleService.ErrorCheckingStatus(t.getWorkTypeSetCd(), t.getWorkingHoursCd());
					// check repeat day 
					if(t.getDays() == null){
						throw new BusinessException("Msg_25");
					}
					
					
					// check validate eap and find msg22
					if(t.getWorkTypeSetCd() == null && t.getDays() !=null ){
						throw new BusinessException("Msg_22");
					}
				}
			}
		});
		
		
		// check add or update
		if (!result.isPresent()) {
			this.dailyPatternRepo.add(dailyPattern);
		} else {
			this.dailyPatternRepo.update(dailyPattern);
		}
	}

}
