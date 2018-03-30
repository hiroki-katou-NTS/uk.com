/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.workrecord.monthcal;

/**
 * The Class BooleanGetAtr.
 */
public class BooleanGetAtr {

	public static final int USE_ATR = 1;
	public static final int USE_NOT = 0;

	/**
	 * Gets the atr.
	 *
	 * @param use
	 *            the use
	 * @return the atr
	 */
	public static int getAtrByBoolean(Boolean use) {
		if (use) {
			return USE_ATR;
		}
		return USE_NOT;
	}

	/**
	 * Gets the atr by integer.
	 *
	 * @param atr
	 *            the atr
	 * @return the atr by integer
	 */
	public static Boolean getAtrByInteger(int atr) {
		return atr == USE_ATR;
	}
}
