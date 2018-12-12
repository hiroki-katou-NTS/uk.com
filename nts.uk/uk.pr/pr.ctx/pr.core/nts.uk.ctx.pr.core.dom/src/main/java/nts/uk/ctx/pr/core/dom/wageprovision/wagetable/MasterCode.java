package nts.uk.ctx.pr.core.dom.wageprovision.wagetable;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * 範囲下限
 */

@StringMaxLength(10)
@StringCharType(CharType.ALPHA_NUMERIC)
public class MasterCode extends StringPrimitiveValue<MasterCode> {

	private static final long serialVersionUID = 1L;

	public MasterCode(String rawValue) {
		super(rawValue);
	}

}
