/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.BreakoutFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento;

/**
 * The Class HolidayFramsetDto.
 */
@Getter
@Setter
public class HolidayFramsetDto implements HolidayFramsetGetMemento {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#getInLegalBreakoutFrameNo()
	 */
	@Override
	public BreakoutFrameNo getInLegalBreakoutFrameNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#getOutLegalBreakoutFrameNo()
	 */
	@Override
	public BreakoutFrameNo getOutLegalBreakoutFrameNo() {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.HolidayFramsetGetMemento#getOutLegalPubHolFrameNo()
	 */
	@Override
	public BreakoutFrameNo getOutLegalPubHolFrameNo() {
		// TODO Auto-generated method stub
		return null;
	}


}
