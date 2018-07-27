package nts.uk.ctx.sys.assist.dom.deletedata;

import nts.arc.primitive.StringPrimitiveValue;
import nts.arc.primitive.constraint.StringMaxLength;

@StringMaxLength(24)
public class PasswordCompressFileEncrypt extends StringPrimitiveValue<PasswordCompressFileEncrypt> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Instantiates a new password encrypt for compress file.
	 *
	 * @param rawValue the raw value
	 */
	public PasswordCompressFileEncrypt(String rawValue) {
		super(rawValue);
	}
}
