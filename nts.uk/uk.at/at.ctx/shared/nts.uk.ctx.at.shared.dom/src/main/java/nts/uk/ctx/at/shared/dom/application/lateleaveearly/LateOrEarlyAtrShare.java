package nts.uk.ctx.at.shared.dom.application.lateleaveearly;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//遅刻早退区分
public enum LateOrEarlyAtrShare {
//	遅刻
	LATE(0, "遅刻"),
//	早退
	EARLY(1, "早退");

	public final int value;

	public final String name;

	private final static LateOrEarlyAtrShare[] values = LateOrEarlyAtrShare.values();

	public static LateOrEarlyAtrShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (LateOrEarlyAtrShare val : values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}

}
