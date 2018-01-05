/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.ScreenMode;

/**
 * The Class GoOutTypeRoundingSet.
 */
// 外出種類別丸め設定
@Getter
public class GoOutTypeRoundingSet extends DomainObject {

	/** The offical use compen go out. */
	// 公用、有償外出
	private DeductGoOutRoundingSet officalUseCompenGoOut;

	/** The private union go out. */
	// 私用、組合外出
	private DeductGoOutRoundingSet privateUnionGoOut;

	/**
	 * Instantiates a new go out type rounding set.
	 *
	 * @param officalUseCompenGoOut the offical use compen go out
	 * @param privateUnionGoOut the private union go out
	 */
	public GoOutTypeRoundingSet(DeductGoOutRoundingSet officalUseCompenGoOut,
			DeductGoOutRoundingSet privateUnionGoOut) {
		super();
		this.officalUseCompenGoOut = officalUseCompenGoOut;
		this.privateUnionGoOut = privateUnionGoOut;
	}

	/**
	 * Instantiates a new go out type rounding set.
	 *
	 * @param memento the memento
	 */
	public GoOutTypeRoundingSet(GoOutTypeRoundingSetGetMemento memento) {
		this.officalUseCompenGoOut = memento.getOfficalUseCompenGoOut();
		this.privateUnionGoOut = memento.getPrivateUnionGoOut();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(GoOutTypeRoundingSetSetMemento memento) {
		memento.setOfficalUseCompenGoOut(this.officalUseCompenGoOut);
		memento.setPrivateUnionGoOut(this.privateUnionGoOut);
	}

	/**
	 * Restore data.
	 *
	 * @param screenMode the screen mode
	 * @param oldDomain the old domain
	 */
	public void restoreData(ScreenMode screenMode, GoOutTypeRoundingSet oldDomain) {
		this.officalUseCompenGoOut.restoreData(screenMode, oldDomain.getOfficalUseCompenGoOut());
		this.privateUnionGoOut.restoreData(screenMode, oldDomain.getPrivateUnionGoOut());
	}
	
	/**
	 * Restore default data.
	 *
	 * @param screenMode the screen mode
	 */
	public void restoreDefaultData(ScreenMode screenMode) {
		this.officalUseCompenGoOut.restoreDefaultData(screenMode);
		this.privateUnionGoOut.restoreDefaultData(screenMode);
	}

}
