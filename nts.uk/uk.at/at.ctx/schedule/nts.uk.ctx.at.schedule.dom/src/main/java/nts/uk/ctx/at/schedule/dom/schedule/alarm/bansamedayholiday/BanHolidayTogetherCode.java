package nts.uk.ctx.at.schedule.dom.schedule.alarm.bansamedayholiday;

import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;
/**
 * 同日休日禁止コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同日休日禁止
 * @author lan_lt
 *
 */
@ZeroPaddedCode
@StringMaxLength(3)
public class BanHolidayTogetherCode extends CodePrimitiveValue<BanHolidayTogetherCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BanHolidayTogetherCode(String rawValue) {
		super(rawValue);
	}

}
