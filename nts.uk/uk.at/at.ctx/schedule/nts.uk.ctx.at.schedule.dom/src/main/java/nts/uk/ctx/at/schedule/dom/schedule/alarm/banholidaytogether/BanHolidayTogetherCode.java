package nts.uk.ctx.at.schedule.dom.schedule.alarm.banholidaytogether;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 同日休日禁止コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止.同日休日禁止コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class BanHolidayTogetherCode extends CodePrimitiveValue<BanHolidayTogetherCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BanHolidayTogetherCode(String rawValue) {
		super(rawValue);
	}

}
