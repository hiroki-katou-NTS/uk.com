package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 就業時間帯連続名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限.就業時間帯連続名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class MaxDayOfWorkTimeName extends StringPrimitiveValue<MaxDayOfWorkTimeName>{
	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public MaxDayOfWorkTimeName(String rawValue) {
		super(rawValue);
	}

}
