package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.monthly.ouen.aggframe;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(6)
/** 作業グループコード */
public class WorkGroupCode extends StringPrimitiveValue<WorkGroupCode> {

	/***/
	private static final long serialVersionUID = 1L;

	public WorkGroupCode(String rawValue) {
		super(rawValue);
	}

}
