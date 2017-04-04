package nts.uk.ctx.basic.dom.organization.workplace;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(10)
public class WorkPlaceCode extends StringPrimitiveValue<WorkPlaceCode>{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public WorkPlaceCode(String rawValue) {
		super(rawValue);
	}

}
