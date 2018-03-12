package nts.uk.ctx.at.function.dom.processexecution.tasksetting.detail;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 実行タスク繰り返す間隔（日）
 */
@IntegerRange(min = 1, max = 999)
public class DailyDaySetting extends IntegerPrimitiveValue<DailyDaySetting> {
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	public DailyDaySetting(Integer rawValue) {
		super(rawValue);
	}
}
