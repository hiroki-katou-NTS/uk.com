package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.flex;

import nts.uk.shr.com.i18n.TextResource;

/**
 * フレックス集計方法
 * @author shuichu_ishida
 */
public enum FlexAggregateMethod {
	/** 原則集計 */
	PRINCIPLE(0, TextResource.localize("KMK004_280")),
	/** 便宜上集計 */
	FOR_CONVENIENCE(1, TextResource.localize("KMK004_281"));

	/** The Constant values. */
	private final static FlexAggregateMethod[] values = FlexAggregateMethod.values();
	
	public int value;
	public String nameId;

	private FlexAggregateMethod(int value, String nameId) {
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
	public static FlexAggregateMethod valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (FlexAggregateMethod val : FlexAggregateMethod.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
