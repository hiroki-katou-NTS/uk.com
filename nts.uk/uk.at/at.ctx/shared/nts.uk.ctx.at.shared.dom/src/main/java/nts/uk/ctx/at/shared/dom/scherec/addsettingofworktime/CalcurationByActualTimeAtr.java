/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

/**
 * The Enum CalcurationByActualTimeAtr.
 */
// 実働のみで計算するしない区分
public enum CalcurationByActualTimeAtr {
	
	/** The calculation by actual time. */
	// 実働時間のみで計算する
	CALCULATION_BY_ACTUAL_TIME(0, "Enum_CalculationByActualTime"),
	
	/** The calculation other than actual time. */
	// 実働時間以外も含めて計算する
	CALCULATION_OTHER_THAN_ACTUAL_TIME(1, "Enum_calculationOtherThanActualTime");

	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	/** The Constant values. */
	private final static CalcurationByActualTimeAtr[] values = CalcurationByActualTimeAtr.values();

	
	/**
	 * Instantiates a new calcuration by actual time atr.
	 *
	 * @param value the value
	 * @param nameId the name id
	 */
	private CalcurationByActualTimeAtr(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the calcuration by actual time atr
	 */
	public static CalcurationByActualTimeAtr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CalcurationByActualTimeAtr val : CalcurationByActualTimeAtr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
	
	/**
	 * 実働時間のみで計算するか判定する
	 * @return　実働時間のみで判定する
	 */
	public boolean isCalclationByActualTime() {
		return CALCULATION_BY_ACTUAL_TIME.equals(this);
	}
	
	
	/**
	 * 実働時間以外も含めて計算するか判定する
	 * @author ken_takasu
	 * @return　実働時間以外も含めて計算する場合→true
	 */
	public boolean isCalculationOtherThanActualTime() {
		return CALCULATION_OTHER_THAN_ACTUAL_TIME.equals(this);
	}
	
	
	
	
}

