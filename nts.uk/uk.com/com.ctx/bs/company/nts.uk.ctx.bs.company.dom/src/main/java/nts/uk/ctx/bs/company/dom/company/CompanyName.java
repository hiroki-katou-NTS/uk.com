package nts.uk.ctx.bs.company.dom.company;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;

/**
 * Name of Company
 */
@StringCharType(CharType.ALPHA_NUMERIC)
public class CompanyName extends StringPrimitiveValue<CompanyName>{
	/**	 * serialVersionUID	 */
	private static final long serialVersionUID = 1L;

	public CompanyName(String rawValue) {
		super(rawValue);
	}


}
