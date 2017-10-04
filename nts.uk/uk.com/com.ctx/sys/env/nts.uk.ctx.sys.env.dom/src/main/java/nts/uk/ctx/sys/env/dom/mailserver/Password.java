package nts.uk.ctx.sys.env.dom.mailserver;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class Password.
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(25)
public class Password extends CodePrimitiveValue<Password>{

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 6222974091020341765L;
	
	
	/**
	 * Instantiates a new password.
	 *
	 * @param rawValue the raw value
	 */
	public Password(String rawValue) {
		super(rawValue);
	}
}
