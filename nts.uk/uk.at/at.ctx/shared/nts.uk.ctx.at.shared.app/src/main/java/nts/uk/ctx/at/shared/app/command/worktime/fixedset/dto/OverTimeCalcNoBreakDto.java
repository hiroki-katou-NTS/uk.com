/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto;

import lombok.Value;
import nts.uk.ctx.at.shared.dom.worktime.common.CalcMethodNoBreak;
import nts.uk.ctx.at.shared.dom.worktime.common.OTFrameNo;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento;

/**
 * The Class OverTimeCalcNoBreakDto.
 */
@Value
public class OverTimeCalcNoBreakDto implements OverTimeCalcNoBreakGetMemento {

	/** The calc method. */
	private Integer calcMethod;

	/** The in law OT. */
	private Integer inLawOT;

	/** The not in law OT. */
	private Integer notInLawOT;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getCalcMethod()
	 */
	@Override
	public CalcMethodNoBreak getCalcMethod() {
		return CalcMethodNoBreak.valueOf(calcMethod);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getInLawOT()
	 */
	@Override
	public OTFrameNo getInLawOT() {
		if (this.inLawOT == null) {
			return null;
		}
		return new OTFrameNo(this.inLawOT);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreakGetMemento#
	 * getNotInLawOT()
	 */
	@Override
	public OTFrameNo getNotInLawOT() {
		if (this.notInLawOT == null) {
			return null;
		}
		return new OTFrameNo(this.notInLawOT);
	}

}
