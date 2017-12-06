/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * The Class HDWorkTimeSheetSettingDto.
 */
@Getter
@Setter
public class HDWorkTimeSheetSettingDto implements HDWorkTimeSheetSettingGetMemento{

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

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getWorkTimeNo()
	 */
	@Override
	public Integer getWorkTimeNo() {
		return this.workTimeNo;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getTimezone()
	 */
	@Override
	public TimeZoneRounding getTimezone() {
		return new TimeZoneRounding(this.timezone);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getIsLegalHolidayConstraintTime()
	 */
	@Override
	public boolean getIsLegalHolidayConstraintTime() {
		return this.isLegalHolidayConstraintTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getInLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getInLegalBreakFrameNo() {
		return new BreakFrameNo(this.inLegalBreakFrameNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getIsNonStatutoryDayoffConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryDayoffConstraintTime() {
		return this.isNonStatutoryDayoffConstraintTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getOutLegalBreakFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalBreakFrameNo() {
		return new BreakFrameNo(this.outLegalBreakFrameNo);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getIsNonStatutoryHolidayConstraintTime()
	 */
	@Override
	public boolean getIsNonStatutoryHolidayConstraintTime() {
		return this.isNonStatutoryHolidayConstraintTime;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingGetMemento#getOutLegalPubHDFrameNo()
	 */
	@Override
	public BreakFrameNo getOutLegalPubHDFrameNo() {
		return new BreakFrameNo(this.outLegalPubHDFrameNo);
	}



}
