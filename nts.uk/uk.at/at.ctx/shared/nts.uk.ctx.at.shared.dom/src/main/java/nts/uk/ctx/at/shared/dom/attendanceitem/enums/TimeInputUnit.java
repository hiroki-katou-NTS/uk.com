package nts.uk.ctx.at.shared.dom.attendanceitem.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeInputUnit {
	
	TIME_INPUT_1Min(0, "1分"),
	TIME_INPUT_5Min(0, "5分"),
	TIME_INPUT_10Min(0, "10分"),
	TIME_INPUT_15Min(0, "15分"),
	TIME_INPUT_30Min(0, "30分"),
	TIME_INPUT_50Min(0, "60分");
	public final int value;
	public final String nameId;
}
