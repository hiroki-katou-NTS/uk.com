/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class DeductGoOutRoundingSet.
 */
//計上控除別外出丸め設定
@Getter
public class DeductGoOutRoundingSet extends DomainObject {

	/** The deduct time rounding setting. */
	//控除時間の丸め設定
	private GoOutTimeRoundingSetting deductTimeRoundingSetting;
	
	/** The appro time rounding setting. */
	//計上時間の丸め設定
	private GoOutTimeRoundingSetting approTimeRoundingSetting;

	/**
	 * Instantiates a new deduct go out rounding set.
	 *
	 * @param memento the memento
	 */
	public DeductGoOutRoundingSet (DeductGoOutRoundingSetGetMemento memento) {
		this.deductTimeRoundingSetting = memento.getDeductTimeRoundingSetting();
		this.approTimeRoundingSetting = memento.getApproTimeRoundingSetting();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento (DeductGoOutRoundingSetSetMemento memento) {
		memento.setDeductTimeRoundingSetting(this.deductTimeRoundingSetting);
		memento.setApproTimeRoundingSetting(this.approTimeRoundingSetting);
	} 
	
	/**
	 * Restore data.
	 */
	public void restoreData(ScreenMode screenMode, DeductGoOutRoundingSet oldDomain) {
		this.deductTimeRoundingSetting.restoreData(screenMode, oldDomain.getDeductTimeRoundingSetting());
		this.approTimeRoundingSetting.restoreData(screenMode, oldDomain.getApproTimeRoundingSetting());
	}
}
