/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;

/**
 * The Class GoOutTypeRoundingSet.
 */
//外出種類別丸め設定
@Getter
public class GoOutTypeRoundingSet extends DomainObject {

	/** The offical use compen go out. */
	//公用、有償外出
	private DeductGoOutRoundingSet officalUseCompenGoOut;
	
	/** The private union go out. */
	//私用、組合外出
	private DeductGoOutRoundingSet privateUnionGoOut;
	
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
	public void saveToMemento(GoOutTypeRoundingSetSetMemento memento){
		memento.setOfficalUseCompenGoOut(this.officalUseCompenGoOut);
		memento.setPrivateUnionGoOut(this.privateUnionGoOut);
	}
}
