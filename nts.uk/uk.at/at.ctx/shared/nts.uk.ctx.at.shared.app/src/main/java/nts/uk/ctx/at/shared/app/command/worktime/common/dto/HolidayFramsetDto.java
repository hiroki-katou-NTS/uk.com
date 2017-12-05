/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento;

/**
 * The Class HolidayFramsetDto.
 */
@Value
public class HolidayFramsetDto implements HolidayFramsetGetMemento {

	/** The in legal breakout frame no. */
	private Integer inLegalBreakoutFrameNo;

	/** The out legal breakout frame no. */
	private Integer outLegalBreakoutFrameNo;

	/** The out legal pub hol frame no. */
	private Integer outLegalPubHolFrameNo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#
	 * getInLegalBreakoutFrameNo()
	 */
	@Override
	public BreakoutFrameNo getInLegalBreakoutFrameNo() {
		return new BreakoutFrameNo(this.inLegalBreakoutFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#
	 * getOutLegalBreakoutFrameNo()
	 */
	@Override
	public BreakoutFrameNo getOutLegalBreakoutFrameNo() {
		return new BreakoutFrameNo(this.outLegalBreakoutFrameNo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#
	 * getOutLegalPubHolFrameNo()
	 */
	@Override
	public BreakoutFrameNo getOutLegalPubHolFrameNo() {
		return new BreakoutFrameNo(this.outLegalPubHolFrameNo);
	}

}
