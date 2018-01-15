/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetGetMemento;

/**
 * The Class WorkTimezoneOtherSubHolTimeSetDto.
 */
@Value
public class WorkTimezoneOtherSubHolTimeSetDto implements WorkTimezoneOtherSubHolTimeSetGetMemento {

	/** The sub hol time set. */
	private SubHolTransferSetDto subHolTimeSet;

	/** The work time code. */
	private String workTimeCode;

	/** The origin atr. */
	private Integer originAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetGetMemento#getSubHolTimeSet()
	 */
	@Override
	public SubHolTransferSet getSubHolTimeSet() {
		return new SubHolTransferSet(this.subHolTimeSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetGetMemento#getWorkTimeCode()
	 */
	@Override
	public WorkTimeCode getWorkTimeCode() {
		return new WorkTimeCode(this.workTimeCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetGetMemento#getOriginAtr()
	 */
	@Override
	public CompensatoryOccurrenceDivision getOriginAtr() {
		return CompensatoryOccurrenceDivision.valueOf(this.originAtr);
	}

}
