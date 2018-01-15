/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.difftimeset.dto;

import nts.uk.ctx.at.shared.app.command.worktime.common.dto.HDWorkTimeSheetSettingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSettingGetMemento;

/**
 * The Class DayOffTimezoneSettingDto.
 */
public class DayOffTimezoneSettingDto extends HDWorkTimeSheetSettingDto {

	/** The is update start time. */
	private boolean isUpdateStartTime;

	/**
	 * To domain.
	 *
	 * @return the day off timezone setting
	 */
	public DayOffTimezoneSetting toDomain() {
		return new DayOffTimezoneSetting(new DayOffTimezoneSettingImpl(this));
	}

	/**
	 * The Class DayOffTimezoneSettingImpl.
	 */
	public class DayOffTimezoneSettingImpl implements DayOffTimezoneSettingGetMemento {

		/** The dto. */
		private DayOffTimezoneSettingDto dto;

		/**
		 * Instantiates a new day off timezone setting impl.
		 *
		 * @param dayOffTimezoneSettingDto the day off timezone setting dto
		 */
		public DayOffTimezoneSettingImpl(DayOffTimezoneSettingDto dayOffTimezoneSettingDto) {
			this.dto = dayOffTimezoneSettingDto;
		}

		@Override
		public Integer getWorkTimeNo() {
			return this.dto.getWorkTimeNo();
		}

		@Override
		public TimeZoneRounding getTimezone() {
			return this.dto.getTimezone();
		}

		@Override
		public boolean getIsLegalHolidayConstraintTime() {
			return this.dto.getIsLegalHolidayConstraintTime();
		}

		@Override
		public BreakFrameNo getInLegalBreakFrameNo() {
			return this.dto.getInLegalBreakFrameNo();
		}

		@Override
		public boolean getIsNonStatutoryDayoffConstraintTime() {
			return this.dto.getIsNonStatutoryHolidayConstraintTime();
		}

		@Override
		public BreakFrameNo getOutLegalBreakFrameNo() {
			return this.dto.getOutLegalBreakFrameNo();
		}

		@Override
		public boolean getIsNonStatutoryHolidayConstraintTime() {
			return this.dto.getIsNonStatutoryHolidayConstraintTime();
		}

		@Override
		public BreakFrameNo getOutLegalPubHDFrameNo() {
			return this.dto.getOutLegalPubHDFrameNo();
		}

		@Override
		public boolean getIsUpdateStartTime() {
			return this.dto.isUpdateStartTime;
		}

	}
}
