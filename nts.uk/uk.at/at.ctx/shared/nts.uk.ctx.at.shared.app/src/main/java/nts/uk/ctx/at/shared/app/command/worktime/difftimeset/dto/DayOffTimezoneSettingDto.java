/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSettingGetMemento;

public class DayOffTimezoneSettingDto {
	
	/** The is update start time. */
	private boolean isUpdateStartTime;
	
	public DayOffTimezoneSetting toDomain() {
		return new DayOffTimezoneSetting(new DayOffTimezoneSettingImpl(this));
	}
	
	public class DayOffTimezoneSettingImpl implements DayOffTimezoneSettingGetMemento {

		private DayOffTimezoneSettingDto dto;
		
		public DayOffTimezoneSettingImpl(DayOffTimezoneSettingDto dayOffTimezoneSettingDto) {
			this.dto = dayOffTimezoneSettingDto;
		}

		@Override
		public Integer getWorkTimeNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public TimeZoneRounding getTimezone() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getIsLegalHolidayConstraintTime() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BreakFrameNo getInLegalBreakFrameNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getIsNonStatutoryDayoffConstraintTime() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BreakFrameNo getOutLegalBreakFrameNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getIsNonStatutoryHolidayConstraintTime() {
			// TODO Auto-generated method stub
			return false;
		}

		@Override
		public BreakFrameNo getOutLegalPubHDFrameNo() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public boolean getIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
