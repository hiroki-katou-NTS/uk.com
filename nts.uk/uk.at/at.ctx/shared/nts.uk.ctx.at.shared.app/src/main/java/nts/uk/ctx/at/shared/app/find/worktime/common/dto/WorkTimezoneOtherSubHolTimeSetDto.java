/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.CompensatoryOccurrenceDivision;
import nts.uk.ctx.at.shared.dom.worktime.common.SubHolTransferSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimezoneOtherSubHolTimeSetSetMemento;

/**
 * The Class WorkTimezoneOtherSubHolTimeSetDto.
 */
@Getter
@Setter
public class WorkTimezoneOtherSubHolTimeSetDto implements WorkTimezoneOtherSubHolTimeSetSetMemento{
	
	/** The sub hol time set. */
	private SubHolTransferSetDto subHolTimeSet;

	/** The work time code. */
	private String workTimeCode;

	/** The origin atr. */
	private Integer originAtr;

	public WorkTimezoneOtherSubHolTimeSetDto() {
		this.subHolTimeSet = new SubHolTransferSetDto();
	}
	
	@Override
	public void setSubHolTimeSet(SubHolTransferSet set) {
		set.saveToMemento(this.subHolTimeSet);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetSetMemento#setWorkTimeCode(nts.uk.ctx.at.
	 * shared.dom.worktime.common.WorkTimeCode)
	 */
	@Override
	public void setWorkTimeCode(WorkTimeCode cd) {
		this.workTimeCode = cd.v();
		
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.
	 * WorkTimezoneOtherSubHolTimeSetSetMemento#setOriginAtr(nts.uk.ctx.at.
	 * shared.dom.worktime.common.OriginAtr)
	 */
	@Override
	public void setOriginAtr(CompensatoryOccurrenceDivision atr) {
		this.originAtr = atr.value;
	}

}
