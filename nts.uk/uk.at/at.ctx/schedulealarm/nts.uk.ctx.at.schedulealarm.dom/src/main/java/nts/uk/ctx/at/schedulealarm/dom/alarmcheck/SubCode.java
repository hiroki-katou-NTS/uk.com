package nts.uk.ctx.at.schedulealarm.dom.alarmcheck;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
/**
 * サブコード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定のアラームチェック.アラームチェック条件.サブコード
 * @author lan_lt
 *
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(2)
public class SubCode extends CodePrimitiveValue<SubCode>{
	/**	 serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public SubCode(String rawValue) {
		super(rawValue);
	}
}
