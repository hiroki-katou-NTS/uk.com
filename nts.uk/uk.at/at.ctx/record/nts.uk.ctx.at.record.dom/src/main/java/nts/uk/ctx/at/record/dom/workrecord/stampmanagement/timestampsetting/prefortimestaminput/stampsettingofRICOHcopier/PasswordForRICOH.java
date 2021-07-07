package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.stampsettingofRICOHcopier;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @name RICOH用パスワード
 * @part UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.打刻設定.打刻入力の前準備.RICOH複写機の打刻設定.RICOH用パスワード
 * @author ThanhPV
 */

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(8)
public class PasswordForRICOH extends StringPrimitiveValue<PasswordForRICOH>{
	
	private static final long serialVersionUID = 1L;

	/**
	 * @param rawValue
	 */
	public PasswordForRICOH(String rawValue) {
		super(rawValue);
	}
}