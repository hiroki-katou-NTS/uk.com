/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.app.find.shift.pattern.daily.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.WorkingCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternValSetMemento;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.Days;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DispOrder;

/**
 * The Class DailyPatternValDto.
 */
@Getter
@Setter
public class DailyPatternValDto implements DailyPatternValSetMemento {

	/** The disp order. */
	private Integer dispOrder;

	/** The work type set cd. */
	private String workTypeSetCd;

	/** The working hours cd. */
	private String workingHoursCd;

	/** The days. */
	private Integer days;

	/**
	 * Instantiates a new daily pattern val dto.
	 */
	public DailyPatternValDto() {
		super();
	}

	/**
	 * Instantiates a new daily pattern val dto.
	 *
	 * @param cid
	 *            the cid
	 * @param patternCode
	 *            the pattern code
	 * @param dispOrder
	 *            the disp order
	 * @param workTypeSetCd
	 *            the work type set cd
	 * @param workingHoursCd
	 *            the working hours cd
	 * @param days
	 *            the days
	 */
	public DailyPatternValDto(Integer dispOrder, String workTypeSetCd, String workingHoursCd,
			Integer days) {
		this.dispOrder = dispOrder;
		this.workTypeSetCd = workTypeSetCd;
		this.workingHoursCd = workingHoursCd;
		this.days = days;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setDispOrder(nts.uk.ctx.at.shared.dom.dailypattern.DispOrder)
	 */
	@Override
	public void setDispOrder(DispOrder setDispOrder) {
		this.dispOrder = setDispOrder.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setWorkTypeCodes(nts.uk.ctx.at.shared.dom.dailypattern.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCodes(WorkTypeCode setWorkTypeCodes) {
		this.workTypeSetCd = setWorkTypeCodes.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#
	 * setWorkHouseCodes(nts.uk.ctx.at.shared.dom.dailypattern.WorkingCode)
	 */
	@Override
	public void setWorkHouseCodes(WorkingCode setWorkHouseCodes) {
		this.workingHoursCd = setWorkHouseCodes.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValSetMemento#setDays(
	 * nts.uk.ctx.at.shared.dom.dailypattern.Days)
	 */
	@Override
	public void setDays(Days setDays) {
		this.days = setDays.v();
	}

}
