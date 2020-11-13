package nts.uk.ctx.at.shared.dom.application.stamp;

import lombok.AllArgsConstructor;

@AllArgsConstructor
//打刻申請時間帯分類
public enum TimeZoneStampClassificationShare {
//	2
	PARENT(0,"育児"),
//	6
	NURSE(1,"介護"),
//	5
	BREAK(2,"休憩");
	
	public int value;
	
	public String name;
	
	private final static TimeZoneStampClassificationShare[] values = TimeZoneStampClassificationShare.values();

	public static TimeZoneStampClassificationShare valueOf(Integer value) {
		if (value == null) {
			return null;
		}

		for (TimeZoneStampClassificationShare val : TimeZoneStampClassificationShare.values) {
			if (val.value == value) {
				return val;
			}
		}

		return null;
	}
}
