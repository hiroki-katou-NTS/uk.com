/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.common;

/**
 * The Interface FlowFixedRestSetSetMemento.
 */
public interface FlowFixedRestSetSetMemento {

	/**
	 * Sets the checks if is refer rest time.
	 *
	 * @param val the new checks if is refer rest time
	 */
 	void setIsReferRestTime(boolean val);

	/**
	 * Sets the use private go out rest.
	 *
	 * @param val the new use private go out rest
	 */
	 void setUsePrivateGoOutRest(boolean val);

	/**
	 * Sets the use asso go out rest.
	 *
	 * @param val the new use asso go out rest
	 */
	 void setUseAssoGoOutRest(boolean val);

	/**
	 * Sets the calculate method.
	 *
	 * @param method the new calculate method
	 */
	 void setCalculateMethod(FlowFixedRestCalcMethod method);
}
