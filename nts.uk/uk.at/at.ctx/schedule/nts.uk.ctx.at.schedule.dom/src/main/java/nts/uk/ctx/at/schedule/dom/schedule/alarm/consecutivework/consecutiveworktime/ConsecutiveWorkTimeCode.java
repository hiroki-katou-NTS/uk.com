package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.consecutiveworktime;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 就業時間帯連続コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の連続勤務.就業時間帯連続コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class ConsecutiveWorkTimeCode extends CodePrimitiveValue<ConsecutiveWorkTimeCode>{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	
	public ConsecutiveWorkTimeCode(String rawValue) {
		super(rawValue);
	}


}
