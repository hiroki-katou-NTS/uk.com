package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.support;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.arc.primitive.constraint.StringRegEx;
/**
 * 応援カード番号
 * path: UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務実績.打刻管理.応援.応援カード番号
 * @author laitv
 *
 */
@StringMaxLength(6)
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringRegEx("^[a-zA-Z0-9\\s#$%&()~:|{}*+?@'<>_/;\"\\\\\\[\\]\\`-]{1,20}$")
public class SupportCardNumber extends StringPrimitiveValue<SupportCardNumber>{

	public SupportCardNumber(String rawValue) {
		super(rawValue);
	}
	private static final long serialVersionUID = 1L;

}
