package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

@StringMaxLength(20)
@StringCharType(CharType.ALPHA_NUMERIC)
public class SpecialLeaveCD extends CodePrimitiveValue<SpecialLeaveCD>  {

	private static final long serialVersionUID = 1L;

	public SpecialLeaveCD(String rawValue) {
		super(rawValue);
	}

}
