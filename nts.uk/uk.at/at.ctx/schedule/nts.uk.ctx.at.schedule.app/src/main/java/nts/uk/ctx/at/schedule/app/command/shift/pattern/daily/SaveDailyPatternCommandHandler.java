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
		// Check patternName Null.
//		if (StringUtil.isNullOrEmpty(command.getPatternName(), true)) {
//			// validate eap and find messegeId.
//			throw new BusinessException("");
//		}
		
		// Check validate dailypattern all not setting
//		List<DailyPatternValDto> checkNotSetting = command.getDailyPatternVals().stream().filter(Objects::nonNull)
//				.collect(Collectors.toList());
//		if (checkNotSetting.isEmpty()) {
//			throw new BusinessException("Msg_31");
//		}
		
		command.getDailyPatternVals().stream().filter(Objects::nonNull).forEach(t -> {
			// check pair work days
			if(!StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true)  && !StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true)){
				basicScheduleService.checkPairWorkTypeWorkTime(t.getWorkTypeSetCd(), t.getWorkingHoursCd());
			}
		});
		
		
		// Check validate 
//		command.getDailyPatternVals().forEach(new Consumer<DailyPatternValDto>() {
//			@Override
//			public void accept(DailyPatternValDto t) {
//				if(t != null){
//					// check pair work days
//					if(!StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true)  && !StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true)){
//						basicScheduleService.checkPairWorkTypeWorkTime(t.getWorkTypeSetCd(), t.getWorkingHoursCd());
//					}
//				 
//					// check validate eap and find msg25
//					if(!StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true) && !StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true) && t.getDays() == null ){
//						throw new BusinessException("Msg_25");
//					}
//					
//					// check validate eap and find msg25
//					if(StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true) && StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true) && t.getDays() == null ){
//						throw new BusinessException("Msg_25");
//					}
//					
//					// check validate eap and find msg22
//					if(StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true) && !StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true)){
//						throw new BusinessException("Msg_23");
//					}
//					
//					// check validate eap and find msg22
//					if(StringUtil.isNullOrEmpty(t.getWorkTypeSetCd(), true) && StringUtil.isNullOrEmpty(t.getWorkingHoursCd(), true) && t.getDays() != null ){
//						throw new BusinessException("Msg_22");
//					}
//				}
//			}
//		});
		
		
		// check add or update
		if (!result.isPresent()) {
			this.dailyPatternRepo.add(dailyPattern);
		} else {
			this.dailyPatternRepo.update(dailyPattern);
		}
	}

}
