/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetSetMemento;

/**
 * The Class HolidayFramsetDto.
 */
@Getter
@Setter
public class HolidayFramsetDto implements HolidayFramsetSetMemento {

	/** The in legal breakout frame no. */
	private Integer inLegalBreakoutFrameNo;

	/** The out legal breakout frame no. */
	private Integer outLegalBreakoutFrameNo;

	/** The out legal pub hol frame no. */
	private Integer outLegalPubHolFrameNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetSetMemento#
	 * setInLegalBreakoutFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakoutFrameNo)
	 */
	@Override
	public void setInLegalBreakoutFrameNo(BreakoutFrameNo inLegalBreakoutFrameNo) {
		this.inLegalBreakoutFrameNo = inLegalBreakoutFrameNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetSetMemento#
	 * setOutLegalBreakoutFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakoutFrameNo)
	 */
	@Override
	public void setOutLegalBreakoutFrameNo(BreakoutFrameNo outLegalBreakoutFrameNo) {
		this.outLegalBreakoutFrameNo = outLegalBreakoutFrameNo.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetSetMemento#
	 * setOutLegalPubHolFrameNo(nts.uk.ctx.at.shared.dom.worktime.common.
	 * BreakoutFrameNo)
	 */
	@Override
	public void setOutLegalPubHolFrameNo(BreakoutFrameNo outLegalPubHolFrameNo) {
		this.outLegalPubHolFrameNo = outLegalPubHolFrameNo.v();
	}

}
