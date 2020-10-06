package nts.uk.ctx.at.schedule.dom.schedule.alarm.consecutivework.limitworktime;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 就業時間帯上限コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.連続勤務.就業時間帯の期間内上限.就業時間帯上限コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class MaxDayOfWorkTimeCode extends CodePrimitiveValue<MaxDayOfWorkTimeCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	public MaxDayOfWorkTimeCode(String rawValue) {
		super(rawValue);
	}
}
