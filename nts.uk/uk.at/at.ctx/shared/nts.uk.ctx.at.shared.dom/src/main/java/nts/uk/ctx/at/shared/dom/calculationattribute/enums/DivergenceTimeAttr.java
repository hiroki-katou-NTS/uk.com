package nts.uk.ctx.at.shared.dom.calculationattribute.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
/** 乖離時間Attr */
public enum DivergenceTimeAttr {

	// 0: 使用しない
	NOT_USE(0),
	// 1: 使用する
	USE(1);

	/** The value. */
	public final int value;
	
	/** The Constant values. */
	private final static DivergenceTimeAttr[] values = DivergenceTimeAttr.values();

	/**
	 * Checks if is use.
	 *
	 * @return true, if is use
	 */
	public boolean isUse() {
		return USE.equals(this);
	}

	/**
	 * Value of.
	 *
	 * @param value the value
	 * @return the divergence time attr
	 */
	public static DivergenceTimeAttr valueOf(Integer value) {
		// Invalid object.
		if (value == null) {
			return null;
		}

		// Find value.
		for (DivergenceTimeAttr val : DivergenceTimeAttr.values) {
			if (val.value == value) {
				return val;
			}
		}

		// Not found.
		return null;
	}
}
