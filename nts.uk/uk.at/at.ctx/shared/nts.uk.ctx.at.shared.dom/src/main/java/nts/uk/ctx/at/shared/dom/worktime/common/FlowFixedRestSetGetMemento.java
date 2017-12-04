/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FlowFixedRestSetGetMemento.
 */
public interface FlowFixedRestSetGetMemento {

	/**
 	 * Gets the checks if is refer rest time.
 	 *
 	 * @return the checks if is refer rest time
 	 */
 	boolean getIsReferRestTime();

	/**
	 * Gets the use private go out rest.
	 *
	 * @return the use private go out rest
	 */
	 boolean getUsePrivateGoOutRest();

	/**
	 * Gets the use asso go out rest.
	 *
	 * @return the use asso go out rest
	 */
	 boolean getUseAssoGoOutRest();

	/**
	 * Gets the calculate method.
	 *
	 * @return the calculate method
	 */
	 FlowFixedRestCalcMethod getCalculateMethod();
}
