/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.scherec.totaltimes.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalSubjectsSetMemento;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.WorkTypeAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

/**
 * The Class TotalSubjectsDto.
 */
@Getter
@Setter
public class TotalSubjectsDto implements TotalSubjectsSetMemento {

	/** The work type code. */
	private String workTypeCode;

	/** The work type atr. */
	private Integer workTypeAtr;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.total.times.SummaryListSetMamento#
	 * setWorkTypeCode(nts.uk.ctx.at.schedule.dom.shift.pattern.WorkTypeCode)
	 */
	@Override
	public void setWorkTypeCode(WorkTypeCode setWorkTypeCode) {
		this.workTypeCode = setWorkTypeCode.v();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalSubjectsSetMemento#
	 * setWorkTypeAtr(nts.uk.ctx.at.shared.dom.scherec.totaltimes.WorkTypeAtr)
	 */
	@Override
	public void setWorkTypeAtr(WorkTypeAtr workTypeAtr) {
		this.workTypeAtr = workTypeAtr.value;
	}
}
