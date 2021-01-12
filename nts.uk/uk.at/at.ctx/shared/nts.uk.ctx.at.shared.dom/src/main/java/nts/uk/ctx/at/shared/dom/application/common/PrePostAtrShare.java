package nts.uk.ctx.at.shared.dom.application.common;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         事前事後区分
 */
@AllArgsConstructor
public enum PrePostAtrShare {
	/**
	 * 0: 事前の受付制限
	 */
	PREDICT(0),
	/**
	 * 1: 事後の受付制限
	 */
	POSTERIOR(1),

	/**
	 * 2: 選択なし
	 */
	NONE(2);

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
