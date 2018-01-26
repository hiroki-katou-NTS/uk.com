package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 実行タスク繰り返す間隔（週）
 */
@IntegerRange(min = 1, max = 52)
public class WeeklyWeekSetting extends IntegerPrimitiveValue<WeeklyWeekSetting> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public WeeklyWeekSetting(Integer rawValue) {
		super(rawValue);
	}
}
