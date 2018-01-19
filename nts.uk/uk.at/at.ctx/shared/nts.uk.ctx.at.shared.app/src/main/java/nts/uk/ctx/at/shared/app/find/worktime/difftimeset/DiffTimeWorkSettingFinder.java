/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakBeakTimeDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDayDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto.DiffTimeWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class DiffTimeWorkSettingFinder.
 */
@Stateless
public class DiffTimeWorkSettingFinder {

	/** The diff time work setting repository. */
	@Inject
	private DiffTimeWorkSettingRepository diffTimeWorkSettingRepository;

	/**
	 * Find by work time code.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the diff time work setting dto
	 */
	public DiffTimeWorkSettingDto findByWorkTimeCode(String workTimeCode) {
		String companyId = AppContexts.user().companyId();
		Optional<DiffTimeWorkSetting> diffTimeOp = diffTimeWorkSettingRepository.find(companyId, workTimeCode);
		if (diffTimeOp.isPresent()) {
			DiffTimeWorkSettingDto dto = new DiffTimeWorkSettingDto();
			diffTimeOp.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
	
	public BreakTimeDayDto findBreakTimeByCode(String worktimeCode) {	
		String companyID = AppContexts.user().companyId();
		Optional<DiffTimeWorkSetting> diffTimeOp = this.diffTimeWorkSettingRepository.find(companyID, worktimeCode);
		
		return getBreakTimeDtos(diffTimeOp);
	}
	
	/**
	 * Gets the break time dtos.
	 *
	 * @param opFixedWorkSetting the op fixed work setting
	 * @return the break time dtos
	 */
	private BreakTimeDayDto getBreakTimeDtos(Optional<DiffTimeWorkSetting> opFixedWorkSetting) {
		
		BreakTimeDayDto breakTimeDtos = new BreakTimeDayDto();
		
		List<BreakTimeDto> breakDto = new ArrayList<>();
		
		List<BreakBeakTimeDto> breakBreakDto = new ArrayList<>();
		
		if (opFixedWorkSetting.isPresent()) {
			List<DiffTimeHalfDayWorkTimezone> lstHalfDayWorkTimezone = opFixedWorkSetting.get().getHalfDayWorkTimezones();
			for(DiffTimeHalfDayWorkTimezone lst : lstHalfDayWorkTimezone){
				Integer count = 1;
				for (DeductionTime timezone : lst.getRestTimezone().getRestTimezones()){
					breakDto.add(new BreakTimeDto(count, createBreakTimeField(timezone.getStart(), timezone.getEnd())));
					count++;
				}
			}
			
			if(opFixedWorkSetting.get().getDayoffWorkTimezone() != null){
				Integer count = 1;
				for (DeductionTime timezone : opFixedWorkSetting.get().getDayoffWorkTimezone().getRestTimezone().getRestTimezones()){
					breakBreakDto.add(new BreakBeakTimeDto(count, createBreakTimeField(timezone.getStart(), timezone.getEnd())));
					count++;
				}
			}
			
			breakTimeDtos.setBreakTimeDto(breakDto);
			breakTimeDtos.setBreakBreakTimeDto(breakBreakDto);
		}
		return breakTimeDtos;
	}
	
	/**
	 * Creates the break time field.
	 *
	 * @param start the start
	 * @param end the end
	 * @return the string
	 */
	private String createBreakTimeField(TimeWithDayAttr start, TimeWithDayAttr end) {
		return start.dayAttr().description + start.getRawTimeWithFormat() + " ~ "
				+ end.dayAttr().description + end.getRawTimeWithFormat();
	}
}
