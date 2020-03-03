package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.ZeroPaddedCode;

/**
 * @author ThanhNX 看護区分コード
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(2)
@ZeroPaddedCode
public class NurseClassifiCode extends StringPrimitiveValue<NurseClassifiCode> {
	private static final long serialVersionUID = 1L;

	public NurseClassifiCode(String rawValue) {
		super(rawValue);
	}

}
