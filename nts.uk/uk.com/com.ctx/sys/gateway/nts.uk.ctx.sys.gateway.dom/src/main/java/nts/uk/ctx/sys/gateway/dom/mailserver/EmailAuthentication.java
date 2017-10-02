package nts.uk.ctx.sys.gateway.dom.mailserver;

import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;
import nts.uk.shr.com.primitive.CodePrimitiveValue;

/**
 * The Class EmailAuthentication.
 */
@StringCharType(CharType.ANY_HALF_WIDTH)
@StringMaxLength(80)
public class EmailAuthentication extends CodePrimitiveValue<EmailAuthentication> {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 3072948248326997648L;

	/**
	 * Instantiates a new email authentication.
	 *
	 * @param rawValue the raw value
	 */
	public EmailAuthentication(String rawValue) {
		super(rawValue);
	}
	
}
