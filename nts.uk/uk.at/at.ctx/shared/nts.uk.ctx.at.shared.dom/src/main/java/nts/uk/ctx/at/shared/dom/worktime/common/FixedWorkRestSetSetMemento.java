/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FixedWorkRestSetSetMemento.
 */
public interface FixedWorkRestSetSetMemento {

	/**
	 * Sets the common rest set.
	 *
	 * @param set the new common rest set
	 */
	 void setCommonRestSet(CommonRestSetting set);

	/**
	 * Sets the calculate method.
	 *
	 * @param method the new calculate method
	 */
	 void setCalculateMethod(FixedRestCalculateMethod method);
}
