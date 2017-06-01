package nts.uk.ctx.sys.portal.dom.toppagepart;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

/**
 * @author LamDT
 */
@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(4)
public class TopPagePartCode extends StringPrimitiveValue<TopPagePartCode> {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	public TopPagePartCode(String rawValue) {
		super(rawValue);
	}

}
