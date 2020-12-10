package nts.uk.ctx.at.shared.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//開始終了区分
public enum StartEndClassificationShare {

	START(0, "開始"),

	END(1, "終了");

	public int value;

	public String name;

	private final static StartEndClassificationShare[] values = StartEndClassificationShare.values();

	public static StartEndClassificationShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (StartEndClassificationShare val : StartEndClassificationShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
