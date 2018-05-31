/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FixedWorkRestSetGetMemento.
 */
public interface FixedWorkRestSetGetMemento {

	/**
	 * Gets the common rest set.
	 *
	 * @return the common rest set
	 */
	 CommonRestSetting getCommonRestSet();

	/**
 	 * Gets the checks if is plan actual not match master refer.
 	 *
 	 * @return the checks if is plan actual not match master refer
 	 */
 	boolean getIsPlanActualNotMatchMasterRefer();

	/**
	 * Gets the calculate method.
	 *
	 * @return the calculate method
	 */
	 FixedRestCalculateMethod getCalculateMethod();
}
