package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 勤務上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限.勤務上限日数
 * @author lan_lt
 *
 */
@IntegerRange(min = 1, max = 30)
public class MaxDay extends IntegerPrimitiveValue<MaxDay>{

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;

	public MaxDay(Integer rawValue) {
		super(rawValue);
	}

}
