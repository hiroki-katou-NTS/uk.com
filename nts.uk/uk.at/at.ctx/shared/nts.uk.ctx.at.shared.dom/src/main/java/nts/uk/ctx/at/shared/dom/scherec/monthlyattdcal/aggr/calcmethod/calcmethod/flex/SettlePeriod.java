package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import nts.uk.shr.com.i18n.TextResource;

/**
 * 清算期間
 * @author shuichi_ishida
 */
public enum SettlePeriod {
	/** 単月 */
	SINGLE_MONTH(0, TextResource.localize("KMK004_272")),
	/** 複数月 */
	MULTI_MONTHS(1, TextResource.localize("KMK004_273"));
	
	/** The Constant values. */
	private final static SettlePeriod[] values = SettlePeriod.values();
	
	public int value;
	public String nameId;

	private SettlePeriod(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
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
