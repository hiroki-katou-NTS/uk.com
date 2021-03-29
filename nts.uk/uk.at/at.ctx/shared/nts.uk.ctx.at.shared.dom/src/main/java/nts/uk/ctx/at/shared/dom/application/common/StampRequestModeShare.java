package nts.uk.ctx.at.shared.dom.application.common;

import lombok.AllArgsConstructor;

/**
 * 打刻申請モード
 * 
 * @author thanh_nx
 *
 */
@AllArgsConstructor
public enum StampRequestModeShare {

	/**
	 * 打刻申請
	 */
	STAMP_ADDITIONAL(0, "打刻申請"),

	/**
	 * レコーダイメージ申請
	 */
	STAMP_ONLINE_RECORD(1, "レコーダイメージ申請");

	public final int value;

	public final String name;
	
	private final static StampRequestModeShare[] values = StampRequestModeShare.values();

	public static StampRequestModeShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (StampRequestModeShare val : StampRequestModeShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
