package nts.uk.ctx.at.shared.dom.application.stamp;

import lombok.AllArgsConstructor;

/**
 * @author thanh_nx
 *
 *         打刻申請時刻分類
 */
@AllArgsConstructor
public enum TimeStampAppEnumShare {

	ATTEENDENCE_OR_RETIREMENT(0, "出勤／退勤"),

	EXTRAORDINARY(1, "臨時"),

	GOOUT_RETURNING(2, "外出／戻り"),

	CHEERING(3, "応援");

	public int value;

	public String name;

	private final static TimeStampAppEnumShare[] values = TimeStampAppEnumShare.values();

	public static TimeStampAppEnumShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (TimeStampAppEnumShare val : TimeStampAppEnumShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
