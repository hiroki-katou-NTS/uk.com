package nts.uk.ctx.at.shared.dom.scherec.application.common;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         事前事後区分(反映用)
 */
@AllArgsConstructor
public enum PrePostAtrShare {
	/**
	 * 0: 事前
	 */
	PREDICT(0),
	/**
	 * 1: 事後
	 */
	POSTERIOR(1);

	public final int value;

	private final static PrePostAtrShare[] values = PrePostAtrShare.values();

	public static PrePostAtrShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (PrePostAtrShare val : PrePostAtrShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
