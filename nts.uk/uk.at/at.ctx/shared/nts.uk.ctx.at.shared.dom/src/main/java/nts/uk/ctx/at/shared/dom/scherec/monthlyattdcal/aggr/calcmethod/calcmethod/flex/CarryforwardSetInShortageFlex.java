package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

/**
 * フレックス不足時の繰越設定
 * @author shuichi_ishida
 */
public enum CarryforwardSetInShortageFlex {
	/** 当月積算 */
	CURRENT_MONTH_INTEGRATION(0, "当月清算"),
	/** 翌月繰越 */
	NEXT_MONTH_CARRYFORWARD(1, "翌月繰越");

	/** The Constant values. */
	private final static CarryforwardSetInShortageFlex[] values = CarryforwardSetInShortageFlex.values();
	
	public int value;
	
	public String name;
	private CarryforwardSetInShortageFlex(int value ,String name){
		this.name = name;
		this.value = value;
	}
	
	/**
	 * Value of.
	 *
	 * @param value
	 *            the value
	 * @return the method
	 */
	public static CarryforwardSetInShortageFlex valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (CarryforwardSetInShortageFlex val : CarryforwardSetInShortageFlex.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
