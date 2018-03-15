package nts.uk.ctx.at.record.dom.remainingnumber.specialleave.empinfo.basicinfo;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(2)
@StringCharType(CharType.ALPHA_NUMERIC)
public class GrantTable extends StringPrimitiveValue<GrantTable>{

	private static final long serialVersionUID = 1L;

	public GrantTable(String rawValue) {
		super(rawValue);
	}

}
