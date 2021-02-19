package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * 勤務予定のアラームチェック条件コード
 * 
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.勤務予定のアラームチェック条件コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(2)
public class AlarmCheckConditionScheduleCode extends CodePrimitiveValue<AlarmCheckConditionScheduleCode>{
	/**serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public AlarmCheckConditionScheduleCode(String rawValue) {
		super(rawValue);
	}
}
