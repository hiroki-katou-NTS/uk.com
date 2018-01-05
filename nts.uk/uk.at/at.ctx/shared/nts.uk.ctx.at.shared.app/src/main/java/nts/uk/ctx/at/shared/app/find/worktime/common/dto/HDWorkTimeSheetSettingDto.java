/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HDWorkTimeSheetSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZoneRounding;

/**
 * The Class HDWorkTimeSheetSettingDto.
 */
@Getter
@Setter
public class HDWorkTimeSheetSettingDto implements HDWorkTimeSheetSettingSetMemento{

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setWorkTimeNo(java.lang.Integer)
	 */
	@Override
	public void setWorkTimeNo(Integer workTimeNo) {
		this.workTimeNo = workTimeNo;
		
	}

	@Override
	public void setTimezone(TimeZoneRounding timezone) {
		if (timezone != null) {
			this.timezone = new TimeZoneRoundingDto();
			timezone.saveToMemento(this.timezone);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsLegalHolidayConstraintTime(boolean)
	 */
	@Override
	public void setIsLegalHolidayConstraintTime(boolean isLegalHolidayConstraintTime) {
		this.isLegalHolidayConstraintTime = isLegalHolidayConstraintTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setInLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setInLegalBreakFrameNo(BreakFrameNo inLegalBreakFrameNo) {
		this.inLegalBreakFrameNo = inLegalBreakFrameNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsNonStatutoryDayoffConstraintTime(
	 * boolean)
	 */
	@Override
	public void setIsNonStatutoryDayoffConstraintTime(boolean isNonStatutoryDayoffConstraintTime) {
		this.isNonStatutoryDayoffConstraintTime = isNonStatutoryDayoffConstraintTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setOutLegalBreakFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalBreakFrameNo(BreakFrameNo outLegalBreakFrameNo) {
		this.outLegalBreakFrameNo = outLegalBreakFrameNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setIsNonStatutoryHolidayConstraintTime(
	 * boolean)
	 */
	@Override
	public void setIsNonStatutoryHolidayConstraintTime(boolean isNonStatutoryHolidayConstraintTime) {
		this.isNonStatutoryHolidayConstraintTime = isNonStatutoryHolidayConstraintTime;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.
	 * HDWorkTimeSheetSettingSetMemento#setOutLegalPubHDFrameNo(nts.uk.ctx.at.
	 * shared.dom.worktime.fixedset.BreakFrameNo)
	 */
	@Override
	public void setOutLegalPubHDFrameNo(BreakFrameNo outLegalPubHDFrameNo) {
		this.outLegalPubHDFrameNo = outLegalPubHDFrameNo.v();
	}


}
