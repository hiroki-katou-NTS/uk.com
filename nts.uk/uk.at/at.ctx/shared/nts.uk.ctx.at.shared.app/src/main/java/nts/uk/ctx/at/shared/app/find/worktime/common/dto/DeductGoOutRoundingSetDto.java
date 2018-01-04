/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.worktime.common.dto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetSetMemento;
import nts.uk.ctx.at.shared.dom.worktime.common.GoOutTimeRoundingSetting;

/**
 * The Class DeductGoOutRoundingSetDto.
 */
@Getter
@Setter
public class DeductGoOutRoundingSetDto implements DeductGoOutRoundingSetSetMemento {

	/** The deduct time rounding setting. */
	private GoOutTimeRoundingSettingDto deductTimeRoundingSetting;

	/** The appro time rounding setting. */
	private GoOutTimeRoundingSettingDto approTimeRoundingSetting;

	/**
	 * Instantiates a new deduct go out rounding set dto.
	 */
	public DeductGoOutRoundingSetDto() {
		this.deductTimeRoundingSetting = new GoOutTimeRoundingSettingDto();
		this.approTimeRoundingSetting = new GoOutTimeRoundingSettingDto();
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetSetMemento
	 * #setDeductTimeRoundingSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingSetting)
	 */
	@Override
	public void setDeductTimeRoundingSetting(GoOutTimeRoundingSetting set) {
		set.saveToMemento(this.deductTimeRoundingSetting);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.worktime.common.DeductGoOutRoundingSetSetMemento
	 * #setApproTimeRoundingSetting(nts.uk.ctx.at.shared.dom.worktime.common.
	 * GoOutTimeRoundingSetting)
	 */
	@Override
	public void setApproTimeRoundingSetting(GoOutTimeRoundingSetting set) {
		set.saveToMemento(this.approTimeRoundingSetting);
	}

}
