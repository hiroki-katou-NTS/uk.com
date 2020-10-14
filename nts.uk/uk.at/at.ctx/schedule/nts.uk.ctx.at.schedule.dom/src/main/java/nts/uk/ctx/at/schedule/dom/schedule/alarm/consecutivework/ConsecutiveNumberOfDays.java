package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;
/**
 * 連続できる日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.連続できる日数
 * @author lan_lt
 *
 */
@IntegerRange(min = 1, max = 30)
public class ConsecutiveNumberOfDays extends IntegerPrimitiveValue<ConsecutiveNumberOfDays>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public ConsecutiveNumberOfDays(Integer rawValue) {
		super(rawValue);
	}
}
