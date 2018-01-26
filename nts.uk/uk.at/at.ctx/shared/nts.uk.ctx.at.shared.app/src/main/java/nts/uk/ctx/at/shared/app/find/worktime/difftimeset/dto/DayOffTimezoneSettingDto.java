/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.difftimeset.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.common.dto.TimeZoneRoundingDto;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DayOffTimezoneSettingSetMemento;

/**
 * The Class DayOffTimezoneSettingDto.
 */
@Getter
@Setter
public class DayOffTimezoneSettingDto implements DayOffTimezoneSettingSetMemento {

	/** The work time no. */
	private Integer workTimeNo;

	/** The timezone. */
	private TimeZoneRoundingDto timezone;

	/** The is legal holiday constraint time. */
	private boolean isLegalHolidayConstraintTime;

	/** The in legal break frame no. */
	private BigDecimal inLegalBreakFrameNo;

	/** The is non statutory dayoff constraint time. */
	private boolean isNonStatutoryDayoffConstraintTime;

	/** The out legal break frame no. */
	private BigDecimal outLegalBreakFrameNo;

	/** The is non statutory holiday constraint time. */
	private boolean isNonStatutoryHolidayConstraintTime;

	/** The out legal pub HD frame no. */
	private BigDecimal outLegalPubHDFrameNo;

	/** The is update start time. */
	private boolean isUpdateStartTime;

	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		this.workTimeNo = workTimeNo;
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		this.timezone = new TimeZoneRoundingDto();
		timezone.saveToMemento(this.timezone);
	}

	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		this.isLegalHolidayConstraintTime = isLegalHolidayConstraintTime;
	}

	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		this.inLegalBreakFrameNo = inLegalBreakFrameNo.v();
	}

	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		this.isNonStatutoryDayoffConstraintTime = isNonStatutoryDayoffConstraintTime;
	}

	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		this.outLegalBreakFrameNo = outLegalBreakFrameNo.v();
	}

	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		this.isNonStatutoryHolidayConstraintTime = isNonStatutoryHolidayConstraintTime;
	}

	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		this.outLegalPubHDFrameNo = outLegalPubHDFrameNo.v();
	}

	@Override
	public void setIsUpdateStartTime(boolean isUpdateStartTime) {
		this.isUpdateStartTime = isUpdateStartTime;
	}
}
