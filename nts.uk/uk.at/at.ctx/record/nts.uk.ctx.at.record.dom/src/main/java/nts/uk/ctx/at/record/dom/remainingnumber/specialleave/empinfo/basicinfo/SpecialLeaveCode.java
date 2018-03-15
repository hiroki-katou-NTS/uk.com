package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(20)
@StringCharType(CharType.ALPHA_NUMERIC)
public class SpecialLeaveCode extends CodePrimitiveValue<SpecialLeaveCode>  {

	private static final long serialVersionUID = 1L;

	public SpecialLeaveCode(String rawValue) {
		super(rawValue);
	}

}
