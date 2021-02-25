package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

/**
 * 清算期間月数
 * @author shuichi_ishida
 */
public enum SettlePeriodMonths {
	/** 2 */
	TWO(2),
	/** 3 */
	THREE(3);

	/** The Constant values. */
	private final static SettlePeriodMonths[] values = SettlePeriodMonths.values();
	
	public int value;
	private SettlePeriodMonths(int value){
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the method
	 */
	public static SettlePeriodMonths valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettlePeriodMonths val : SettlePeriodMonths.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
