package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.CharType;
import nts.arc.primitive.constraint.StringCharType;
import nts.arc.primitive.constraint.StringMaxLength;

@StringCharType(CharType.ALPHA_NUMERIC)
@StringMaxLength(16)
public class PasswordCompressFile extends StringPrimitiveValue<PasswordCompressFile> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new password for compress file.
	 *
	 * @param rawValue the raw value
	 */
	public PasswordCompressFile(String rawValue) {
		super(rawValue);
	}
}
