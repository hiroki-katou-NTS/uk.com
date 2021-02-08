package nts.uk.ctx.at.function.dom.processexecution.tasksetting.primitivevalue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 1日の繰り返し間隔時間
 */
public enum OneDayRepeatIntervalDetail {
	MIN_10(0,"Enum_OneDayRepeatIntervalDetail_MIN_10"),
	MIN_15(1,"Enum_OneDayRepeatIntervalDetail_MIN_15"),
	MIN_20(2,"Enum_OneDayRepeatIntervalDetail_MIN_20"),
	MIN_30(3,"Enum_OneDayRepeatIntervalDetail_MIN_30"),
	MIN_60(4,"Enum_OneDayRepeatIntervalDetail_MIN_60");
	
	/** The value. */
	public final int value;

	/** The name id. */
	public final String nameId;

	private OneDayRepeatIntervalDetail(int value, String nameId) {
		this.value = value;
		this.nameId = nameId;
	}
	
	public int getMinuteValue() {
		Pattern p = Pattern.compile(".MIN_([0-9]+)");
		Matcher m = p.matcher(nameId);
		if (m.find()) {
			return Integer.parseInt(m.group(1));
		}
		return 0;
	}
}
