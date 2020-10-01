package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * 同時出勤禁止コード
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.勤務予定のアラームチェック.同時出勤.同時出勤禁止.同時出勤禁止コード
 * @author lan_lt
 *
 */
@StringCharType(CharType.NUMERIC)
@ZeroPaddedCode
@StringMaxLength(3)
public class BanWorkTogetherCode extends CodePrimitiveValue<BanWorkTogetherCode>{
	/** serialVersionUID */
	private static final long serialVersionUID = 1L;
	
	public BanWorkTogetherCode(String rawValue) {
		super(rawValue);
	}

}
