/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakBeakTimeDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDayDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.FixedWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixHalfDayWorkTimezone;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FixedWorkSettingFinder.
 */
@Stateless
public class FixedWorkSettingFinder {

	/** The fixed work setting repository. */
	@Inject
	private FixedWorkSettingRepository fixedWorkSettingRepository;
	
	/**
	 * Find by code.
	 *
	 * @param worktimeCode the worktime code
	 * @return the fixed work setting dto
	 */
	public FixedWorkSettingDto findByCode(String worktimeCode) {		
		String companyId = AppContexts.user().companyId();
		
		Optional<FixedWorkSetting> opFixedWorkSetting = this.fixedWorkSettingRepository.findByKey(companyId, worktimeCode);
		if (opFixedWorkSetting.isPresent()) {		
			FixedWorkSettingDto fixedWorkSettingDto = new FixedWorkSettingDto();
			opFixedWorkSetting.get().saveToMemento(fixedWorkSettingDto);
			return fixedWorkSettingDto;
		}
		return null;
	}
	
	/**
	 * Find break time by code.
	 *
	 * @param worktimeCode the worktime code
	 * @return the break time day dto
	 */
	public BreakTimeDayDto findBreakTimeByCode(String worktimeCode) {	
		String companyID = AppContexts.user().companyId();
		Optional<FixedWorkSetting> opFixedWorkSetting = this.fixedWorkSettingRepository.findByKey(companyID, worktimeCode);
		
		return getBreakTimeDtos(opFixedWorkSetting);
	}
	
	/**
	 * Gets the break time dtos.
	 *
	 * @param opFixedWorkSetting the op fixed work setting
	 * @return the break time dtos
	 */
	public BreakTimeDayDto getBreakTimeDtos(Optional<FixedWorkSetting> opFixedWorkSetting) {
		
		BreakTimeDayDto breakTimeDtos = new BreakTimeDayDto();
		
		List<BreakTimeDto> breakDto = new ArrayList<>();
		
		List<BreakBeakTimeDto> breakBreakDto = new ArrayList<>();
		
		if (opFixedWorkSetting.isPresent()) {
			List<FixHalfDayWorkTimezone> lstHalfDayWorkTimezone = opFixedWorkSetting.get().getLstHalfDayWorkTimezone();
			for(FixHalfDayWorkTimezone lst : lstHalfDayWorkTimezone){
				Integer count = 1;
				for (DeductionTime timezone : lst.getRestTimezone().getTimezones()){
					breakDto.add(new BreakTimeDto(count, createBreakTimeField(timezone.getStart(), timezone.getEnd())));
					count++;
				}
			}
			
			if(opFixedWorkSetting.get().getOffdayWorkTimezone() != null){
				Integer count = 1;
				for (DeductionTime timezone : opFixedWorkSetting.get().getOffdayWorkTimezone().getRestTimezone().getTimezones()){
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
