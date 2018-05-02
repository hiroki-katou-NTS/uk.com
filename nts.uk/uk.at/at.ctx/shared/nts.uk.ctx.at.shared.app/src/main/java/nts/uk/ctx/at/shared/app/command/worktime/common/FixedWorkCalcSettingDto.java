/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common;

import lombok.Value;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto.ExceededPredAddVacationCalcDto;
import nts.uk.ctx.at.shared.app.command.worktime.fixedset.dto.OverTimeCalcNoBreakDto;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;

/**
 * The Class FixedWorkCalcSettingDto.
 */
@Value
public class FixedWorkCalcSettingDto implements FixedWorkCalcSettingGetMemento {

	/** The exceeded pred add vacation calc. */
	private ExceededPredAddVacationCalcDto exceededPredAddVacationCalc;

	/** The over time calc no break. */
	private OverTimeCalcNoBreakDto overTimeCalcNoBreak;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getExceededPredAddVacationCalc()
	 */
	@Override
	public ExceededPredAddVacationCalc getExceededPredAddVacationCalc() {
		return new ExceededPredAddVacationCalc(this.exceededPredAddVacationCalc);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingGetMemento
	 * #getOverTimeCalcNoBreak()
	 */
	@Override
	public OverTimeCalcNoBreak getOverTimeCalcNoBreak() {
		return new OverTimeCalcNoBreak(this.overTimeCalcNoBreak);
	}

}
