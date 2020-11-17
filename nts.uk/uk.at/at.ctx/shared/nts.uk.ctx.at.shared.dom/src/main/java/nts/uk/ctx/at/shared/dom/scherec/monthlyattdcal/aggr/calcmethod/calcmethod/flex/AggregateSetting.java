package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

/**
 * 集計設定
 * @author shuichu_ishida
 */
public enum AggregateSetting {
	/** 時間外は全てフレックス時間として管理する */
	INCLUDE_ALL_OUTSIDE_TIME_IN_FLEX_TIME(0, "KMK004_286"),
	/** フレックス時間の内訳を管理する */
	MANAGE_DETAIL(1, "KMK004_350");
	
	public int value;
	public String name;

	/** The Constant values. */
	private final static AggregateSetting[] values = AggregateSetting.values();
	
	private AggregateSetting(int value, String name) {
		this.value = value;
		this.name = name;
	}
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the method
	 */
	public static AggregateSetting valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (AggregateSetting val : AggregateSetting.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
