package nts.uk.ctx.at.record.dom.stampmanagement.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
@StringMaxLength(3)
@StringCharType(CharType.NUMERIC)
public class VailidateIP extends StringPrimitiveValue<VailidateIP> {


	private static final long serialVersionUID = 1L;
	
	public VailidateIP(String rawValue) {
		super(rawValue);
		// TODO Auto-generated constructor stub
	}

}
