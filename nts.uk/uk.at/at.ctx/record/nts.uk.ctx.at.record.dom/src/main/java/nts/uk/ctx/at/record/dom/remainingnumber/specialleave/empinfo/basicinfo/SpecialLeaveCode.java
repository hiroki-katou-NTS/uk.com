package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(20)
@StringCharType(CharType.ALPHA_NUMERIC)
public class SpecialLeaveCode extends StringPrimitiveValue<SpecialLeaveCode>  {

	private static final long serialVersionUID = 1L;

	public SpecialLeaveCode(String rawValue) {
		super(rawValue);
	}

}
