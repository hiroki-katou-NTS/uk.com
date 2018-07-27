/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.ExceededPredAddVacationCalcDto;
import nts.uk.ctx.at.shared.app.find.worktime.fixedset.dto.OverTimeCalcNoBreakDto;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak;

/**
 * The Class FixedWorkCalcSettingDto.
 */
@Getter
@Setter
public class FixedWorkCalcSettingDto implements FixedWorkCalcSettingSetMemento {

	/** The exceeded pred add vacation calc. */
	private ExceededPredAddVacationCalcDto exceededPredAddVacationCalc;
	
	/** The over time calc no break. */
	private OverTimeCalcNoBreakDto overTimeCalcNoBreak;
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento#setExceededPredAddVacationCalc(nts.uk.ctx.at.shared.dom.worktime.fixedset.ExceededPredAddVacationCalc)
	 */
	@Override
	public void setExceededPredAddVacationCalc(ExceededPredAddVacationCalc exceededPredAddVacationCalc) {
		if (exceededPredAddVacationCalc != null) {
			this.exceededPredAddVacationCalc = new ExceededPredAddVacationCalcDto();
			exceededPredAddVacationCalc.saveToMemento(this.exceededPredAddVacationCalc);
		}
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkCalcSettingSetMemento#setOverTimeCalcNoBreak(nts.uk.ctx.at.shared.dom.worktime.fixedset.OverTimeCalcNoBreak)
	 */
	@Override
	public void setOverTimeCalcNoBreak(OverTimeCalcNoBreak overTimeCalcNoBreak) {
		if (overTimeCalcNoBreak != null) {
			this.overTimeCalcNoBreak = new OverTimeCalcNoBreakDto();
			overTimeCalcNoBreak.saveToMemento(this.overTimeCalcNoBreak);
		}
	}

}
