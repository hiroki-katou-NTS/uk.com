package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;
/**
 * 就業時間帯連続名称
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の連続勤務.就業時間帯連続名称
 * @author lan_lt
 *
 */
@StringMaxLength(20)
public class ConsecutiveWorkTimeName extends StringPrimitiveValue<ConsecutiveWorkTimeName>{

	/** serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public ConsecutiveWorkTimeName(String rawValue) {
		super(rawValue);
	}

}
