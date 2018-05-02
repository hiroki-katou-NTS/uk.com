/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
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
 	 * Sets the checks if is plan actual not match master refer.
 	 *
 	 * @param isPlanActualNotMatchMasterRefer the new checks if is plan actual not match master refer
 	 */
 	void setIsPlanActualNotMatchMasterRefer(boolean isPlanActualNotMatchMasterRefer);

	/**
	 * Sets the calculate method.
	 *
	 * @param method the new calculate method
	 */
	 void setCalculateMethod(FixedRestCalculateMethod method);
}
