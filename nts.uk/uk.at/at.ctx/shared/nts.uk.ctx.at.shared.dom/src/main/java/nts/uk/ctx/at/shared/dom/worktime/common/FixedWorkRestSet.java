/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

import lombok.Getter;

/**
 * The Class FixedWorkRestSet.
 */
//固定勤務の休憩設定
@Getter
public class FixedWorkRestSet {

	/** The common rest set. */
	//共通の休憩設定
	private CommonRestSetting commonRestSet; 
	
	/** The calculate method. */
	//計算方法
	private FixedRestCalculateMethod calculateMethod;

	/**
	 * Instantiates a new fixed work rest set.
	 *
	 * @param memento the memento
	 */
	public FixedWorkRestSet(FixedWorkRestSetGetMemento memento) {
		this.commonRestSet = memento.getCommonRestSet();
		this.calculateMethod = memento.getCalculateMethod();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(FixedWorkRestSetSetMemento memento) {
		memento.setCommonRestSet(this.commonRestSet);
		memento.setCalculateMethod(this.calculateMethod);
	}
}
