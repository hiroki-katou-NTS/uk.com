package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

/**
 * 清算期間
 * @author shuichi_ishida
 */
public enum SettlePeriod {
	/** 単月 */
	SINGLE_MONTH(0),
	/** 複数月 */
	MULTI_MONTHS(1);
	
	/** The Constant values. */
	private final static SettlePeriod[] values = SettlePeriod.values();
	
	public int value;
	private SettlePeriod(int value){
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the method
	 */
	public static SettlePeriod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (SettlePeriod val : SettlePeriod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
