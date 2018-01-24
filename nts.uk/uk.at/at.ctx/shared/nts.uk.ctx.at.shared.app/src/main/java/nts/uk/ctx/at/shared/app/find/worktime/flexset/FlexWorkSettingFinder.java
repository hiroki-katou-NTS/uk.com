/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.flexset;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakBeakTimeDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDayDto;
import nts.uk.ctx.at.shared.app.find.breaktime.dto.BreakTimeDto;
import nts.uk.ctx.at.shared.app.find.worktime.flexset.dto.FlexWorkSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexHalfDayWorkTime;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * The Class FlexWorkSettingFinder.
 */
@Stateless
public class FlexWorkSettingFinder {

	/** The repository. */
	@Inject
	private FlexWorkSettingRepository repository;

	/**
	 * Find by id.
	 *
	 * @param workTimeCode
	 *            the work time code
	 * @return the optional
	 */
	public FlexWorkSettingDto findById(String workTimeCode) {

		// get company id
		String companyId = AppContexts.user().companyId();

		// call repository find by id
		Optional<FlexWorkSetting> flexWorkSetting = this.repository.find(companyId, workTimeCode);

		if (flexWorkSetting.isPresent()) {
			FlexWorkSettingDto dto = new FlexWorkSettingDto();
			flexWorkSetting.get().saveToMemento(dto);
			return dto;
		}
		return null;
	}
	
	/**
	 * Gets the break time dtos.
	 *
	 * @param opFixedWorkSetting the op fixed work setting
	 * @return the break time dtos
	 */
	public BreakTimeDayDto getBreakTimeDtos(FlexWorkSetting opFlexWorkSetting) {
		
		BreakTimeDayDto breakTimeDtos = new BreakTimeDayDto();
		
		List<BreakTimeDto> breakDto = new ArrayList<>();
		
		List<BreakBeakTimeDto> breakBreakDto = new ArrayList<>();
		
		if (opFlexWorkSetting != null) {
			List<FlexHalfDayWorkTime> lstHalfDayWorkTimezone = opFlexWorkSetting.getLstHalfDayWorkTimezone();
			for(FlexHalfDayWorkTime lst : lstHalfDayWorkTimezone){
				Integer count = 1;
				for (DeductionTime timezone : lst.getRestTimezone().getFixedRestTimezone().getTimezones()){
					breakDto.add(new BreakTimeDto(count, createBreakTimeField(timezone.getStart(), timezone.getEnd())));
					count++;
				}
			}
			
			if(opFlexWorkSetting.getOffdayWorkTime() != null){
				Integer count = 1;
				for (DeductionTime timezone : opFlexWorkSetting.getOffdayWorkTime().getRestTimezone().getFixedRestTimezone().getTimezones()){
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
