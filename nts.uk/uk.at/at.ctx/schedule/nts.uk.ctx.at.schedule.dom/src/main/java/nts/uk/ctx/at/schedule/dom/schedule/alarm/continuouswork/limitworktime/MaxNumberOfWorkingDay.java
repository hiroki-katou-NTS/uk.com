package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.limitworktime;

import nts.arc.primitive.IntegerPrimitiveValue;
import nts.arc.primitive.constraint.IntegerRange;

/**
 * 就業時間帯の期間内上限勤務
 * 勤務上限日数
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限
 * @author lan_lt
 *
 */
@IntegerRange(min = 1, max = 31)
public class MaxNumberOfWorkingDay extends IntegerPrimitiveValue<MaxNumberOfWorkingDay>{

	/**	serialVersionUID */
	private static final long serialVersionUID = 1L;

	public MaxNumberOfWorkingDay(Integer rawValue) {
		super(rawValue);
	}

}
