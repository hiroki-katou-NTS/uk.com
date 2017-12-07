/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import lombok.Getter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSettingSetMemento;

/**
 * The Class DayOffTimezoneSettingDto.
 */
@Getter
public class DayOffTimezoneSettingDto implements DayOffTimezoneSettingSetMemento {

	/** The is update start time. */
	private boolean isUpdateStartTime;
	
	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		// TODO Auto-generated method stub
		
	}
}
