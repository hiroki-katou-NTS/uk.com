package nts.uk.shr.com.security.audittrail.correction.content;

import java.text.NumberFormat;

import lombok.RequiredArgsConstructor;
import nts.arc.enums.EnumAdaptor;

@RequiredArgsConstructor
public enum DataValueAttribute {

	STRING(0),
	COUNT(1),
	MONEY(2),
	TIME(3),
	;
	public final int value;
	
	public static DataValueAttribute of(int value) {
		return EnumAdaptor.valueOf(value, DataValueAttribute.class);
	}
	
	public String format(Object value) {
		switch (this) {
		case STRING:
		case COUNT:
			return value.toString();
		case MONEY:
			return NumberFormat.getInstance().format(value);
		case TIME:
			return formatMinutesToTime((int) value);
		default:
			throw new RuntimeException("invalid attribute: " + this);
		}
	}
	
	private static String formatMinutesToTime(int valueAsMinutes) {
		boolean isMinus = valueAsMinutes < 0;
		int value = Math.abs(valueAsMinutes);
		int minute = value % 60;
		int hour = value / 60;
		return String.format("%s%d:%02d", (isMinus ? "-" : ""), hour, minute);
	}
}
