package nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TimeInputUnit {
	
	TIME_INPUT_1Min(0, "1分"),
	TIME_INPUT_5Min(1, "5分"),
	TIME_INPUT_10Min(2, "10分"),
	TIME_INPUT_15Min(3, "15分"),
	TIME_INPUT_30Min(4, "30分"),
	TIME_INPUT_50Min(5, "60分");
	public final int value;
	public final String nameId;
}
