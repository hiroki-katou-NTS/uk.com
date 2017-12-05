/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetGetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSetting;

/**
 * The Class DeductGoOutRoundingSetDto.
 */
@Getter
@Setter
public class DeductGoOutRoundingSetDto implements DeductGoOutRoundingSetGetMemento {

	/** The deduct time rounding setting. */
	private GoOutTimeRoundingSettingDto deductTimeRoundingSetting;

	/** The appro time rounding setting. */
	private GoOutTimeRoundingSettingDto approTimeRoundingSetting;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetGetMemento#getDeductTimeRoundingSetting()
	 */
	@Override
	public GoOutTimeRoundingSetting getDeductTimeRoundingSetting() {
		return new GoOutTimeRoundingSetting(this.deductTimeRoundingSetting);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetGetMemento#getApproTimeRoundingSetting()
	 */
	@Override
	public GoOutTimeRoundingSetting getApproTimeRoundingSetting() {
		return new GoOutTimeRoundingSetting(this.approTimeRoundingSetting);
	}
	


}
