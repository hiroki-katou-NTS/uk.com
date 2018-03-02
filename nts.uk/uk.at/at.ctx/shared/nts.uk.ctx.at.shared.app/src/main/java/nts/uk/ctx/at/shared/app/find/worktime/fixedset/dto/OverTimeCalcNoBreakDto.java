/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento;

/**
 * The Class OverTimeCalcNoBreakDto.
 */
@Getter
@Setter
public class OverTimeCalcNoBreakDto implements OverTimeCalcNoBreakSetMemento {

	/** The calc method. */
	private Integer calcMethod;
	
	/** The in law OT. */
	private Integer inLawOT;
	
	/** The not in law OT. */
	private Integer notInLawOT;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento#setCalcMethod(nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak)
	 */
	@Override
	public void setCalcMethod(CalcMethodNoBreak calcMethod) {
		this.calcMethod = calcMethod.value;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento#setInLawOT(nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo)
	 */
	@Override
	public void setInLawOT(OverTimeFrameNo inLawOT) {
		this.inLawOT = inLawOT.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakSetMemento#setNotInLawOT(nts.uk.ctx.at.shared.dom.workrule.outsideworktime.overtime.overtimeframe.OverTimeFrameNo)
	 */
	@Override
	public void setNotInLawOT(OverTimeFrameNo notInLawOT) {
		this.notInLawOT = notInLawOT.v();
	}

}
